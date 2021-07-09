package org.eduard

import java.io.File
import java.io.InputStream

class PropertiesReader {
    val properties = HashMap<String, String>()
    init {
        try {
            val inputStream: InputStream = File("./src/main/resources/application.conf").inputStream()
            inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    if (!line.isBlank()) {
                        val params = line.split("=")
                        if (params.size == 2) properties.put(params[0].trim(), params[1].trim()) else println("Could not read params: $line")
                    }
                }
            }
        }
        catch (ex: Exception) {
            println("Could not read any params. Cause: ${ex.localizedMessage}")
        }
    }
    companion object {
        val instance = PropertiesReader()
    }
}