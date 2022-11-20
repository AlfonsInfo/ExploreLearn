package com.alfonsus.explorelearn.home.Todo

class TodoApi {
    companion object{
        val BASE_URL = "https://elearning-pbp.herokuapp.com/"

        val GET_ALL_URL = BASE_URL + "todolists"
        val GET_BY_ID_URL = BASE_URL + "todolists/"
        val DELETE_URL = BASE_URL + "todolists/"
        val UPDATE_URL = BASE_URL + "todolists/"
        val ADD_URL = BASE_URL + "todolists/"
    }
}