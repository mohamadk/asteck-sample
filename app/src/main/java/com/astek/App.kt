package com.astek

import android.app.Application
import com.astek.di.MoviesApp

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MoviesApp.init(this)
    }
}
