package com.example.todostaskapplication.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todostaskapplication.R
import com.example.todostaskapplication.adapters.TodosAdapter
import com.example.todostaskapplication.databinding.ActivityMainBinding
import com.example.todostaskapplication.models.RequestTodo
import com.example.todostaskapplication.models.dbmodels.TodosTable
import com.example.todostaskapplication.utils.AppConstants
import com.example.todostaskapplication.utils.OnItemClickedInterface
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
            this, allTodos,object:OnItemClickedInterface{
                override fun onClickedEvent(todo: TodosTable,operation:String) {
                    if (operation=="delete"){
                        deleteTodoFromList(todo)
                    } else {
                        openPopup(operation)
                    }
                }
            }
        )

        binding.recyclerView.adapter = todosAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

        binding.addTodos.setOnClickListener {
            openPopup("add")
        }
    }
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
        todosAdapter.setTodos(allTodos)
        dialog.dismiss()
        AppConstants.showToastShort("Todo deleted !!",this)
    }
}
@SuppressLint("InflateParams", "MissingInflatedId")
private fun openPopup(screen:String){
    dialog= Dialog(this,R.style.customDialogue)
    val dialogView = LayoutInflater.from(this)
        .inflate(R.layout.add_todo_items, null, false)
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
    val submit: MaterialButton = dialogView.findViewById(R.id.submit_button)
    val todos :ImageView= dialogView.findViewById(R.id.cancel_pop_up)
    val radioGroup: RadioGroup =dialogView.findViewById(R.id.radioGroup)

    if (screen!="add"){
        submit.text = resources.getString(R.string.update)
    } else {
        submit.text = resources.getString(R.string.update)
    }

    submit.setOnClickListener {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
//        if (selectedRadioButtonId != -1) {
//            val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
//            val selectedOption = selectedRadioButton.text
//            Toast.makeText(this, "Selected option: $selectedOption", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        } else {
//            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
//        }
        val todoAdd = RequestTodo(
            todo = "test dummy",
            completed = true,
            userId = 123
        )
        mainViewModel.addTodos(todoAdd)
        allTodos =  mainViewModel.getAllTodos()
        todosAdapter.setTodos(allTodos)
        dialog.dismiss()
        AppConstants.showToastShort("Todo Created",this)
    }

    todos.setOnClickListener {
        dialog.dismiss()
    }

}
}