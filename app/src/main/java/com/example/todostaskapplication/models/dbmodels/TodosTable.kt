package com.example.todostaskapplication.models.dbmodels


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID


class TodosTable : RealmObject {
    @PrimaryKey
    var id: String? = UUID.randomUUID().toString()
    var todo: String? = null
    var completed: Boolean? = null
    var userId: Int? = null
    var status: Int = 0

    constructor(i: String, s: String, b: Boolean, i1: Int, i2: Int) : this() {
        id = i
        todo = s
        completed = b
        userId = i1
        status = i2
    }

    constructor()
}