package com.example.todostaskapplication.repository

import com.example.todostaskapplication.models.Todos
import com.example.todostaskapplication.models.dbmodels.TodosQueries
import com.example.todostaskapplication.models.dbmodels.TodosTable
import javax.inject.Inject

class TodosRepositoryImpl @Inject constructor() :TodosRepository{
    private val todosQueries: TodosQueries = TodosQueries

    override fun addTodo(todo: TodosTable) =todosQueries.addTodo(todo)
    override fun addAllTodos(todo: ArrayList<TodosTable>) =todosQueries.addAllTodos(todo)


    override fun getTodos(): List<TodosTable> =todosQueries.getTodos()

//    override fun updateTodo(id: Int, updatedTodo: TodosTable)=todosQueries.updateTodo(id,updatedTodo)

    override fun deleteTodo(id: String)=todosQueries.deleteTodo(id)
}