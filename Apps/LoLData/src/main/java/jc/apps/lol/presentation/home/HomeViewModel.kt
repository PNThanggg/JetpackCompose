package jc.apps.lol.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.apps.lol.data.model.toChampionList
import jc.apps.lol.domain.usecase.GetAllChampionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChampionsUseCase: GetAllChampionsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getChampionsUseCase.invoke().fold(fnL = { failure ->
                _state.update {
                    it.copy(
                        isLoading = false, error = failure.message
                    )
                }
            }, fnR = { response ->
                Log.i(TAG, "$response")
                Log.i(TAG, "${response.champion}")
                Log.i(TAG, "${response.format}")
                Log.i(TAG, "${response.type}")
                Log.i(TAG, "${response.version}")

                _state.update {
                    it.copy(
                        isLoading = false,
                        champions = response.champion.toChampionList(),
                    )
                }
            })
        }
    }


    fun onSearchTextChange(text: String) {
        _state.update {
            it.copy(
                searchText = text, filteredChampions = it.champions.filter { champion ->
                    champion.name?.contains(text, ignoreCase = true) == true
                })
        }
    }
}