package com.example.todostaskapplication.utils

import com.example.todostaskapplication.models.dbmodels.TodosTable

interface OnItemClickedInterface {
    fun onClickedEvent(todo:TodosTable,operation:String)
}