package com.example.project1

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

//check for internet connection
class Functions {
    fun isConnecting(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected

//        //check for internet connection paste to the activity to check
//        if (Functions.isConnecting(this)) {
//// you have internet connection // do your stuff here
//        } else {
//            Toast.makeText(this, "No internet connecion.", Toast.LENGTH_LONG).show();
//        }

    }
}