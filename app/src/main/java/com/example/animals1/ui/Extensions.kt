package com.example.animals1.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import com.bumptech.glide.Glide

fun ViewGroup.inflate(layoutRes: Int, attach:Boolean=false):View{
   return LayoutInflater.from(this.context).inflate(layoutRes, this,false)
}

fun ImageView.loadUrl(url: String){
    Glide.with(this).load(url).into(this)
}

inline fun <reified T:Activity>Context.startActivity(vararg pairs: Pair<String,Any?>){
    val intent =Intent(this,T::class.java)
        .apply { putExtras(bundleOf(*pairs)) }
        .also ( ::startActivity)
}

fun View.setVisible(visible: Boolean){
    visibility = if(visible) View.VISIBLE else View.GONE
}

inline fun <reified T:ViewModel>ViewModelStoreOwner.getViewModel(body: T.() -> Unit = {}):T{
    return ViewModelProvider(this).get<T>().apply(body)
}

fun <T>LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit){
    liveData.observe(this, Observer(observer))
}
