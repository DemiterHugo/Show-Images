package com.example.animals1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.animals1.R
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import com.example.animals1.databinding.ActivityDetailBinding
import com.example.animals1.ui.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    companion object {
        const val Extra_IDE = "palomo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra(Extra_IDE,-1)
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO){MediaProvider.getItems()}
            val item = items.firstOrNull{
                it.id == itemId
            }
            item?.let {
                supportActionBar?.title = it.title
                binding.detailThumb.loadUrl(it.url)
                binding.detailVideoIndicator.visibility = when(it.type){
                    MediaItem.Type.PHOTO -> View.GONE
                    MediaItem.Type.VIDEO -> View.VISIBLE
                }
            }
        }






    }
}
