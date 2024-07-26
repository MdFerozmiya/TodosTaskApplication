package com.example.todostaskapplication.repository.api

import com.example.todostaskapplication.api.Service
import com.example.todostaskapplication.models.CompletedStatus
import com.example.todostaskapplication.models.RequestTodo


object TodosApiRepository {
        suspend fun getAllApiTodos()=Service.retrofitService.getAllTodos()
        suspend fun addTodoApi( todo: RequestTodo)=Service.retrofitService.addTodoApi(todo)
        suspend fun updateTodo(id: Int,completedStatus: CompletedStatus)=Service.retrofitService.updateTodo(id,completedStatus)
        suspend fun deleteTodo(id: Int)=Service.retrofitService.deleteTodo(id)
}