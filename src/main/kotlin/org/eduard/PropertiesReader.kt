package org.eduard

import java.io.File
import java.io.InputStream

class PropertiesReader {
    val properties = HashMap<String, String>()
    init {
        try {
            val inputStream: InputStream = File("./src/main/resources/server.conf").inputStream()
            inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    if (!line.isBlank()) {
                        val params = line.split("=")
                        properties.put(params[0].trim(), params[1].trim())
                    }
                }
            }
        } catch (ex: Exception) {
            println("Could not read any params. Cause: ${ex.localizedMessage}")
        }
    }
    companion object {
        val instance = PropertiesReader()
    }
}