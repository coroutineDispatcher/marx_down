package com.coroutinedispatcher.marxdown.ui.documents

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class DocumentsFragment : Fragment() {

    private val documentsViewModel by viewModels<DocumentsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
