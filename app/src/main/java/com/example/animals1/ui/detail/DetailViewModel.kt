package com.example.animals1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import com.example.animals1.data.MediaProviderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailViewModel(private val mediaProvider: MediaProvider = MediaProviderImpl,
                      private val ioDispatcher: CoroutineContext = Dispatchers.IO): ViewModel() {

    private val _item = MutableLiveData<MediaItem>()
    val item: LiveData<MediaItem> get() = _item

    fun onCreate(id: Int){
       viewModelScope.launch {
            val items = withContext(ioDispatcher){mediaProvider.getItems()}
             _item.value = items.firstOrNull { mediaItem -> mediaItem.id == id  }
        }
    }
}