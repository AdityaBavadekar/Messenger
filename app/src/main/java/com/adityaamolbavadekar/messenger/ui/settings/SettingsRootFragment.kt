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

package com.adityaamolbavadekar.messenger.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.constants.PreferenceKeys
import com.adityaamolbavadekar.messenger.databinding.SettingsRootFragmentBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.prefs
import com.adityaamolbavadekar.messenger.model.FontSize
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.IntentsUtils
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.theming.ThemeInfo.Companion.getPreferredThemeInfo
import com.adityaamolbavadekar.pinlog.PinLog
import java.util.*

class SettingsRootFragment : BindingHelperFragment<SettingsRootFragmentBinding>(),
        (SettingsActivity.SettingsItem) -> Unit {

    private val list = MutableLiveData(listOf<SettingsActivity.SettingsItem>())
    private lateinit var listAdapter: SettingsActivity.SettingsAdapter
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onShouldInflateBinding(): SettingsRootFragmentBinding {
        return SettingsRootFragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            listAdapter = SettingsActivity.SettingsAdapter(this@SettingsRootFragment)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        val loader = Dialogs.showLoadingDialog(requireContext(), getString(R.string.loading), false)

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loader.show()
            else loader.hide()
        }

        list.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

        getList()
    }

    private fun getList() = runOnIOThread {
        val newList = mutableListOf<SettingsActivity.SettingsItem>()
        newList.add(
            SettingsActivity.SettingsItem(
                "AccountCategoryHeader",
                SettingsActivity.SettingsItem.Types.CATEGORY_HEADER,
                "You"
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                "Account",
                SettingsActivity.SettingsItem.Types.ACCOUNT_ITEM,
                me.loadName(),
                me.phone ?: me.email,
                R.drawable.ic_user_profile_default, me.photoUrl
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                UUID.randomUUID().toString(), SettingsActivity.SettingsItem.Types.DIVIDER
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                "AppearanceCategoryHeader",
                SettingsActivity.SettingsItem.Types.CATEGORY_HEADER,
                "Appearance"
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                "conversations",
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "Conversations"
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                SettingsActivity.SettingsKeys.THEME,
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "Theme", null, null, null
            ) { it.getPreferredThemeInfo().name }
        )
        newList.add(
            SettingsActivity.SettingsItem(
                SettingsActivity.SettingsKeys.NOTIFICATIONS,
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "Notifications"
            )
        )
        newList.add(
            SettingsActivity.SettingsItem(
                SettingsActivity.SettingsKeys.TEXT_SIZE,
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "Text size", null, null, null
            ) {
                when (it.prefs.getInt(PreferenceKeys.TEXT_SIZE, FontSize.MEDIUM)) {
                    FontSize.MEDIUM -> it.getString(R.string.medium)
                    FontSize.SMALL -> it.getString(R.string.small)
                    else -> it.getString(R.string.large)
                }
            }
        )

        newList.add(
            SettingsActivity.SettingsItem(
                SettingsActivity.SettingsKeys.HELP_FEEDBACK,
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "Help and Feedback"
            )
        )

        newList.add(
            SettingsActivity.SettingsItem(
                SettingsActivity.SettingsKeys.ABOUT,
                SettingsActivity.SettingsItem.Types.SETTINGS_ITEM,
                "About Messenger"
            )
        )

        list.postValue(newList)
    }

    override fun invoke(item: SettingsActivity.SettingsItem) {
        when (item.type) {
            SettingsActivity.SettingsItem.Types.ACCOUNT_ITEM -> {

            }
            SettingsActivity.SettingsItem.Types.SETTINGS_ITEM -> {
                when (item.key) {
                    SettingsActivity.SettingsKeys.ABOUT -> {
                        startActivity(
                            SettingsActivity.newIntent(
                                requireContext(),
                                SettingsActivity.SettingsFragments.ABOUT
                            )
                        )
                    }
                    SettingsActivity.SettingsKeys.THEME -> showToggleTheme()
                    SettingsActivity.SettingsKeys.NOTIFICATIONS -> {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            startActivity(
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                    .putExtra(
                                        Intent.EXTRA_PACKAGE_NAME,
                                        requireContext().packageName
                                    )
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
                        } else {
                            //todo
                        }
                    }
                    SettingsActivity.SettingsKeys.TEXT_SIZE -> {
                        val textSize =
                            requireContext().prefs.getInt(PreferenceKeys.TEXT_SIZE, FontSize.MEDIUM)
                        Dialogs.showItemChooserDialog(
                            requireContext(),
                            R.string.choose_text_size,
                            R.array.text_size,
                            textSize
                        ) {
                            prefsManager.saveTextSize(it)
                            listAdapter.notifyDataSetChanged()
                        }
                    }

                    SettingsActivity.SettingsKeys.HELP_FEEDBACK -> {
                        viewModel.getLogsInFile {
                            val uri = try {
                                FileProvider.getUriForFile(
                                    requireContext(),
                                    requireContext().packageName + ".pinlog.provider", it!!
                                )
                            } catch (e: Exception) {
                                Uri.fromFile(it!!)
                            }
                            val i = IntentsUtils.shareThroughEmail(
                                requireContext(),
                                subject = "Help for Messenger",
                                textContent = "I am sharing with you logs. Please help me with: \n",
                                attachments = arrayListOf(uri),
                                toEmail = Constants.SUPPORT_EMAIL
                            )
                            InternalLogger.logD(TAG, "Launching email-share intent.")
                            startActivity(i)
                        }
                    }
                }
            }
        }
    }

    private fun showToggleTheme() {
        Dialogs.showThemeChooserDialog(requireContext()) {
            requireActivity().recreate()
        }
        listAdapter.notifyDataSetChanged()
    }

    companion object {
        private val TAG = SettingsRootFragment::class.java.simpleName
    }


}