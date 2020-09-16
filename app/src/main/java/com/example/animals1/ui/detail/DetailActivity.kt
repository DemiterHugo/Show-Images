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
import com.example.animals1.ui.setVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    private val presenter = DetailPresenter(this,lifecycleScope)
    lateinit var binding: ActivityDetailBinding
    companion object {
        const val Extra_IDE = "DetailActivity:extraId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra(Extra_IDE,-1)

        presenter.onCreate(itemId)
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setImage(url: String) {
        binding.detailThumb.loadUrl(url)
    }

    override fun setDetailIndicatorVisible(visible: Boolean) {
        binding.detailVideoIndicator.setVisible(visible)
    }
}
