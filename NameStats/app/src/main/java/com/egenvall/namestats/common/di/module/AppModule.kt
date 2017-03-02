package com.egenvall.namestats.common.di.module

import android.app.Application
import android.content.Context
import com.egenvall.namestats.common.threading.AndroidUiExecutor
import com.egenvall.namestats.common.threading.BackgroundExecutor
import com.egenvall.namestats.common.threading.RxIoExecutor
import com.egenvall.namestats.common.threading.UiExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides @Singleton internal fun provideApplicationContext(): Context {
        return application
    }

    @Provides @Singleton internal fun provideUiThread(androidUiThread: AndroidUiExecutor): UiExecutor {
        return androidUiThread
    }

    @Provides @Singleton @Named("ioExecutor") internal fun provideIoExecutor(
            rxIoExecutor: RxIoExecutor): BackgroundExecutor {
        return rxIoExecutor
    }
}