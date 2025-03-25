package jc.apps.lol.presentation.home

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