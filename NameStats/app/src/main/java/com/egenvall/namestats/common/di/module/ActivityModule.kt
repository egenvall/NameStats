package com.egenvall.namestats.common.di.module

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.common.threading.AndroidUiExecutor
import com.egenvall.namestats.common.threading.RxIoExecutor
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    /**
     * Exposes the activity to dependents in the graph.
     */
    @Provides @PerScreen internal fun activity(): Activity {
        return activity
    }

    @Provides internal fun uiExecutor() : AndroidUiExecutor {
        return AndroidUiExecutor()
    }

    @Provides internal fun backgroundExecutor() : RxIoExecutor {
        return RxIoExecutor()
    }
}