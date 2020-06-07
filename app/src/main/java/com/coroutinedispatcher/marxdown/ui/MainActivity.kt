package com.coroutinedispatcher.marxdown.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coroutinedispatcher.marxdown.MarxDownApplication
import com.coroutinedispatcher.marxdown.R
import com.coroutinedispatcher.marxdown.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, MarxDownApplication.getDocumentsFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }
}
