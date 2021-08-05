package com.itzikpich.amiiboapiapp.data.local

import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getAmiiboListFromDB() : Flow<List<AmiiboResponse.Amiibo>>
    suspend fun isAmiiboListEmpty() : Boolean
    suspend fun saveAmiiboList(list: List<AmiiboResponse.Amiibo>)
    suspend fun getAmiiboById(id:String) : Flow<AmiiboResponse.Amiibo>
    suspend fun updateAmiibo(amiibo: AmiiboResponse.Amiibo)
    suspend fun addPurchaseItem(purchase: Purchase)
    suspend fun getPurchasedIds(): HashSet<Purchase>
    suspend fun clearAllAmiiboFromDB()
    suspend fun clearAmiiboFromDB(id: String)
    suspend fun addAmiiboToDB(amiibo: AmiiboResponse.Amiibo) : Long
}