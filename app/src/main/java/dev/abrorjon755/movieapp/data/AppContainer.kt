package dev.abrorjon755.movieapp.data

import dev.abrorjon755.movieapp.network.MovieApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val movieRepository: MovieRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.themoviedb.org/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMGI2NTI1ODk2Zjg5MjEzZjQyZTlhYTI3OWQwNTcwZSIsIm5iZiI6MTcyNjIxODg0Ny4wMiwic3ViIjoiNjZlNDAyNWZjODFiMjRiM2ZlMjNmYWE4Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.QUcJ5a2BhpHFnhe7mJTR_GIc-QX-EQD8ni5lOuh-hSA"
                )
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    override val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl(retrofitService)
    }
}