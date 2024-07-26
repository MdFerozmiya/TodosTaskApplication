package com.example.todostaskapplication.models.dbmodels

import com.example.todostaskapplication.TodosTaskApplication
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query

object TodosQueries {
    private val realm = TodosTaskApplication.realm

    fun addTodo(todos: TodosTable) {
        try {
            realm.writeBlocking {
                copyToRealm(todos, updatePolicy = UpdatePolicy.ALL)
            }
        } finally {
//            realm.close()
        }

    }
    fun addAllTodos(todo: ArrayList<TodosTable>) {
        try {
            realm.writeBlocking {
                todo.forEach {oneTodo->
                    copyToRealm(oneTodo,updatePolicy = UpdatePolicy.ALL)
                }
            }
        } finally {
//            realm.close()
        }
    }

    fun getTodos():List<TodosTable>{
      return try {
            realm.query<TodosTable>().find()
        } finally {
//            realm.close()
        }
    }

//    fun getTodosFlow(): Flow<List<TodosTable>> {
//        return realm
//            .query<TodosTable>()
//            .asFlow()
//            .map {result->
//                result.list.toList()
//            }
//    }

//    fun updateTodo(id: Int, updatedTodo: TodosTable) {
//        realm.executeTransaction { transaction ->
//            val todo = transaction.where(TodosTable::class.java).equalTo("id", id).findFirst()
//            todo?.let {
//                it.todo = updatedTodo.todo
//                it.completed = updatedTodo.completed
//                it.userId = updatedTodo.userId
//                it.status = updatedTodo.status
//            }
//        }
//    }

    fun deleteTodo(id: Int) {
        try {
            realm.writeBlocking {
                val writeTransactionItems = query<TodosTable>("id = $id").find()
                delete(writeTransactionItems)
            }
        }finally {
//            realm.close()
        }

    }
}


