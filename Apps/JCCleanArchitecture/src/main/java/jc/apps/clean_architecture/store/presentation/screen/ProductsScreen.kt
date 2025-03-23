package jc.apps.clean_architecture.store.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import jc.apps.clean_architecture.store.presentation.widgets.LoadingDialog
import jc.apps.clean_architecture.store.presentation.widgets.MyTopAppBar
import jc.apps.clean_architecture.store.presentation.widgets.ProductCard


@Composable
internal fun ProductsScreen() {
    val viewModel: ProductsViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LoadingDialog(isLoading = state.value.isLoading)

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = { MyTopAppBar(text = "Products") }) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp,
        ) {
            items(state.value.products.size) { index ->
                ProductCard(product = state.value.products[index])
            }
        }
    }
}

