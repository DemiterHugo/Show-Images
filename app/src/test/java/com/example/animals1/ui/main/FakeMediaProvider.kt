package com.example.animals1.ui.main

import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider

class FakeMediaProvider: MediaProvider {
    override fun getItems(): List<MediaItem> = emptyList()

}