package com.coroutinedispatcher.marxdown.ui.editor

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.TextFormat
import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.WildCard
import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.formats.BoldFormat
import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.formats.CodeFormat
import com.coroutinedispatcher.marxdown.ui.editor.data.textformat.formats.ItalicFormat
import com.coroutinedispatcher.marxdown.utils.CustomTypefaceSpan
import com.coroutinedispatcher.marxdown.utils.removeCharAt
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
            val boldOccurrences = findOccurrences(
                text = text,
                wildCard = WildCard.BOLD
            )
            val italicOccurrences = findOccurrences(
                text = boldOccurrences.first,
                wildCard = WildCard.ITALIC,
                currentListOfTextFormats = boldOccurrences.second
            )

            // We pass the data from the last searched wildcard
            _state.value = State.Success(
                createSpannableString(
                    text = italicOccurrences.first,
                    listOfTextFormat = italicOccurrences.second
                )
            )
        }
    }

    private fun findOccurrences(
        text: String,
        wildCard: WildCard,
        currentListOfTextFormats: List<TextFormat> = listOf()
    ): Pair<String, List<TextFormat>> {
        val listOfTextFormats = mutableListOf<TextFormat>()
        var tempText = text
        var index = tempText.indexOf(wildCard.chars)
        var fromIndex = -1

        while (index >= 0) {
            if (fromIndex == -1) {
                fromIndex = index
            } else {
                // EUREKA -> Found a new Format
                // Decrease by 1 the toIndex because we're going to remove the ending wildcard char
                val toIndex = index - 1
                // Check and update if this range impact previous found formats
                currentListOfTextFormats.forEach { iTextFormat ->
                    when {
                        // The new founded format is before the previously founded format
                        iTextFormat.from > fromIndex && iTextFormat.to > toIndex -> {
                            iTextFormat.apply {
                                this.from -= 2
                                this.to -= 2
                            }
                        }
                        // The new founded format is in the middle of the previously founded format
                        iTextFormat.from < fromIndex && iTextFormat.to > toIndex -> {
                            iTextFormat.apply {
                                this.to -= 2
                            }
                        }
                        // The new founded format cuts the previously founded format in the middle and goes beyond
                        iTextFormat.from > fromIndex && iTextFormat.to < toIndex -> {
                            iTextFormat.apply {
                                this.to -= 2
                            }
                        }
                    }
                }
                // Add found format information in the list
                listOfTextFormats.add(
                    createTextFormat(
                        wildCard = wildCard,
                        from = fromIndex,
                        to = toIndex
                    )
                )
                // Remove starting and ending wildcard char
                tempText = tempText
                    .removeCharAt(index = fromIndex)
                    .removeCharAt(index = toIndex)
                // Move index cursor 2 steps backs
                index -= 2
                // Reset fromIndex
                fromIndex = -1
            }
            index = tempText.indexOf(wildCard.chars, index + 1)
        }
        // Add currentListOfTextFormats to the top of the new list
        listOfTextFormats.addAll(0, currentListOfTextFormats)

        return Pair(
            first = tempText,
            second = listOfTextFormats.toList()
        )
    }

    private fun createTextFormat(
        wildCard: WildCard,
        from: Int,
        to: Int
    ): TextFormat {
        return when (wildCard) {
            WildCard.BOLD -> {
                BoldFormat(from, to)
            }
            WildCard.ITALIC -> {
                ItalicFormat(from, to)
            }
            WildCard.CODE -> {
                CodeFormat(from, to)
            }
        }
    }

    private fun createSpannableString(
        text: String,
        listOfTextFormat: List<TextFormat>
    ): SpannableString {
        val spannableText = SpannableString(text)
        listOfTextFormat.forEach { iTextFormat ->
            when (iTextFormat) {
                is BoldFormat -> {
                    if (::boldTypeface.isInitialized) {
                        spannableText.setSpan(
                            CustomTypefaceSpan(boldTypeface),
                            iTextFormat.from,
                            iTextFormat.to,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                is ItalicFormat -> {
                    if (::italicTypeface.isInitialized) {
                        spannableText.setSpan(
                            CustomTypefaceSpan(italicTypeface),
                            iTextFormat.from,
                            iTextFormat.to,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                is CodeFormat -> {
                    // TODD Implement this method
                }
            }
        }
        return spannableText
    }
}
