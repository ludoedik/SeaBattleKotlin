package org.eduard.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.eduard.exception.business.BusinessException
import org.eduard.exception.critical.CriticalServerException
import org.eduard.service.ClientServiceImpl
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandlerSeaBattle(private val client: Socket) : ClientHandler {
    private val reader: BufferedReader = client.getInputStream().bufferedReader()
    private val writer: PrintWriter = PrintWriter(client.getOutputStream())
    private var running = false
    private val clientService = ClientServiceImpl()
    private val objectMapper = jacksonObjectMapper()

    private val operationsMap = mapOf(
        Commands.REGISTRATION to clientService::addUser,
    )

    override fun handle() {
        running = true
        while (running) {
            try {
                val message: Message = objectMapper.readValue(reader.readLine())
                val operation = operationsMap[message.command]
                val response: Response?
                if (operation != null)
                    response = operation.invoke(message.value)
                else {
                    response = Response(Status.ERROR, "Not valid command")
                    println(response.message)
                }
                writer.println(objectMapper.writeValueAsString(response))
                writer.flush()
            } catch (ex: BusinessException) {
                println(ex.message)
                writer.println(objectMapper.writeValueAsString(Response(Status.ERROR, ex.message ?: "Error")))
                writer.flush()
            } catch (ex: CriticalServerException) {
                close(ex.message ?: "Critical error happened")
                running = false
            } catch (ex: NullPointerException) {
                close(ex.localizedMessage)
                running = false
            }
        }
    }

    private fun close(message: String) {
        println("Socket closed. Message: $message")
        reader.close()
        writer.close()
        client.close()
    }
}
