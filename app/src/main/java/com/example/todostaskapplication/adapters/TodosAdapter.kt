package com.example.todostaskapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todostaskapplication.R
import com.example.todostaskapplication.databinding.TodoListItemsBinding
import com.example.todostaskapplication.models.dbmodels.TodosTable
import com.example.todostaskapplication.utils.OnItemClickedInterface

class TodosAdapter(
    private val context: Context,
    private var todoList :List<TodosTable>,
    private val onItemClickedInterface : OnItemClickedInterface
):RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {

    inner class TodosViewHolder(val binding: TodoListItemsBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        return TodosViewHolder(TodoListItemsBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater,
                parent,
                false
        ))
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
       val view =  holder.binding
        if (todoList[holder.adapterPosition].completed!!){
            view.linearLayout.background = context.resources.getDrawable(R.drawable.bg_for_todos_completed)
        }else{
            view.linearLayout.background = context.resources.getDrawable(R.drawable.todo_item_bg)
        }
        view.ivDelete.setOnClickListener {
            onItemClickedInterface.onClickedEvent(todoList[holder.adapterPosition],"delete")
        }
        view.ivUpdate.setOnClickListener {
            onItemClickedInterface.onClickedEvent(todoList[holder.adapterPosition],"update")
        }
    }
    override fun getItemCount()=todoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setTodos(list: List<TodosTable>){
        todoList =list
        notifyDataSetChanged()
    }

}