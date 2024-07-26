package com.example.todostaskapplication

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import com.example.todostaskapplication.models.dbmodels.TodosTable
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Inject


@HiltAndroidApp
class TodosTaskApplication :Application(){
    @Inject
    lateinit var workerFactory : HiltWorkerFactory
    companion object{
        lateinit var realm: Realm
    }
    override fun onCreate() {
        super.onCreate()
        val config = RealmConfiguration.create(schema = setOf(TodosTable::class))
         realm = Realm.open(
             configuration = config)
        println("Application Open")
    }

}