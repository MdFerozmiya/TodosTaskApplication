package com.example.todostaskapplication.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {
    fun main(work : suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main+ CoroutineExceptionHandler{ _,_-> }).launch { work() }
    fun io(work : suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO+ CoroutineExceptionHandler{ _, _->}).launch { work() }
}