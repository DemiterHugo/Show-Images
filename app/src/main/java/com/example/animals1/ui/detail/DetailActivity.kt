package com.example.animals1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.animals1.data.MediaItem
import com.example.animals1.databinding.ActivityDetailBinding
import com.example.animals1.ui.loadUrl
import com.example.animals1.ui.setVisible

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    lateinit var binding: ActivityDetailBinding

    companion object {
        const val Extra_IDE = "DetailActivity:extraId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get<DetailViewModel>().apply {
            item.observe(this@DetailActivity, Observer {
                supportActionBar?.title = it.title
                binding.detailThumb.loadUrl(it.url)
                binding.detailVideoIndicator.setVisible(it.type == MediaItem.Type.VIDEO)
            })
        }

        val itemId = intent.getIntExtra(Extra_IDE,-1)

        viewModel.onCreate(itemId)
    }
}
