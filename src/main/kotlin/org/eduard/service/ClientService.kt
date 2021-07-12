package org.eduard.service

import org.eduard.handler.Response

interface ClientService {
    fun addUser(params: String): Response
    fun login(params: String): Response

}