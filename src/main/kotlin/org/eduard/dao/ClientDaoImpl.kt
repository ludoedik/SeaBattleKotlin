package org.eduard.dao

import org.eduard.dto.ClientDto
import org.eduard.entity.ClientEntity
import java.sql.Connection
import java.sql.PreparedStatement

class ClientDaoImpl : ClientDao {
    override fun get(id: Long): ClientEntity {
        TODO("Not yet implemented")
    }

    override fun save(client: ClientDto): String {
        var connection: Connection? = null
        try {
            connection = DataSource.instance.getConnection()
            val statement: PreparedStatement =
                connection.prepareStatement("insert into CLIENT (USERNAME, PASSWORD) VALUES ( '${client.username}', '${client.password}' );")
            statement.execute()
            return "User successfully added"
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        } finally {
            connection?.close()
        }/*
        var newConnection: Connection? = null
        try {
            newConnection = DataSource.instance.getConnection()
            val statement: PreparedStatement = newConnection.prepareStatement("SELECT USERNAME FROM CLIENT")
            val test = statement.executeQuery()
            test.next()
            println(test.getString("USERNAME"))
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            newConnection?.close()
        }*/
        return "yo"
    }
}