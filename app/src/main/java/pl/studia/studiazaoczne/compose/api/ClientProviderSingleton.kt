package pl.studia.studiadzienne.api

object ClientProviderSingleton {

    //Tworzone pierwszy raz kiedy ktoś będzie potrzebował dostę do restaurantApi
    val restaurantApi by lazy { ClienProvider().getRestaurantApi() }

}