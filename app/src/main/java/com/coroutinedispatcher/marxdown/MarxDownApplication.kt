package com.coroutinedispatcher.marxdown

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.coroutinedispatcher.marxdown.ui.brain.instantiationError
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import com.coroutinedispatcher.marxdown.ui.generateHtml.HTMLFragment
import io.realm.Realm

class MarxDownApplication : Application() {

    private lateinit var editorFragment: EditorFragment
    private lateinit var documentsFragment: DocumentsFragment
    private lateinit var htmlFragment: HTMLFragment
    private lateinit var realm: Realm
    private lateinit var frozenRealm: Realm

    override fun onCreate() {
        super.onCreate()
        editorFragment = EditorFragment()
        documentsFragment = DocumentsFragment()
        htmlFragment = HTMLFragment()
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        frozenRealm = realm.freeze()
        editorFragmentInstance = editorFragment
        documentsFragmentInstance = documentsFragment
        htmlFragmentInstance = htmlFragment
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }

    companion object {
        private var editorFragmentInstance: EditorFragment? = null
        private var documentsFragmentInstance: DocumentsFragment? = null
        private var realm: Realm? = null
        private var htmlFragmentInstance: HTMLFragment? = null

        fun getEditorFragment() =
            checkNotNull(editorFragmentInstance) { EditorFragment::class.simpleName!!.instantiationError() }

        fun getDocumentsFragment() =
            checkNotNull(documentsFragmentInstance) { DocumentsFragment::class.simpleName!!.instantiationError() }

        fun getRealmInstance() =
            checkNotNull(realm) { Realm::class.java.simpleName.instantiationError() }

        fun getHtmlFragment() =
            checkNotNull(htmlFragmentInstance) { HTMLFragment::class.simpleName!!.instantiationError() }
    }
}
