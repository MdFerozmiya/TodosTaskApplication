package com.example.todostaskapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.lifecycle.LiveData
import java.util.UUID

fun showToastShort(msg:String,context: Context){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}
fun showToastLong(msg:String,context: Context){
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}
fun getRandomId():String{
    return  UUID.randomUUID().toString()
}

class NetWorkManager(context: Context) : LiveData<Boolean>() {
    override fun onActive() {
        super.onActive()
        checkNetworkConnection()
    }
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private var connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }
    }

    private fun checkNetworkConnection() {
        val network = connectivityManager.activeNetwork
        if (network == null)
            postValue(false)

        val requestBuilder = NetworkRequest.Builder().apply {
            addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        }.build()
        connectivityManager.registerNetworkCallback(requestBuilder, networkCallback)
    }
}