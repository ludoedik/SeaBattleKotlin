package org.eduard

import org.eduard.dao.DataSource
import org.eduard.exception.InitializingException
import org.eduard.controller.Server
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