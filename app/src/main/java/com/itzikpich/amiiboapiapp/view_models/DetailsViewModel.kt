package com.itzikpich.amiiboapiapp.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzikpich.amiiboapiapp.data.Repository
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    val amiibo = MutableLiveData<AmiiboResponse.Amiibo>()

    init {
        val id = requireNotNull(savedStateHandle.get<String>("id"))
        getAmiiboById(id)
    }

    // use flow to listen to live updates when item is purchased
    private fun getAmiiboById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAmiiboById(id).collect {
                    amiibo.postValue(it)
                }
            }
        }
    }

    // update amiibo item to db
    private fun updateAmiibo(amiibo: AmiiboResponse.Amiibo) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateAmiibo(amiibo)
            }
        }

    // add item to purchase table
    private fun savePurchaseToDb(purchase: Purchase) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addPurchaseItem(purchase)
            }
        }

    fun onItemClicked() {
        amiibo.value?.let { amiibo ->
            if (!amiibo.isPurchased) {
                savePurchaseToDb(Purchase(amiibo.id)) // add purchase to Purchased
                updateAmiibo(amiibo.copy(isPurchased = true)) // update Amiibo item
            }
        }
    }

}