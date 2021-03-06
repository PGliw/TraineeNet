package com.pwr.commonplatform.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.pwr.commonplatform.data.model.trainee.*
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingDetails
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingOverview
import com.pwr.commonplatform.data.model.trainer.TrainingStatusDTO
import com.pwr.commonplatform.utils.ConnectivityInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface TrainingNetAPI {

    companion object {
        //private const val SERVER_URL = "http://192.168.1.169:8080/"
        private const val SERVER_URL = "http://34.89.157.85:80/"
        private const val TIMEOUT_SECONDS = 10L
        var accessToken: String? = null
        var refreshToken: String? = null

        operator fun invoke(
            context: Context
        ): TrainingNetAPI {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(ConnectivityInterceptor(context))
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrainingNetAPI::class.java)
        }
    }

    @GET("trainers2")
    suspend fun getTrainers(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<Trainer>>

    @GET("trainers2/overviews")
    suspend fun getTrainersOverviews(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<TrainerOverview>>

    @GET("trainers2/summaries")
    suspend fun getTrainersSummaries(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<Summary>>

    @GET("centres/summaries")
    suspend fun getCentresSummaries(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<Summary>>

    @GET("sports/summaries")
    suspend fun getSportsSummaries(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<Summary>>

    @Headers("Authorization: Basic ZnJvbnRlbmRDbGllbnRJZDpmcm9udGVuZENsaWVudFNlY3JldA==")
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAuthToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): Response<TokenResponse>

    @GET("trainers2/{id}/details")
    suspend fun getTrainerDetails(
        @Path("id") trainerID: Long,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<TrainerDetails>

    @GET("trainers2/{id}/trainings")
    suspend fun getTrainerUpcomingTrainings(
        @Path("id") trainerID: Long,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<TrainingSummary>>

    // TODO query param with sport id, eg. ?sportID=XYZ
    @GET("trainers2/{id}/centres")
    suspend fun getTrainerCentres(
        @Path("id") trainerID: Long,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<CentreOverview>>

    @GET("trainee/trainings/summaries")
    suspend fun getTrainingsSummaries(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<List<TrainingSummary>>

    @GET("trainer/trainings")
    suspend fun getTrainerTrainingsOverviews(
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken",
        @Query("status")
        trainingStatus: String? = null
    ): Response<List<TrainerTrainingOverview>>

    @GET("trainer/trainings/{id}/details")
    suspend fun getTrainerTrainingDetails(
        @Path("id")
        trainingID: Long,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<TrainerTrainingDetails>

    @PATCH("trainer/trainings/{id}")
    suspend fun updateTrainerTrainingStatus(
        @Path("id")
        trainingID: Long,
        @Body
        statusDTO: TrainingStatusDTO,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<Any>

    @POST("trainee/trainings")
    suspend fun postTraineeTraining(
        @Body traineeTrainingDTO: TraineeTrainingDTO,
        @Header("Authorization")
        authorizationHeaderValue: String = "Bearer $accessToken"
    ): Response<Any>
}


data class TokenResponse(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("error_description") val errorDescription: String? = null
)