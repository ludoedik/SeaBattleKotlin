package org.eduard.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.eduard.Message
import org.eduard.exception.NoSuchCommandException
import org.eduard.service.ClientService
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandlerSeaBattle(private val client: Socket) : ClientHandler {
    private val reader: BufferedReader = client.getInputStream().bufferedReader()
    private val writer: PrintWriter = PrintWriter(client.getOutputStream())
    private var running = false
    private var isAuthorized = false
    private val clientService = ClientService()
    private val operationsMap = mapOf(
        "auth" to clientService::authorize,
    )
    private val objectMapper = jacksonObjectMapper()

    override fun handle() {
        running = true
        while (running) {
            try {
                val message: Message = objectMapper.readValue(reader.readLine())
                val operation = operationsMap[message.command]
                val response: String?
                if (operation != null)
                    response = operation.invoke(message.value)
                else throw NoSuchCommandException()
                writer.println(response)
                writer.flush()
            } catch (ex: NoSuchCommandException) {
                println("Not valid operation")
                writer.println("Not valid command")
                writer.flush()
            } catch (ex: NullPointerException) {
                println("Socket closed. Message: ${ex.localizedMessage}")
                reader.close()
                writer.close()
                client.close()
                running = false
            }
        }
    }
}
