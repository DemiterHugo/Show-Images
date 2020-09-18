package com.example.animals1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import com.example.animals1.ui.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel(){

    private val _progessVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progessVisible

    private val _items = MutableLiveData<List<MediaItem>>()
    val items: LiveData<List<MediaItem>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail


    fun onFilterSelected(filter: Filter){
        viewModelScope.launch {
            _progessVisible.value = true
            _items.value = withContext(Dispatchers.IO){getFilteredItems(filter)}
            _progessVisible.value = false
        }
    }

    private fun getFilteredItems(filter: Filter):List<MediaItem>{
        return MediaProvider.getItems().let {
            when(filter){
                Filter.None -> it
                is Filter.ByType -> it.filter { mediaItem -> mediaItem.type == filter.type }
            }
        }
    }

    fun onItemClicked(item: MediaItem){
        _navigateToDetail.value = Event(item.id)
    }
}