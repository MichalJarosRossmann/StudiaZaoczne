package pl.studia.studiadzienne.api

import pl.studia.studiadzienne.api.responses.RestaurantResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantApi {

    @GET("testHelloWorld")
    fun getHelloWorld(): Call<RestaurantHelloWorldResponse>


    @GET("RestaurantList")
    suspend fun getRestaurantListSuspend(): List<RestaurantResponse>


}