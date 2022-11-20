package com.alfonsus.explorelearn.home.Kelas

class KelasApi {
    companion object {
        val BASE_URL = "https://elearning-pbp.herokuapp.com/"

        val GET_ALL_URL = BASE_URL + "kelas"
        val GET_BY_ID_URL = BASE_URL + "kelas/"
        val ADD_URL = BASE_URL + "kelas"
        val UPDATE_URL = BASE_URL + "kelas/"
        val DELETE_URL = BASE_URL + "kelas/"
    }
}