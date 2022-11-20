package com.alfonsus.explorelearn.home.Kelas

import com.google.gson.annotations.SerializedName

data class ResponseKelas (
    @SerializedName("status") val stt:String,val totalData: Int, val data:List<Kelas>
)