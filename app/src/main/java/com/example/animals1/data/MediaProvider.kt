package com.example.animals1.data

import androidx.annotation.WorkerThread

interface MediaProvider{
    fun getItems(): List<MediaItem>
}


object MediaProviderImpl: MediaProvider {

    @WorkerThread
    override fun getItems():List<MediaItem>{
        return (1 until 11).map {
            MediaItem(it,
                "Title $it",
                //"https://placekitten.com/200/200?image=$it",
                "http://placeimg.com/640/480?animals=$it",
                if(it%3==0)MediaItem.Type.VIDEO else MediaItem.Type.PHOTO)
        }
    }
}