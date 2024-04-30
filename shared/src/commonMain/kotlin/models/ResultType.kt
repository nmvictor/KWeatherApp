package models

sealed class ResultType<T> (val data : T? = null, val error : String? = null) {
    class Success<T>(data: T?) : ResultType<T>(data)
    class Error<T>(error: String?) : ResultType<T>(null, error)
    class Loading<T>(val isLoading: Boolean = true) : ResultType<T>(null)
}