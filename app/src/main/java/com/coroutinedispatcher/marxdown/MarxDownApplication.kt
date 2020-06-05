package com.coroutinedispatcher.marxdown

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import io.realm.Realm
import timber.log.Timber

class MarxDownApplication : Application() {

    private lateinit var realm: Realm
    private lateinit var frozenRealm: Realm

    private lateinit var editorFragment: EditorFragment
    private lateinit var documentsFragment: DocumentsFragment

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeRealm()
        initializeFragments()
        initializeDarkMode()
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

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeRealm() {
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        frozenRealm = realm.freeze()
    }

    private fun initializeFragments() {
        initializeEditorFragment()
        initializeDocumentsFragment()
    }

    private fun initializeEditorFragment() {
        editorFragment = EditorFragment()
        editorFragmentInstance = editorFragment
    }

    private fun initializeDocumentsFragment() {
        documentsFragment = DocumentsFragment()
        documentsFragmentInstance = documentsFragment
    }

    private fun initializeDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }
}
