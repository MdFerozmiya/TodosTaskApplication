package com.example.todostaskapplication.models.dbmodels


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class TodosTable : RealmObject {
    @PrimaryKey
    var id: Int? = null
    var todo: String? = null
    var completed: Boolean? = null
    var userId: Int? = null
    var status: Int = 0

    constructor(i: Int, s: String, b: Boolean, i1: Int, i2: Int) : this() {
        id = i
        todo = s
        completed = b
        userId = i1
        status = i2
    }

    constructor()
}