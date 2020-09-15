package com.example.animals1.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals1.R
import com.example.animals1.data.MediaItem
import com.example.animals1.databinding.ViewMediaItemBinding
import com.example.animals1.ui.inflate
import com.example.animals1.ui.loadUrl
import kotlin.properties.Delegates

private typealias Medialistener = (MediaItem)-> Unit

class MediaAdapter(itemss: List<MediaItem> = emptyList(),private val listener: Medialistener): RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    var items: List<MediaItem> by Delegates.observable(itemss){ p, old, new -> notifyDataSetChanged()}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_media_item,false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View, private val listener: Medialistener): RecyclerView.ViewHolder(itemView){

        private val binding = ViewMediaItemBinding.bind(itemView)

        fun bind(mediaItem: MediaItem){
            binding.mediaTitle.text = mediaItem.title
            binding.mediaThumb.loadUrl(mediaItem.url)
            binding.mediaVideoIndicator.visibility = when (mediaItem.type){
                MediaItem.Type.PHOTO -> View.GONE
                MediaItem.Type.VIDEO -> View.VISIBLE
            }

            binding.root.setOnClickListener { listener(mediaItem) }
        }
    }
}