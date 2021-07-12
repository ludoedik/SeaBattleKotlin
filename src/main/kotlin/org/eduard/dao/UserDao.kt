package org.eduard.dao

import org.eduard.dto.UserDto
import org.eduard.entity.UserEntity

interface UserDao {
    fun get(user: UserDto): UserEntity?
    fun save(user: UserDto): Long
}