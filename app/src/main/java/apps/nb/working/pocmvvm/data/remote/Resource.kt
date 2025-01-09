package apps.nb.working.pocmvvm.data.remote

sealed class Resource<out T> {
    data class Success<out T>(
        val data: T,
        val totalRecords: Int? = null
    ) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
}
