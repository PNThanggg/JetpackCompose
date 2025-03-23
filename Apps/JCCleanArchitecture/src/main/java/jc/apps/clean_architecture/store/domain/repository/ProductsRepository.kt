package jc.apps.clean_architecture.store.domain.repository

import jc.apps.clean_architecture.store.domain.model.NetworkError
import jc.apps.clean_architecture.store.domain.model.Product
import jc.apps.clean_architecture.utils.Either

interface ProductsRepository {
    suspend fun getProducts(): Either<NetworkError, List<Product>>
}