package com.example.todostaskapplication.utils

import android.content.Context
import android.widget.Toast

object AppConstants {

    fun showToastShort(msg:String,context: Context){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    fun showToastLong(msg:String,context: Context){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }

}