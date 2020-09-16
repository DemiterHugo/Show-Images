package com.example.animals1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.animals1.R
import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.databinding.ActivityMainBinding
import com.example.animals1.ui.detail.DetailActivity
import com.example.animals1.ui.setVisible
import com.example.animals1.ui.startActivity


class MainActivity : AppCompatActivity(), MainPresenter.View {

private lateinit var binding:ActivityMainBinding
    private val presenter = MainPresenter(this,lifecycleScope)
    private var mediaAdapter = MediaAdapter {presenter.onItemClicked(it)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaAdapter
        presenter.onFilterSelected(Filter.None)
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
        presenter.onFilterSelected(filter)
        return super.onOptionsItemSelected(item)
    }


    override fun setProgressVisible(visible: Boolean) {
        binding.progress.setVisible(visible)
    }

    override fun updateItems(items: List<MediaItem>) {
        mediaAdapter.items = items
    }

    override fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.Extra_IDE to id)
    }
}
