package org.eduard.dao

import org.eduard.dto.ClientDto
import org.eduard.entity.ClientEntity

interface ClientDao {
    fun get(id: Long): ClientEntity
    fun save(client: ClientDto): String
}