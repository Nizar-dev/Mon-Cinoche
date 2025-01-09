package apps.nb.working.pocmvvm.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <U> OkHttpClient.createService(
    baseUrl: String,
    converter: GsonConverterFactory,
    service: Class<U>
) = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(converter)
    .client(this.newBuilder().build()).build().create(service)
