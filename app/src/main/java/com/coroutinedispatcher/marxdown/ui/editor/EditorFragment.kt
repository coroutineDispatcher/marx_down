package com.coroutinedispatcher.marxdown.ui.editor

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.coroutinedispatcher.marxdown.MarxDownApplication
import com.coroutinedispatcher.marxdown.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EditorFragment : Fragment(R.layout.fragment_editor) {

    private val editorViewModel by viewModels<EditorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        observeState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.html_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.html_menu -> openHtmlFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openHtmlFragment() {
        // Todo add bundle parameters
        if (MarxDownApplication.getHtmlFragment().isAdded) return
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, MarxDownApplication.getHtmlFragment())
            .addToBackStack("")
            .commit()
    }

    private fun observeState() {
        editorViewModel.state
            .onEach { state -> Log.d(EditorFragment::class.java.simpleName, state.toString()) }
            .launchIn(lifecycleScope)
    }
}
