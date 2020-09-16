package com.example.animals1.ui.detail

import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter(private val view: View,private val scope: CoroutineScope) {

    interface View{
        fun setTitle(title: String)
        fun setImage(url: String)
        fun setDetailIndicatorVisible(visible: Boolean)
    }

    fun onCreate(id: Int){
        scope.launch {
            val items = withContext(Dispatchers.IO){MediaProvider.getItems()}
            val item = items.firstOrNull { mediaItem -> mediaItem.id == id  }
            item?.let {
                view.setTitle(item.title)
                view.setImage(item.url)
                view.setDetailIndicatorVisible(item.type == MediaItem.Type.VIDEO)
            }
        }
    }
}