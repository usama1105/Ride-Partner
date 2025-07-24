package com.example.ridepartner

import android.app.Application

class APP:Application(){
    override fun onCreate() {
        super.onCreate()
        DIManager.initilize(this)
    }
}