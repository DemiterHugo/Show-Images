package com.example.animals1.ui

data class Event<out T>(private val contet: T) {

    private var hasBeenHandled = false

    fun getContetIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            contet
        }
    }
}