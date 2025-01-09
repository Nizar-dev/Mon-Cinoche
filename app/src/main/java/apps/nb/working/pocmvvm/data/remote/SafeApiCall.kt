package apps.nb.working.pocmvvm.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeApiCall @Inject constructor() {
    suspend inline fun <T> execute(crossinline body: suspend () -> T): Result<T> {
        return try {
            Result.Success(
                withContext(Dispatchers.IO) {
                    body.invoke()
                }
            )
        } catch (e: Exception) {
            Log.e("error in safeApiCall", "${e.message}")
            Result.Error(e.toString())
            /**when (e) {
             is IOException -> context.getString(R.string.error_connection)
             is HttpException -> {
             if (e.code() == 504) context.getString(R.string.error_connection)
             else context.getString(R.string.error_service)
             }
             else -> context.getString(R.string.error_unknown)
             }
             )*/
        }
    }
}
