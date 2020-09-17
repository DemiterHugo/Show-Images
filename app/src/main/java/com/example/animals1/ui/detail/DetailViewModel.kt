package com.example.animals1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(): ViewModel() {



    private val _item = MutableLiveData<MediaItem>()
    val item: LiveData<MediaItem> get() = _item

    fun onCreate(id: Int){
       viewModelScope.launch {
            val items = withContext(Dispatchers.IO){MediaProvider.getItems()}
             _item.value = items.firstOrNull { mediaItem -> mediaItem.id == id  }
        }
    }
}