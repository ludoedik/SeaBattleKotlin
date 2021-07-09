package org.eduard

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.eduard.dao.DataSource
import org.eduard.dto.ClientDto
import org.eduard.exception.InitializingException
import org.eduard.server.Server
import kotlin.system.exitProcess

fun main() {
    try {
        PropertiesReader.instance
        DataSource.instance
        Server().run()
    } catch (ex: InitializingException) {
        exitProcess(-1)
    }
}