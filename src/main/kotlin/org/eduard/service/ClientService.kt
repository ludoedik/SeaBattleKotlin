package org.eduard.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.eduard.dao.ClientDaoImpl
import org.eduard.dto.ClientDto

class ClientService {
    val clientDao = ClientDaoImpl()
    val mapper = jacksonObjectMapper()

    fun authorize(params: String): String {
        val client: ClientDto = jacksonObjectMapper().readValue(params)
        //val client = ClientDto("Andrey", "Zaytsev")
        return clientDao.save(client)
    }
}