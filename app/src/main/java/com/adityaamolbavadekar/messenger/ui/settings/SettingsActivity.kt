/*
 *
 *    Copyright 2022 Aditya Bavadekar
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
 *
 */

package com.adityaamolbavadekar.messenger.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.*
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.bumptech.glide.Glide

class SettingsActivity : BaseActivity() {

    private val list = MutableLiveData(listOf<SettingsItem>())

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        val binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }
        val navController = Navigation.findNavController(this, R.id.container)
        intent?.getStringExtra(EXTRA_FRAGMENT)?.let {
            if (SettingsFragments.valueOf(it) == SettingsFragments.ABOUT) {
                binding.appBar.isGone = true
                navController.navigate(
                    R.id.aboutMessengerFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.aboutMessengerFragment, true).build()
                )
            }
        }
    }

    override fun onBackPressed() {
        if (intent?.getStringExtra(EXTRA_FRAGMENT) != null) {
            finish()
        } else super.onBackPressed()
    }

    class SettingsAdapter(private val onClickListener: (SettingsItem) -> Unit) :
        ListAdapter<SettingsItem, SettingsAdapter.Holder>(SettingsItem.DiffUtilCallback()) {

        inner class Holder(
            private val root: View,
            private val title: TextView?,
            private val summary: TextView?,
            private val icon: ImageView?,
            private val iconFrame: View?,
        ) :
            RecyclerView.ViewHolder(root) {
            fun bind(item: SettingsItem) {
                if (item.type != SettingsItem.Types.CATEGORY_HEADER) {
                    root.setOnClickListener { onClickListener(item) }
                }

                title?.text = item.title

                if (item.preferenceSummaryProvider != null) {
                    summary?.text = item.preferenceSummaryProvider.invoke(root.context)
                    summary?.isVisible = true
                } else {
                    if (item.summary != null) {
                        summary?.text = item.summary
                        summary?.isVisible = true
                    } else {
                        summary?.isGone = true
                    }
                }


                if (item.iconUrl != null) {
                    icon?.load(Uri.parse(item.iconUrl), true, item.icon)
                    iconFrame?.isVisible = true
                } else {
                    if (item.icon != null) {
                        icon?.setImageResource(item.icon)
                        iconFrame?.isVisible = true
                    } else {
                        icon?.let { Glide.with(root.context).clear(it) }
                        iconFrame?.isGone = true
                    }
                }
            }

            constructor(b: SettingsActivityItemBinding) : this(
                root = b.root,
                title = b.title,
                summary = b.summary,
                icon = b.icon,
                iconFrame = b.iconFrame
            )

            constructor(b: SettingsActivityDividerItemBinding) : this(
                root = b.root, null, null, null, null
            )

            constructor(b: SettingsActivityCategoryItemBinding) : this(
                root = b.root,
                title = b.title,
                summary = b.summary,
                null,
                null
            )

            constructor(b: SettingsActivityAccountPrefsItemBinding) : this(
                root = b.root,
                title = b.title,
                summary = b.summary,
                icon = b.icon,
                iconFrame = b.iconFrame
            )

        }

        override fun getItemViewType(position: Int): Int {
            return getItem(position).type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val inflater = LayoutInflater.from(parent.context)
            val view = when (viewType) {
                SettingsItem.Types.DIVIDER -> Holder(
                    SettingsActivityDividerItemBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
                SettingsItem.Types.CATEGORY_HEADER ->
                    Holder(SettingsActivityCategoryItemBinding.inflate(inflater, parent, false))
                SettingsItem.Types.ACCOUNT_ITEM ->
                    Holder(SettingsActivityAccountPrefsItemBinding.inflate(inflater, parent, false))
                else -> Holder(
                    SettingsActivityItemBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            return (view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    data class SettingsItem(
        val key: String,
        val type: Int = Types.SETTINGS_ITEM,
        val title: String = "",
        val summary: String? = null,
        @DrawableRes val icon: Int? = null,
        val iconUrl: String? = null,
        val preferenceSummaryProvider: ((Context) -> String)? = null,
    ) {

        annotation class Types {
            companion object {
                const val CATEGORY_HEADER = 0
                const val SETTINGS_ITEM = 1
                const val ACCOUNT_ITEM = 2
                const val DIVIDER = 3
                const val FOOTER = 4
            }
        }

        class DiffUtilCallback : DiffUtil.ItemCallback<SettingsItem>() {
            override fun areItemsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
                return oldItem.key == newItem.key
            }
        }

    }

    annotation class SettingsKeys {
        companion object {
            const val THEME = "key_theme"
            const val NOTIFICATIONS = "key_notifications"
            const val TEXT_SIZE = "key_text_size"
            const val WALLPAPER = "key_wallpaper"
            const val ABOUT = "key_about"
            const val DELETE_ACCOUNT = "key_delete_account"
            const val CHANGE_NUMBER = "key_change_number"
            const val PRIVACY = "key_privacy"
            const val ADD_PIN = "key_add_pin"
            const val REQUEST_ACCOUNT_DATA = "key_request_account_info"
        }
    }

    enum class SettingsFragments { THEME, NOTIFICATIONS, PRIVACY, ADD_PIN, REQUEST_ACCOUNT_DATA, ABOUT }

    companion object {
        private val TAG = SettingsActivity::class.java.simpleName
        private const val EXTRA_FRAGMENT = "extra_fragment"
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

        fun newIntent(context: Context, extraFragment: SettingsFragments): Intent {
            return Intent(context, SettingsActivity::class.java)
                .putExtra(EXTRA_FRAGMENT, extraFragment.name)
        }
    }

}