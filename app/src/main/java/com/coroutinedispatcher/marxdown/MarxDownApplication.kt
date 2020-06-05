package com.coroutinedispatcher.marxdown

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import io.realm.Realm

class MarxDownApplication : Application() {

    private lateinit var editorFragment: EditorFragment
    private lateinit var documentsFragment: DocumentsFragment
    private lateinit var realm: Realm
    private lateinit var frozenRealm: Realm

    override fun onCreate() {
        super.onCreate()
        editorFragment = EditorFragment()
        documentsFragment = DocumentsFragment()
        realm = Realm.getDefaultInstance()
        frozenRealm = realm.freeze()
        editorFragmentInstance = editorFragment
        documentsFragmentInstance = documentsFragment
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }

    companion object {
        private var editorFragmentInstance: EditorFragment? = null
        private var documentsFragmentInstance: DocumentsFragment? = null
        private var realm: Realm? = null

        fun getEditorFragment() =
            checkNotNull(editorFragmentInstance) { "EditorFragment has not been instantiated yet" }

        fun getDocumentsFragment() =
            checkNotNull(documentsFragmentInstance) { "DocumentFragment has not been instantiated yet" }

        fun getRealmInstance() =
            checkNotNull(realm) { "Realm instance has not been instantiated yet" }
    }
}
