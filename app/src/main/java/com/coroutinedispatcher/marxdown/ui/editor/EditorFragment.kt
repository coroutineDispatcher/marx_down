package com.coroutinedispatcher.marxdown.ui.editor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.coroutinedispatcher.marxdown.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class EditorFragment : Fragment(R.layout.fragment_editor) {

    private lateinit var tvDisplay: TextView
    private lateinit var etInput: EditText

    private val editorViewModel by viewModels<EditorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeTextView(view = view)
        initializeEditText(view = view)
    }

    private fun initializeTextView(view: View) {
        tvDisplay = view.findViewById(R.id.tv_display)
    }

    private fun initializeEditText(view: View) {
        etInput = view.findViewById(R.id.et_input)
        etInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                editorViewModel.onTextChanged(text = s.toString())
            }
        })
    }

    private fun observeState() {
        editorViewModel.state
            .onEach { state ->
                Timber.d("$state")
                if (state is EditorViewModel.State.Success && !state.data.isNullOrBlank()) {
                    tvDisplay.text = state.data
                }
            }
            .launchIn(lifecycleScope)
    }
}
