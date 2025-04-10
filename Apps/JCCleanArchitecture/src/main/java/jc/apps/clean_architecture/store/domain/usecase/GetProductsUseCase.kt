package jc.apps.clean_architecture.store.domain.usecase

import jc.apps.clean_architecture.store.domain.model.NetworkError
import jc.apps.clean_architecture.store.domain.model.Product
import jc.apps.clean_architecture.store.domain.repository.ProductsRepository
import jc.apps.clean_architecture.utils.Either
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): Either<NetworkError, List<Product>> {
        return repository.getProducts()
    }
}