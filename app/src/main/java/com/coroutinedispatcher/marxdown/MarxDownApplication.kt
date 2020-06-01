package com.coroutinedispatcher.marxdown

import android.app.Application
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment

class MarxDownApplication : Application() {

    private lateinit var editorFragment: EditorFragment
    private lateinit var documentsFragment: DocumentsFragment

    override fun onCreate() {
        super.onCreate()
        editorFragment = EditorFragment()
        documentsFragment =
            DocumentsFragment()
        editorFragmentInstance = editorFragment
        documentsFragmentInstance = documentsFragment
    }

    companion object {
        private var editorFragmentInstance: EditorFragment? = null
        private var documentsFragmentInstance: DocumentsFragment? = null

        fun getEditorFragment() =
            checkNotNull(editorFragmentInstance) { "EditorFragment has not been instantiated yet" }

        fun getDocumentsFragment() =
            checkNotNull(documentsFragmentInstance) { "DocumentFragment has not been instantiated yet" }
    }
}
