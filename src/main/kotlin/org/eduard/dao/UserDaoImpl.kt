package org.eduard.dao

import org.eduard.dto.UserDto
import org.eduard.entity.UserEntity
import org.eduard.exception.business.UserAlreadyExistsException
import org.eduard.exception.critical.CriticalServerException
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import java.sql.Connection
import java.sql.Statement

class UserDaoImpl : UserDao {
    override fun get(user: UserDto): UserEntity? {
        var connection: Connection? = null
        try {
            connection = DataSource.instance.getConnection()
            val statement = connection.prepareStatement("SELECT * FROM CLIENT WHERE USERNAME = ? AND PASSWORD = ?")
            statement.setString(1, user.username)
            statement.setString(2, user.password)
            val resultSet = statement.executeQuery()
            return if(resultSet.next())
                UserEntity(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3))
            else null
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        } finally {
            connection?.close()
        }
    }

    override fun save(user: UserDto): Long {
        val query = "insert into CLIENT (USERNAME, PASSWORD) VALUES ( ?, ? );"
        var connection: Connection? = null
        try {
            connection = DataSource.instance.getConnection()
            val statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            statement.setString(1, user.username)
            statement.setString(2, user.password)
            statement.execute()
            val resultSet = statement.generatedKeys
            return resultSet.getLong(1)
        } catch (ex: JdbcSQLIntegrityConstraintViolationException) {
            throw UserAlreadyExistsException()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw CriticalServerException("Unexpected exception")
        } finally {
            connection?.close()
        }
    }
}