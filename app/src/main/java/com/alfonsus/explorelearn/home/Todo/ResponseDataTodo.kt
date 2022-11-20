package com.alfonsus.explorelearn.home.Todo

import com.google.gson.annotations.SerializedName

data class ResponseDataTodo (
    @SerializedName("status") val stt:String,val totalData: Int, val data:List<Todo>
)