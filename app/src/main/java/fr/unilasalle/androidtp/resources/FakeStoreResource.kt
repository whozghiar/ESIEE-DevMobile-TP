package fr.unilasalle.androidtp.resources


import retrofit2.http.GET
import fr.unilasalle.androidtp.beans.Product

class FakeStoreResource {
    @GET("products")
    fun getProducts(): retrofit2.Call<List<Product>> {
        return TODO()
    }

}