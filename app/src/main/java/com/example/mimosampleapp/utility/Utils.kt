package com.example.mimosampleapp.utility

import android.content.Context
import android.net.ConnectivityManager
import java.lang.ref.WeakReference

class Utils(context: Context) {

    private val context: Context = context

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}