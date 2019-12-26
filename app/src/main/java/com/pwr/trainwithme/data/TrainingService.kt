package com.pwr.trainwithme.data

import com.pwr.trainwithme.Trainer
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface TrainingService {

    // TODO catch connection timeout exception
    companion object{
        private const val SERVER_URL = "http://192.168.1.169:8080/"

        operator fun invoke(
        ): TrainingService{

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SERVER_URL)
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()) // use Gson to parse Json
                .build()
                .create(TrainingService::class.java)
        }
    }

    @GET("trainers")
    suspend fun getTrainers(): Response<List<Trainer>>

    @POST("")
    suspend fun authorize(): Response<List<Trainer>>
}

