package com.example.todostaskapplication.repository


import com.example.todostaskapplication.models.Todos
import com.example.todostaskapplication.models.dbmodels.TodosTable

interface TodosRepository {
    fun addTodo(todo: TodosTable)
    fun getTodos(): List<TodosTable>
//    fun updateTodo(id: Int, updatedTodo: TodosTable)
    fun deleteTodo(id: Int)
    fun addAllTodos(todo: ArrayList<TodosTable>)
}