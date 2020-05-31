package com.coroutinedispatcher.marxdown.ui.editor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.coroutinedispatcher.marxdown.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EditorFragment : Fragment(R.layout.fragment_editor) {

    private val editorViewModel by viewModels<EditorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeState()
    }

    private fun observeState() {
        editorViewModel.state
            .onEach { state -> Log.d(EditorFragment::class.java.simpleName, state.toString()) }
            .launchIn(lifecycleScope)
    }
}
