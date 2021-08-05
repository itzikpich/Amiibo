package com.itzikpich.amiiboapiapp.view_models

import androidx.lifecycle.*
import com.itzikpich.amiiboapiapp.data.Repository
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel() {

    var amiiboList: MutableLiveData<List<AmiiboResponse.Amiibo>> = MutableLiveData()
    var filteredAmiiboList = listOf<AmiiboResponse.Amiibo>()
    var isLoading = MutableLiveData(false)
    var isFiltering = false
    var showMessage = MutableLiveData("Long click to remove item from the list")

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAmiiboList() // get list from local or network
            }
        }
    }

    fun clearTableAndRefetchAmiiboResponseFromNetwork() {
        isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAmiiboResponse()
                isLoading.postValue(false)
            }
        }
    }

    private suspend fun getAmiiboList() {
        isLoading.postValue(true)
        // if list of amiibos is empty fetch data from network
        if(repository.isAmiiboListEmpty()) {
            repository.getAmiiboResponse() // try fetching data from network and update the db
        }
        isLoading.postValue(false)
        // observe amiibo table on db, using flow to get live updates
        repository.getAmiiboListFromDB().collect { list ->
            filteredAmiiboList = list.filter { it.isPurchased }
            amiiboList.postValue(list)
        }
    }

    fun clearAmiiboByIdFromDB(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clearAmiiboFromDB(id)
            }
        }
    }
}