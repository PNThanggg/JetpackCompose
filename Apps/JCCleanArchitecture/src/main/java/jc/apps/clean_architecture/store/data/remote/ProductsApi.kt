package jc.apps.clean_architecture.store.data.remote

import jc.apps.clean_architecture.store.domain.model.Product
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): List<Product>
}