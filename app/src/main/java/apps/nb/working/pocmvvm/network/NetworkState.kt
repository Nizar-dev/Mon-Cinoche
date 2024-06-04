package apps.nb.working.pocmvvm.network

import retrofit2.Response

sealed class NetworkState <out T> {
    data class Success <out T> (val data: T) : NetworkState <T> ()
    data class Error<T> (val rseponse: Response<T>) : NetworkState <T> ()


}