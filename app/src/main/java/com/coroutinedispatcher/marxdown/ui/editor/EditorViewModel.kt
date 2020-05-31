package com.coroutinedispatcher.marxdown.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EditorViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    sealed class State {
        object Idle : State()

        // Todo to be deleted
        object Loading : State()
        object Success : State()
    }

    init {
        viewModelScope.launch {
            _state.value = State.Idle
            delay(1000)
            _state.value = State.Loading
            delay(1000)
            _state.value = State.Success
        }
    }
}
