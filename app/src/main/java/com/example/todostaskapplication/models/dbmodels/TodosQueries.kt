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
        } catch (e:Exception) {
            println(e)
        }
    }
    fun addAllTodos(todo: ArrayList<TodosTable>) {
        try {
            realm.writeBlocking {
                todo.forEach {oneTodo->
                    copyToRealm(oneTodo,updatePolicy = UpdatePolicy.ALL)
                }
            }
        }catch (e:Exception) {
            println(e)
        }
    }
    fun getTodos():List<TodosTable>{
        return realm.query<TodosTable>().find()
    }
    fun deleteTodo(id: String) {
        try {
            realm.writeBlocking {
                val writeTransactionItems = query<TodosTable>("id = '$id'").find()
                delete(writeTransactionItems)
            }
        }catch (e:Exception) {
            println(e)
        }

    }
}


