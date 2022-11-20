package com.alfonsus.explorelearn.home.Todo

import com.google.gson.annotations.SerializedName

class ResponseCreateTodo (
    @SerializedName("status") val stt:Int,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val pesan:String,
    )