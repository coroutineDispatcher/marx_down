package com.coroutinedispatcher.marxdown.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coroutinedispatcher.marxdown.MarxDownApplication
import com.coroutinedispatcher.marxdown.R
import com.coroutinedispatcher.marxdown.databinding.ActivityMainBinding
import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var documentsFragment: DocumentsFragment
    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, documentsFragment)
            .commit()
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }
}
