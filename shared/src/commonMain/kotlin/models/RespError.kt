package models

import kotlinx.serialization.Serializable

@Serializable
data class RespError(
    val error: KTError,
)
@Serializable
data class KTError(
    val code: Int,
    val message: String,
)