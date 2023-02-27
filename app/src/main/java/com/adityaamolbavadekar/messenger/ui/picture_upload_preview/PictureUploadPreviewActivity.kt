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

package com.adityaamolbavadekar.messenger.ui.picture_upload_preview

import android.app.Activity
import android.content.Intent
import android.content.Intent.EXTRA_STREAM
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.databinding.ActivityPictureUploadPreviewBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.ui.registration.IsoCountrySelectionFragment
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity

class PictureUploadPreviewActivity : BaseActivity() {

    private lateinit var binding: ActivityPictureUploadPreviewBinding
    private var imageUrisList = mutableListOf<Uri>()
    private val smallPagerAdapter =
        ViewPagerAdapters.ImageSliderPagerAdapter(
            ::onShouldSwitchVisibleImage,
            ::onShouldRemoveImage
        )
    private val largePagerAdapter = ViewPagerAdapters.ImageSliderPagerAdapter()

    private fun onShouldSwitchVisibleImage(imageUri: Uri) {
        if (largePagerAdapter.itemCount <= 1) return
        binding.imagesSliderLarge.currentItem = largePagerAdapter.currentList.indexOf(imageUri)
    }

    private fun onShouldRemoveImage(imageUri: Uri) {
        if (largePagerAdapter.itemCount <= 1) return
        Dialogs.showRemoveImageDialog(this) { shouldRemove ->
            if (shouldRemove) {
                imageUrisList.remove(imageUri)
                setupViewPagers()
            }
        }
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        binding = ActivityPictureUploadPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val photoUrls =
            requireNotNull(intent.getStringArrayExtra(EXTRA_STREAM)) { "Urls are required." }
        binding.input.setOnSendListener {
            val extras = Intent()
            // TODO binding.composeBar.extraLinkInfo?.let { info -> message.addLinkInfo(info) }
            if (binding.input.composeText?.toString()?.trim()?.isNotEmpty() == true) {
                extras.putExtra(EXTRA_TEXT, binding.input.composeText!!.toString())
            }
            extras.putExtra(EXTRA_STREAM, imageUrisList.toTypedArray())
            setResult(Activity.RESULT_OK, extras)
            finish()
        }
        binding.imagesSliderLarge.adapter = largePagerAdapter
        binding.imagesSliderSmall.adapter = smallPagerAdapter
        binding.imagesSliderSmall.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val urisList = getUriList(photoUrls)
        imageUrisList.addAll(urisList)
        setupViewPagers()
        binding.toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    private fun setupViewPagers() {
        largePagerAdapter.submitList(imageUrisList)
        smallPagerAdapter.submitList(imageUrisList)
        if (imageUrisList.size == 1) {
            binding.imagesSliderSmall.isGone = true
        } else {
            binding.imagesSliderSmall.isVisible = true
        }
    }

    companion object {
        private val TAG = PictureUploadPreviewActivity::class.java.simpleName
        fun getUriList(photoUrls: Array<String>): List<Uri> {
            val list = mutableListOf<Uri>()
            photoUrls.forEach {
                if (it.isNotEmpty()) {
                    list.add(Uri.parse(it))
                }
            }
            return list.toList()
        }
    }
}
