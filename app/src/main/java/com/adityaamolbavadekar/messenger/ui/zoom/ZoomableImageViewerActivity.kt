/*
 *    Copyright 2023 Aditya Bavadekar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.adityaamolbavadekar.messenger.ui.zoom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.databinding.ActivityPictureUploadPreviewBinding
import com.adityaamolbavadekar.messenger.ui.picture_upload_preview.PictureUploadPreviewActivity.Companion.getUriList
import com.adityaamolbavadekar.messenger.ui.picture_upload_preview.ViewPagerAdapters
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity

/**
 * **REQUIRED EXTRAS :** [Intent.EXTRA_ORIGINATING_URI]
 *
 * Optional : [Intent.EXTRA_TEXT] (for activity title)
 *
 * */
class ZoomableImageViewerActivity : BaseActivity() {

    private lateinit var binding: ActivityPictureUploadPreviewBinding
    private val smallPagerAdapter =
        ViewPagerAdapters.ImageSliderPagerAdapter { onShouldSwitchVisibleImage(it) }
    private val largePagerAdapter = ViewPagerAdapters.ImageSliderPagerAdapter()

    private fun onShouldSwitchVisibleImage(imageUri: Uri) {
        if (largePagerAdapter.itemCount <= 1) return
        binding.imagesSliderLarge.currentItem = largePagerAdapter.currentList.indexOf(imageUri)
    }


    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        binding = ActivityPictureUploadPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val photoUrls = requireNotNull(intent.getStringArrayExtra(Intent.EXTRA_STREAM))
        { "Urls are required." }
        binding.input.isGone = true
        binding.imagesSliderLarge.adapter = largePagerAdapter
        binding.imagesSliderSmall.adapter = smallPagerAdapter
        binding.imagesSliderSmall.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val urisList = getUriList(photoUrls)
        largePagerAdapter.submitList(urisList)
        smallPagerAdapter.submitList(urisList)
        if (urisList.size == 1) {
            binding.imagesSliderSmall.isGone = true
        } else {
            binding.imagesSliderSmall.isVisible = true
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.toolbar.title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        binding.toolbar.subtitle = intent.getStringExtra(EXTRA_SUBTITLE) ?: ""
    }

    companion object {
        fun createNewIntent(
            context: Context,
            urlList: List<String>,
            extraTitle: String? = null,
            extraSubtitle: String? = null
        ): Intent {
            return Intent(context, ZoomableImageViewerActivity::class.java)
                .putExtra(Intent.EXTRA_STREAM, urlList.toTypedArray())
                .putExtra(EXTRA_TITLE, extraTitle)
                .putExtra(EXTRA_SUBTITLE, extraSubtitle)
        }
        private const val TAG = "ZoomableImageViewerActivity"
        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_SUBTITLE = "extra_subtitle"

    }

}