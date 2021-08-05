package com.itzikpich.amiiboapiapp.data

import com.itzikpich.amiiboapiapp.data.local.LocalDataSource
import com.itzikpich.amiiboapiapp.data.remote.RemoteDataSource
import com.itzikpich.amiiboapiapp.models.Purchase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
    ) : LocalDataSource by localDataSource {

        suspend fun getAmiiboResponse() {
            val purchased = getPurchasedIds()
            try {
                remoteDataSource.getAmiiboResponse().let { response ->
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.amiiboList?.let { list ->
                            localDataSource.clearAllAmiiboFromDB()
                            localDataSource.saveAmiiboList(
                                list.mapNotNull {
                                    it?.copy(
                                        id = (it.head + it.tail),
                                        isPurchased = purchased.contains(Purchase((it.head + it.tail)))
                                    )
                                }
                            )
                        }
                    }
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }

}