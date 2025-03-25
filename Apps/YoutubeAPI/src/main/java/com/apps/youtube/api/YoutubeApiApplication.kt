package com.apps.youtube.api

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import modules.logger.AndroidLogAdapter
import modules.logger.FormatStrategy
import modules.logger.Logger
import modules.logger.PrettyFormatStrategy
import timber.log.Timber

@HiltAndroidApp
class YoutubeApiApplication : Application() {
    companion object {
        private const val TAG = "MediaConverterApp"

        class CustomTree : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (priority == Log.VERBOSE) {
                    Logger.v(message)
                    return
                }

                if (priority == Log.DEBUG) {
                    Logger.d(message)
                    return
                }

                if (priority == Log.ERROR) {
                    Logger.e(t, message)
                }

                if (priority == Log.WARN) {
                    Logger.w(message)
                }

                if (priority == Log.INFO) {
                    Logger.i(message)
                }

                if (priority == Log.ASSERT) {
                    Logger.wtf(message)
                }
            }
        }

        private class ReleaseTree : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Logger.clearLogAdapters()
            val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(3)
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

            Timber.plant(CustomTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        Timber.tag(TAG).d("onCreate()")

        FirebaseApp.initializeApp(this@YoutubeApiApplication)
    }
}