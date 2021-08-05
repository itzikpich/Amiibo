package com.itzikpich.amiiboapiapp.data.remote

import com.itzikpich.amiiboapiapp.data.RetrofitService
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl@Inject constructor(private val apiService: RetrofitService):
    RemoteDataSource, RetrofitService by apiService {

    override suspend fun getAmiiboResponse(): Response<AmiiboResponse> = getData()

}