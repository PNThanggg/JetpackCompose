package jc.apps.clean_architecture.store.data.mapper

import jc.apps.clean_architecture.store.domain.model.ApiError
import jc.apps.clean_architecture.store.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toGeneralError(): NetworkError {
    val error = when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error, t = this
    )
}