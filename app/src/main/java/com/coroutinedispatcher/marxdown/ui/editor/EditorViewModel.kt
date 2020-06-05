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
    private val _state = MutableStateFlow<State<String>>(State.Idle())
    val state: StateFlow<State<String>> = _state

    sealed class State<T> {
        data class Idle<T>(var data: T? = null) : State<T>()

        // Todo to be deleted
        data class Loading<T>(var data: T? = null) : State<T>()
        data class Success<T>(var data: T? = null) : State<T>()
    }

    init {
        viewModelScope.launch {
            _state.value = State.Idle()
            delay(1000)
            _state.value = State.Loading()
            delay(1000)
            _state.value = State.Success()
        }
    }

    fun onTextChanged(text: String) {
        viewModelScope.launch {
            _state.value = State.Success(text)
        }
    }
}
