package com.example.animals1.data

import androidx.annotation.WorkerThread

object MediaProvider {

    //"https://placekitten.com/200/200?image=$it"

    @WorkerThread
    fun getItems():List<MediaItem>{
        return (1 until 11).map {
            MediaItem(it,
                "Title $it",
                "https://placekitten.com/200/200?image=$it",
                if(it%3==0)MediaItem.Type.VIDEO else MediaItem.Type.PHOTO)
        }
    }
}