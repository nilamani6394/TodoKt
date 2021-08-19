package com.app.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.app.common.data.prefs.PrefsHelper

class MyApplication : Application() {

    private lateinit var prefs: PrefsHelper

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        prefs = PrefsHelper.getInstance(this)
    }

    companion object {
        private var mInstance: MyApplication? = null

        @Synchronized
        fun getInstance(): MyApplication {
            return mInstance!!
        }
    }

    fun getPrefs() = prefs

    fun inNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            return actNw != null
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}