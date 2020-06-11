package com.coroutinedispatcher.marxdown.di

import com.coroutinedispatcher.marxdown.ui.documents.DocumentsFragment
import com.coroutinedispatcher.marxdown.ui.editor.EditorFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AndroidModule {
    @Provides
    fun provideDocumentsFragment(): DocumentsFragment = DocumentsFragment()

    @Provides
    fun provideEditorFragment(): EditorFragment = EditorFragment()
}