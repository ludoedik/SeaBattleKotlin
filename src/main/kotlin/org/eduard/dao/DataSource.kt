package org.eduard.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.eduard.exception.InitializingException
import org.eduard.PropertiesReader
import org.h2.tools.RunScript
import java.io.FileReader
import java.sql.Connection

class DataSource {
    private val url: String
    private var username: String
    private val password: String
    private val hikariConfig = HikariConfig()
    private val hikariDatasource: HikariDataSource

    init {
        try {
            val configReader = PropertiesReader.instance
            url =
                configReader.properties.get("database.url") ?: throw Exception("Database url is missing")
            username =
                configReader.properties.get("database.username") ?: throw Exception("Database username is missing")
            password =
                configReader.properties.get("database.password") ?: throw Exception("Database password is missing")
        } catch (ex: Exception) {
            println("Error while loading datasource.\nCause: ${ex.localizedMessage}")
            throw InitializingException()
        }
        hikariConfig.jdbcUrl = url
        hikariConfig.username = username
        hikariConfig.password = password
        hikariDatasource = HikariDataSource(hikariConfig)
        /*val connection: Connection = getConnection()
        val statement = connection.prepareStatement("drop table if exists Client;\n" +
                "create table Client\n" +
                "(\n" +
                "    ID INTEGER auto_increment,\n" +
                "    USERNAME VARCHAR not null,\n" +
                "    PASSWORD VARCHAR not null\n" +
                ");")
        statement.execute()
        connection.close()*/
    }

    fun getConnection(): Connection{
        return hikariDatasource.connection
    }

    companion object {
        val instance = DataSource()
    }
}