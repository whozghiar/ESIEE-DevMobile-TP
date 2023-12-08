package fr.unilasalle.androidtp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FakeStoreService {

    private const val BASE_URL = "https://fakestoreapi.com/products"

    fun <T> buildService(serviceType: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceType)
    }
}