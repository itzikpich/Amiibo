package com.itzikpich.amiiboapiapp.view_models

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzikpich.amiiboapiapp.data.Repository
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var imageUri = MutableLiveData<Uri>()
    var isLoading = MutableLiveData(false)
    var showMessage: MutableLiveData<String?> = MutableLiveData(null)

    /** add new Amiibo item with id and title to db,
     * id must start with 16 chars of "0", in order to retrieve them first
      */
    fun addAmiibo(title: String) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isLoading.postValue(true)
                val i = repository.addAmiiboToDB(
                    AmiiboResponse.Amiibo("0000000000000000${title}_${System.currentTimeMillis()}",
                        title,
                        imageUri.value.toString(),
                    )
                )
                showMessage(if (i > 0) "Item saved succesfully" else "Failed saving item")
                isLoading.postValue(false)
            }
        }

    fun showMessage(msg: String) = showMessage.postValue(msg)

}