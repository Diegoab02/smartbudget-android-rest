package co.edu.unipiloto.smartbudget.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente Retrofit único (singleton) para SmartBudget.
 *
 * Cambia BASE_URL por la URL real del backend desplegado.
 * Para desarrollo local con el emulador Android, usa http://10.0.2.2:<puerto>/
 * (10.0.2.2 mapea al localhost de la máquina host).
 */
object RetrofitClient {

    // TODO: reemplazar por la URL real del backend SmartBudget
    private const val BASE_URL = "https://smartbudget.example.co/api/v1/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
