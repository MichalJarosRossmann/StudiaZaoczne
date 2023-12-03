package pl.studia.studiadzienne.api

import pl.studia.studiadzienne.api.responses.RestaurantResponse
import pl.studia.studiazaoczne.compose.model.Restaurant


class LoadRestaurantUseCase {

    val restaurantApi = ClientProviderSingleton.restaurantApi


    //usecase do pobierania danych z api
    suspend operator fun invoke(): List<Restaurant> {
        val restaurantListSuspend = restaurantApi.getRestaurantListSuspend()
        return restaurantListSuspend.map { map(it) }
    }


    //Mapowanie odpowiedzi z api
    private fun map(restaurantResponse: RestaurantResponse): Restaurant {

        return Restaurant(
            restaurantResponse.title ?: "",
            restaurantResponse.address ?: "",
            restaurantResponse.open,
            restaurantResponse.address?.split(",")?.last()?.toIntOrNull() ?: 0
        )
    }
}