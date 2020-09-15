package com.example.animals1.data

sealed class Filter {
    class ByType(val type: MediaItem.Type):Filter()
    object None: Filter()
}