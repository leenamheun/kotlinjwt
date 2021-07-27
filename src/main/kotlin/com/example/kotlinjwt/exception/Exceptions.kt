package com.example.kotlinjwt.exception

import com.example.kotlinjwt.enum.ErrorMessageCode
import java.lang.RuntimeException

class Exceptions {
}

open class ApplicationException(val errorMessageCode: ErrorMessageCode) : RuntimeException(errorMessageCode.message){
    open var errors: List<ApiError>? = null
        set(value) {
            errors = value
        }
}

class SignupException(errorMessageCode: ErrorMessageCode) : ApplicationException(errorMessageCode)

data class ApiError(
    var code: String,
    var message: String
)