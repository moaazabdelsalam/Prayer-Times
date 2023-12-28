package com.task.praytimes.times.data.di

import com.task.praytimes.times.data.local.LocalSource
import com.task.praytimes.times.data.local.LocalSourceImp
import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.data.remote.RemoteSourceImp
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.data.repo.RepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerTimesModule {

    @Binds
    abstract fun bindRepo(
        repoImp: RepoImp
    ): Repo

    @Binds
    abstract fun bindRemoteSource(
        remoteSourceImp: RemoteSourceImp
    ): RemoteSource

    @Binds
    abstract fun bindLocalSource(
        localSourceImp: LocalSourceImp
    ): LocalSource
}