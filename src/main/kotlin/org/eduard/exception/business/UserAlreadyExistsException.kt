package org.eduard.exception.business

class UserAlreadyExistsException(message: String = "User already exists"): BusinessException(message) {
}