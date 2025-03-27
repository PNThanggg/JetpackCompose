package jc.apps.lol.presentation.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.apps.lol.data.model.ChampionModel
import jc.apps.lol.domain.usecase.GetChampionByNameUseCase
import jc.apps.lol.presentation.ChampionDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChampionDetailsViewModel @Inject constructor(
    private val getChampionByNameUseCase: GetChampionByNameUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var champion = mutableStateOf<ChampionModel?>(null)

    init {
        val args = savedStateHandle.toRoute<ChampionDetails>()

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getChampionByNameUseCase.run(args.name) }
            result.fold(fnL = { failure ->
                Log.d("TAG", "ChampionDetailsViewModel: ${failure.message}")
            }, fnR = { response ->
                champion.value = response.champion.values.firstOrNull()
            })
        }
    }
}