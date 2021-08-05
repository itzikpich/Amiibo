package com.itzikpich.amiiboapiapp.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzikpich.amiiboapiapp.data.Repository
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val amiibo = MutableLiveData<AmiiboResponse.Amiibo>()

    // use flow to listen to live updates when item is purchased
    fun getAmiiboById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAmiiboById(id).collect {
                    amiibo.postValue(it)
                }
            }
        }
    }

    // update amiibo item to db
    fun updateAmiibo(amiibo: AmiiboResponse.Amiibo) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateAmiibo(amiibo)
            }
        }

    // add item to purchase table
    fun savePurchaseToDb(purchase: Purchase) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addPurchaseItem(purchase)
            }
        }

}