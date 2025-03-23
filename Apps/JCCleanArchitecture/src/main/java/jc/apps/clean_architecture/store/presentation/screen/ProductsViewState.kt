package jc.apps.clean_architecture.store.presentation.screen

import jc.apps.clean_architecture.store.domain.model.Product

data class ProductsViewState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)