package com.example.animals1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.animals1.R
import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.databinding.ActivityMainBinding
import com.example.animals1.ui.detail.DetailActivity
import com.example.animals1.ui.getViewModel
import com.example.animals1.ui.observe
import com.example.animals1.ui.setVisible
import com.example.animals1.ui.startActivity
import org.koin.android.scope.ScopeActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : ScopeActivity() {

private lateinit var binding:ActivityMainBinding
    private  val viewModel: MainViewModel by viewModel()
    private var mediaAdapter = MediaAdapter {viewModel.onItemClicked(it)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(viewModel) {
            observe(progressVisible){ binding.progress.setVisible(it)}
            observe(items){mediaAdapter.items = it}
            observe(navigateToDetail){
                it.getContetIfNotHandled()?.let { ToDetail(it) }}
        }
        binding.recycler.adapter = mediaAdapter
        viewModel.onFilterSelected(Filter.None)
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
        viewModel.onFilterSelected(filter)
        return super.onOptionsItemSelected(item)
    }


    fun ToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.Extra_IDE to id)
    }
}
