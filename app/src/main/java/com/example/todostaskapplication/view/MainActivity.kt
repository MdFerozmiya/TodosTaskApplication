package com.example.todostaskapplication.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todostaskapplication.R
import com.example.todostaskapplication.adapters.TodosAdapter
import com.example.todostaskapplication.databinding.ActivityMainBinding
import com.example.todostaskapplication.models.dbmodels.TodosTable
import com.example.todostaskapplication.utils.OnItemClickedInterface
import com.example.todostaskapplication.utils.getRandomId
import com.example.todostaskapplication.utils.showToastShort
import com.example.todostaskapplication.view.viewmodels.MainViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dialog :Dialog
    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var todosAdapter : TodosAdapter
    private lateinit var allTodos :List<TodosTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allTodos =  mainViewModel.getAllTodos()

        todosAdapter= TodosAdapter(
            this, emptyList(),object:OnItemClickedInterface{
                override fun onClickedEvent(todo: TodosTable,operation:String) {
                    if (operation=="delete"){
                        deleteTodoFromList(todo)
                    } else {
                        openPopup(operation,todo)
                    }
                }
            }
        )

        binding.recyclerView.adapter = todosAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        if (allTodos.isNotEmpty()) {
            todosAdapter.setTodos(allTodos)
        }else{
            binding.noTodosLayout.visibility=View.VISIBLE
        }

        binding.addTodos.setOnClickListener {
            openPopup("add", TodosTable())
        }
    }
@SuppressLint("InflateParams")
private fun deleteTodoFromList(todo: TodosTable) {
    dialog= Dialog(this,R.style.customDialogue)
    val dialogView = LayoutInflater.from(this)
        .inflate(R.layout.delete_todo_pop_up, null, false)
    dialog.setCancelable(true)
    dialog.setContentView(dialogView)
    val window = dialog.window
    if (window!=null) {
        val layoutParems = WindowManager.LayoutParams()
        layoutParems.copyFrom(window.attributes)
        layoutParems.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParems.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParems
    }

    dialog.show()
    val no: MaterialButton = dialogView.findViewById(R.id.cancel_button)
    val yes :MaterialButton= dialogView.findViewById(R.id.yes_button)

    no.setOnClickListener {
        dialog.dismiss()
    }
    yes.setOnClickListener {
        mainViewModel.deleteTodo(todo.id!!)
        allTodos =  mainViewModel.getAllTodos()
        if (allTodos.isEmpty()){
            binding.noTodosLayout.visibility=View.VISIBLE
        }
        todosAdapter.setTodos(allTodos)
        dialog.dismiss()
        showToastShort(getString(R.string.todo_deleted),this)
    }
}
@SuppressLint("InflateParams", "MissingInflatedId")
private fun openPopup(screen:String,todosTable: TodosTable){
    dialog= Dialog(this,R.style.customDialogue)
    val dialogView = LayoutInflater.from(this)
        .inflate(R.layout.add_todo_items, null, false)
    dialog.setCancelable(true)
    dialog.setContentView(dialogView)
    val window = dialog.window
    if (window!=null) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
    }
    dialog.show()
    val submit: MaterialButton = dialogView.findViewById(R.id.submit_button)
    val popCancel :ImageView= dialogView.findViewById(R.id.cancel_pop_up)
    val yesRadio: RadioButton =dialogView.findViewById(R.id.yes_button)
    val noRadio: RadioButton =dialogView.findViewById(R.id.no_button)
    val todoText = dialogView.findViewById<AutoCompleteTextView>(R.id.autocomplete_todos)
    if (screen!="add"){
        submit.text = resources.getString(R.string.update)
        todoText.setText(todosTable.todo)
    } else {
        submit.text = resources.getString(R.string.submit)
    }

    submit.setOnClickListener {
        if (todoText.text.isNullOrEmpty()){
            todoText.error = getString(R.string.todo_cannot_be_empty)
            showToastShort(getString(R.string.enter_todo_text),this)
        }else if (!yesRadio.isChecked && !noRadio.isChecked){
            showToastShort(getString(R.string.select_the_completed_option),this)
        }
        else if (!todoText.text.isNullOrEmpty() && (yesRadio.isChecked || noRadio.isChecked) ){
            var todoId = getRandomId()
            var completedStatus = false
            var userId=25
            var msg = getString(R.string.todo_created_successfully)
            if (screen!="add"){
                todoId = todosTable.id!!
                userId = todosTable.userId!!
                msg =getString(R.string.todo_updated_successfully)
            }
            completedStatus = yesRadio.isChecked

            val todoAdd = TodosTable(
                i =todoId ,
                s=todoText.text.toString(),
                b=completedStatus,
                i1=userId,
                i2 =0
            )
            mainViewModel.addTodos(todoAdd)
            allTodos =  mainViewModel.getAllTodos()
            binding.noTodosLayout.visibility=View.GONE
            todosAdapter.setTodos(allTodos)
            dialog.dismiss()
            showToastShort(msg,this)
        }


    }

    popCancel.setOnClickListener {
        dialog.dismiss()
    }

}
}