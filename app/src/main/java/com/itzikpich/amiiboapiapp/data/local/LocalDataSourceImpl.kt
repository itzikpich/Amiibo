package com.itzikpich.amiiboapiapp.data.local

import com.itzikpich.amiiboapiapp.db.AmiiboDatabase
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val db: AmiiboDatabase): LocalDataSource {

    override suspend fun getAmiiboListFromDB(): Flow<List<AmiiboResponse.Amiibo>> =
        db.amiiboDao().getAmiiboList()

    override suspend fun isAmiiboListEmpty(): Boolean =
        db.amiiboDao().getAmiiboCount() == 0

    override suspend fun saveAmiiboList(list: List<AmiiboResponse.Amiibo>) =
        db.amiiboDao().saveAmiiboList(list)

    override suspend fun getAmiiboById(id: String): Flow<AmiiboResponse.Amiibo> =
        db.amiiboDao().getAmiiboById(id)

    override suspend fun updateAmiibo(amiibo: AmiiboResponse.Amiibo) =
        db.amiiboDao().updateAmiibo(amiibo.id, amiibo.isPurchased)

    override suspend fun getPurchasedIds(): HashSet<Purchase> =
        db.amiiboDao().getPurchasedIds().toHashSet()

    override suspend fun clearAllAmiiboFromDB() =
        db.amiiboDao().clearAmiiboList()

    override suspend fun clearAmiiboFromDB(id: String) =
        db.amiiboDao().clearAmiibo(id)

    override suspend fun addPurchaseItem(purchase: Purchase) =
        db.amiiboDao().addPurchase(purchase)

    override suspend fun addAmiiboToDB(amiibo: AmiiboResponse.Amiibo) =
        db.amiiboDao().addAmiibo(amiibo)

}