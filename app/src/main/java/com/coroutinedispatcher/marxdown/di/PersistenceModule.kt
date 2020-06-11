package com.coroutinedispatcher.marxdown.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.realm.Realm

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {
    @Provides
    fun getRealmInstance(): Realm = Realm.getDefaultInstance().freeze()
}