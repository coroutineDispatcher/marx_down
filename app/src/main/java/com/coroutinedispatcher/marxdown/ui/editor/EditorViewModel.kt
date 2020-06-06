package com.coroutinedispatcher.marxdown.ui.editor

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinedispatcher.marxdown.utils.CustomTypefaceSpan
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EditorViewModel : ViewModel() {
    private val _state = MutableStateFlow<State<SpannableString>>(State.Idle())
    val state: StateFlow<State<SpannableString>> = _state

    lateinit var boldTypeface: Typeface
    lateinit var italicTypeface: Typeface
    lateinit var codeTypeface: Typeface

    sealed class State<T> {
        data class Idle<T>(var data: T? = null) : State<T>()

        // Todo to be deleted
        data class Loading<T>(var data: T? = null) : State<T>()
        data class Success<T>(var data: T? = null) : State<T>()
    }

    sealed class TextFormat {
        data class Bold(var from: Int = -1, var to: Int = -1) : TextFormat()
        data class Italic(var from: Int, var to: Int) : TextFormat()
        data class Code(var from: Int, var to: Int) : TextFormat()
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
            var tempText = text
            // Find the BOLD substring starting and ending indexes
            var listOfTextFormat = mutableListOf<TextFormat>()
            val boldWildcard = "*"
            var index = tempText.indexOf(boldWildcard)
            val boldTextFormat = TextFormat.Bold()
            while (index >= 0) {
                when {
                    boldTextFormat.from == -1 -> {
                        boldTextFormat.from = index

                    }
                    boldTextFormat.to == -1 -> {
                        boldTextFormat.to = index - 1
                        listOfTextFormat.add(boldTextFormat.copy())
                        tempText = tempText.substring(0, boldTextFormat.from) + tempText.substring(boldTextFormat.from + 1)
                        tempText = tempText.substring(0, boldTextFormat.to) + tempText.substring(boldTextFormat.to + 1)
                        index -= 2
                        boldTextFormat.from = -1
                        boldTextFormat.to = -1
                    }
                }
                index = tempText.indexOf(boldWildcard, index + 1)
            }
            val spannableText = SpannableString(tempText)
            listOfTextFormat.forEach { iTextFormat ->
                when (iTextFormat) {
                    is TextFormat.Bold -> {
                        if (::boldTypeface.isInitialized) {
                            spannableText.setSpan(
                                CustomTypefaceSpan(boldTypeface),
                                iTextFormat.from,
                                iTextFormat.to,
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    is TextFormat.Italic -> {
                        // TODD Implement this method
                    }
                    is TextFormat.Code -> {
                        // TODD Implement this method
                    }
                }
            }
            _state.value = State.Success(spannableText)
        }
    }
}
