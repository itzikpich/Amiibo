package com.itzikpich.amiiboapiapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase
import kotlinx.coroutines.flow.Flow

@Dao
interface AmiiboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAmiiboList(list: List<AmiiboResponse.Amiibo>)

//    @Query("SELECT * FROM amiibo ")
    @Query("SELECT * FROM amiibo ORDER BY LOWER(id) ASC")
    fun getAmiiboList() : Flow<List<AmiiboResponse.Amiibo>>

    @Query("SELECT * FROM amiibo WHERE isPurchased LIKE 1")
    fun getPurchasedAmiiboList() : List<AmiiboResponse.Amiibo>

    @Query("SELECT COUNT(id) FROM amiibo")
    fun getAmiiboCount() : Int

    @Query("SELECT * FROM amiibo WHERE id= :id")
    fun getAmiiboById(id:String) : Flow<AmiiboResponse.Amiibo>

    @Query("UPDATE amiibo SET isPurchased= :purchased WHERE id= :id")
    fun updateAmiibo(id: String, purchased: Boolean)

    @Query("SELECT * FROM purchase")
    fun getPurchasedIds() : List<Purchase>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPurchase(purchase: Purchase)

    @Query("DELETE FROM amiibo")
    fun clearAmiiboList()

    @Query("DELETE FROM amiibo WHERE id= :id")
    fun clearAmiibo(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAmiibo(amiibo: AmiiboResponse.Amiibo) : Long

}