package com.itzikpich.amiiboapiapp.data

import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("amiibo")
    suspend fun getData() : Response<AmiiboResponse>

}