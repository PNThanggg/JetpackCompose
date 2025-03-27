package jc.apps.lol.presentation.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.apps.lol.data.model.ChampionModel
import jc.apps.lol.domain.usecase.GetChampionByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "ChampionDetails"

@HiltViewModel
class ChampionDetailsViewModel @Inject constructor(
    private val getChampionByNameUseCase: GetChampionByNameUseCase,
) : ViewModel() {
    var champion = mutableStateOf<ChampionModel?>(null)

    fun loadChampion(name: String) {
        Log.d(TAG, "loadChampion(): $name")
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getChampionByNameUseCase.run(name) }
            result.fold(fnL = { failure ->
                Log.d(TAG, "ChampionDetailsViewModel: ${failure.message}")
            }, fnR = { response ->
                champion.value = response.champion.values.firstOrNull()
            })
        }
    }
}