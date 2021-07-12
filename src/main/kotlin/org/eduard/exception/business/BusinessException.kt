package org.eduard.exception.business

import java.lang.Exception

open class BusinessException(message: String = "Some error happened") : Exception(message) {
}