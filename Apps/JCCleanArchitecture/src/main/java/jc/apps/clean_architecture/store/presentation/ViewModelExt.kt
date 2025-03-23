package jc.apps.clean_architecture.store.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jc.apps.clean_architecture.utils.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}