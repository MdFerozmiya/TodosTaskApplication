package com.example.todostaskapplication.models

import com.google.gson.annotations.SerializedName

data class GetTodosResponse(
    @SerializedName("todos" ) var todos : ArrayList<Todos> = arrayListOf(),
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("skip"  ) var skip  : Int?             = null,
    @SerializedName("limit" ) var limit : Int?             = null
)

data class Todos(
    @SerializedName("id"        ) var id        : Int?     = null,
    @SerializedName("todo"      ) var todo      : String?  = null,
    @SerializedName("completed" ) var completed : Boolean? = null,
    @SerializedName("userId"    ) var userId    : Int?     = null
)
data class RequestTodo(
    @SerializedName("todo"      ) var todo      : String?  = null,
    @SerializedName("completed" ) var completed : Boolean? = null,
    @SerializedName("userId"    ) var userId    : Int?     = null
)

data class DeletedResponse(
    @SerializedName("todo"         ) var todo      : String?  = null,
    @SerializedName("completed"    ) var completed : Boolean? = null,
    @SerializedName("userId"       ) var userId    : Int?     = null,
    @SerializedName("isDeleted"    ) var isDeleted    : Boolean?     = null
)

data class CompletedStatus(
    val completed: Boolean
)