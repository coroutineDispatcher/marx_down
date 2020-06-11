package com.coroutinedispatcher.marxdown

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class MarxDownApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }
}
