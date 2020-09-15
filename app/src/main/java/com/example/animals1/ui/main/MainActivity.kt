package com.example.animals1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.animals1.R
import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.data.MediaProvider
import com.example.animals1.databinding.ActivityMainBinding
import com.example.animals1.ui.detail.DetailActivity
import com.example.animals1.ui.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

private lateinit var binding:ActivityMainBinding
    private val mediaAdapter = MediaAdapter {itemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaAdapter
        updateItems()
    }

    fun itemClicked(mediaItem: MediaItem){
        startActivity<DetailActivity>("Extra_IDE" to mediaItem.id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //guardar el tipo de filter seleccionado y pasarselo a updateItems

        val filter = when(item.itemId){
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }
        updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    private fun updateItems(filter: Filter = Filter.None){
        //coger los datos del servidor y actualizar los items del adapter.
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            mediaAdapter.items = withContext(Dispatchers.IO){getFilteredItems(filter)}
            binding.progress.visibility = View.GONE
        }

    }

    private fun getFilteredItems(filter: Filter): List<MediaItem>{
        //coger los datos del sevidor y devolver solo una lista filtrada
        return MediaProvider.getItems().let {
            when(filter){
                Filter.None -> it
                is Filter.ByType -> it.filter { mediaItem -> mediaItem.type == filter.type }
            }
        }
    }
}
