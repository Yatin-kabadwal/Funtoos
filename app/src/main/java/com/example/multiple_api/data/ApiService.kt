package com.example.multiple_api.data

import retrofit2.http.GET
import retrofit2.http.Query

// Data Models
data class GenderResponse(val name: String, val gender: String?, val probability: Float)
data class CatFactResponse(val fact: String)
data class DogImageResponse(val message: String, val status: String)
data class JokeResponse(val setup: String, val punchline: String)
data class RandomUserResponse(val results: List<User>)

data class User(
    val name: Name,
    val picture: Picture
)

data class Name(val first: String, val last: String)
data class Picture(val large: String)

// API Service Interface
interface ApiService {
    @GET("https://api.genderize.io/")
    suspend fun getGender(@Query("name") name: String): GenderResponse

    @GET("https://catfact.ninja/fact")
    suspend fun getCatFact(): CatFactResponse

    @GET("https://dog.ceo/api/breeds/image/random")
    suspend fun getRandomDogImage(): DogImageResponse

    @GET("https://official-joke-api.appspot.com/random_joke")
    suspend fun getRandomJoke(): JokeResponse

    @GET("https://randomuser.me/api/")
    suspend fun getRandomUser(): RandomUserResponse
}
