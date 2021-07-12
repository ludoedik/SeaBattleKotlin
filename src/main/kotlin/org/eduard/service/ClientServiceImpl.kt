package org.eduard.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.eduard.dao.UserDaoImpl
import org.eduard.dto.UserDto
import org.eduard.handler.Response
import org.eduard.handler.Status

class ClientServiceImpl : ClientService {
    private val clientDao = UserDaoImpl()
    private val mapper = jacksonObjectMapper()
    private var currentUser: AuthorizedUser? = null

    override fun addUser(params: String): Response {
        val user: UserDto = mapper.readValue(params)
        val userId = clientDao.save(user)
        if (userId > 0) currentUser = AuthorizedUser(userId, user.username)
        return Response(Status.OK,"User successfully added")
    }

    override fun login(params: String): Response {
        val user: UserDto = mapper.readValue(params)
        val userEntity = clientDao.get(user)
        if (userEntity != null) {
            currentUser = AuthorizedUser(userEntity.id, userEntity.username)
        }
        return Response(Status.OK,"User successfully added")
        TODO("Not yet implemented")
    }
}