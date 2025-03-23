package jc.apps.clean_architecture.store.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.apps.clean_architecture.store.domain.repository.ProductsRepository
import jc.apps.clean_architecture.store.presentation.sendEvent
import jc.apps.clean_architecture.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsViewState())
    val state = _state.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            productsRepository.getProducts().fold(
                fnL = { error ->

                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                },
                fnR = { products ->
                    _state.update {
                        it.copy(products = products)
                    }
                },
            )

            _state.update { it.copy(isLoading = false) }
        }
    }
}