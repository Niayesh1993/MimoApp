package com.example.mimosampleapp.utility

import android.content.Context
import android.net.ConnectivityManager
import java.lang.ref.WeakReference

class Utils(context: Context) {

    private val context: Context = context

    private var myWeakInstance: WeakReference<Utils>? = null

    fun getInstance(context: Context): Utils? {
        if (myWeakInstance == null || myWeakInstance!!.get() == null) {
            myWeakInstance = WeakReference(Utils(context.applicationContext))
        }
        return myWeakInstance!!.get()
    }


    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}