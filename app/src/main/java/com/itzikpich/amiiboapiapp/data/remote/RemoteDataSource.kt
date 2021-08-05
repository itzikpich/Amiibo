package com.itzikpich.amiiboapiapp.data.remote

import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getAmiiboResponse() : Response<AmiiboResponse>

}