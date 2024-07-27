package com.example.todostaskapplication.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todostaskapplication.models.CompletedStatus
import com.example.todostaskapplication.models.DeletedResponse
import com.example.todostaskapplication.models.GetTodosResponse
import com.example.todostaskapplication.models.RequestTodo
import com.example.todostaskapplication.models.Todos
import com.example.todostaskapplication.models.dbmodels.TodosTable
import com.example.todostaskapplication.repository.TodosRepositoryImpl
import com.example.todostaskapplication.repository.api.TodosApiRepository
import com.example.todostaskapplication.utils.getRandomId
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val todosRepository: TodosRepositoryImpl,
):ViewModel() {
    private val todosApiRepository: TodosApiRepository = TodosApiRepository

    suspend fun getAllTodosApi():LiveData<GetTodosResponse>{
        val todosData = liveData(Dispatchers.IO){
            val data = todosApiRepository.getAllApiTodos()
            if (data!=null){
                val listOfTodos = ArrayList<TodosTable>()
                data.todos.map {
                    val todo = TodosTable(
                        i = getRandomId(),
                        s = it.todo!!,
                        b = it.completed!!,
                        i1 =it.userId!!,
                        i2 = 0
                    )
                    listOfTodos.add(todo)
                }
               val fetchTodos = todosRepository.getTodos()
                if (fetchTodos.isEmpty()) {
                    todosRepository.addAllTodos(listOfTodos)
                }
            }
            emit(data)
        }
        return todosData
    }
    fun addTodos(todosTable: TodosTable){
        return todosRepository.addTodo(todosTable)
    }
    fun updateTodo(todosTable: TodosTable){
       todosRepository.addTodo(todosTable)
    }
    fun deleteTodo(id: String){
       todosRepository.deleteTodo(id)
    }

     fun getAllTodos():List<TodosTable>{
      return  todosRepository.getTodos()
    }

}

//     fun addTodos(todos: RequestTodo):LiveData<Todos>{
//        val todosData = liveData(Dispatchers.IO) {
//          val api =   todosApiRepository.addTodoApi(todos)
//            Log.i("ResponseForApis","add Todo ${Gson().toJson(api)}")
//            if (api!=null){
//                val todo = TodosTable(
//                    i = AppConstants.getRandomId(),
//                    s = api.todo!!,
//                    b = api.completed!!,
//                    i1 =api.userId!!,
//                    i2 = 0
//                )
//                todosRepository.addTodo(todo)
//            }
//            emit(api)
//        }
//        return todosData
//    }

//     fun updateTodo(id: Int,completedStatus: CompletedStatus):LiveData<Todos>{
//        val todosData = liveData(Dispatchers.IO) {
//            val api =   todosApiRepository.updateTodo(id,completedStatus)
//            Log.i("ResponseForApis","updated todo ${Gson().toJson(api)}")
//            if (api!=null){
//                val todo = TodosTable(
//                    i = AppConstants.getRandomId(),
//                    s = api.todo!!,
//                    b = api.completed!!,
//                    i1 =api.userId!!,
//                    i2 = 0
//                )
//                todosRepository.addTodo(todo)
//            }
//            emit(api)
//        }
//        return todosData
//    }
//     fun deleteTodo(id: Int):LiveData<DeletedResponse>{
//        val todosData = liveData(Dispatchers.IO) {
//            val api =   todosApiRepository.deleteTodo(id)
//            Log.i("ResponseForApis","deleted todo ${Gson().toJson(api)}")
//            if (api!=null){
//                if (api.isDeleted!!){
//                    todosRepository.deleteTodo(id)
//                }
//            }
//            emit(api)
//        }
//        return todosData
//    }