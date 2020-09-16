package com.example.animals1.ui.main

import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(private val view: View, private val scope: CoroutineScope) {

    interface View{
        fun setProgressVisible(visible: Boolean)
        fun updateItems(items: List<MediaItem>)
        fun navigateToDetail(id: Int)
    }

    fun onFilterSelected(filter: Filter){
        scope.launch {
            view.setProgressVisible(true)
            val items = withContext(Dispatchers.IO){getFilteredItems(filter)}
            view.updateItems(items)
            view.setProgressVisible(false)
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
        view.navigateToDetail(item.id)
    }
}