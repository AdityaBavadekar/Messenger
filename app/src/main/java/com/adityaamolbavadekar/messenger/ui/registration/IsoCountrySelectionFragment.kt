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

package com.adityaamolbavadekar.messenger.ui.registration

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.FragmentIsoCountrySelectionBinding
import com.adityaamolbavadekar.messenger.databinding.ItemIsoCountrySelectionBinding
import com.adityaamolbavadekar.messenger.utils.PhoneNumberUtils
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.goBack
import com.adityaamolbavadekar.messenger.utils.extensions.isNotNull
import com.adityaamolbavadekar.messenger.utils.extensions.toast
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class IsoCountrySelectionFragment : BindingHelperFragment<FragmentIsoCountrySelectionBinding>(),
    SearchView.OnQueryTextListener {

    override fun onShouldInflateBinding(): FragmentIsoCountrySelectionBinding {
        return FragmentIsoCountrySelectionBinding.inflate(layoutInflater)
    }

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var countryInfoListAdapter: CountryInfoListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
        viewModel.getCountryInfoList(requireContext())
        binding.toolbar.title = getString(R.string.select_a_country)
        countryInfoListAdapter = CountryInfoListAdapter {
            InternalLogger.logD(TAG, "onCountryInfoClicked : $it")
            viewModel.updatedSelectedCountryInfo(it)
            goBack()
        }
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.countriesRecyclerView.apply {
            adapter = countryInfoListAdapter
            layoutManager = linearLayoutManager
        }
        binding.toolbar.setNavigationOnClickListener {
            goBack()
        }
        viewModel.countryInfoList.observe(viewLifecycleOwner) {
            InternalLogger.logD(TAG, "CountryInfoList.size : ${it.size}")
            countryInfoListAdapter.submitList(it)
            countryInfoListAdapter.submitToTempList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_iso_selection, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                (item.actionView as SearchView).apply {
                    setOnQueryTextListener(this@IsoCountrySelectionFragment)
                    setOnCloseListener { clearSuggestions() }
                }
                true
            }
            else -> false
        }
    }

    private fun clearSuggestions(): Boolean {
        countryInfoListAdapter.clearSearchSuggestions()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.trim().isNotEmpty())
                countryInfoListAdapter.search(it) { requireContext().toast { "No results found." } }
        }
        return true
    }

    class CountryInfoListAdapter(private val onClick: (PhoneNumberUtils.CountryInfo) -> Unit) :
        ListAdapter<PhoneNumberUtils.CountryInfo, CountryInfoListAdapter.CountryInfoHolder>(
            CountryInfoDiffUtil()
        ) {

        private var selectedCountryInfo: PhoneNumberUtils.CountryInfo? = null

        inner class CountryInfoHolder(private val b: ItemIsoCountrySelectionBinding) :
            RecyclerView.ViewHolder(b.root) {
            fun bind(
                info: PhoneNumberUtils.CountryInfo,
                onClick: (PhoneNumberUtils.CountryInfo) -> Unit
            ) {
                b.countryIconTextview.text = info.icon
                b.countryFullName.text = info.country
                b.isoCode.text = info.code
                if (selectedCountryInfo.isNotNull() && info.code == selectedCountryInfo!!.code) {
                    b.selectionIndicator.isVisible = true
                } else {
                    b.selectionIndicator.isGone = true
                }

                b.root.setOnClickListener {
                    selectedCountryInfo = info
                    onClick(info)
                }
            }
        }

        class CountryInfoDiffUtil : DiffUtil.ItemCallback<PhoneNumberUtils.CountryInfo>() {

            override fun areItemsTheSame(
                oldItem: PhoneNumberUtils.CountryInfo,
                newItem: PhoneNumberUtils.CountryInfo
            ): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(
                oldItem: PhoneNumberUtils.CountryInfo,
                newItem: PhoneNumberUtils.CountryInfo
            ): Boolean {
                return (oldItem.code == newItem.code
                        && oldItem.country == newItem.country
                        && oldItem.icon == newItem.icon)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryInfoHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                ItemIsoCountrySelectionBinding.inflate(inflater, parent, false)
            return CountryInfoHolder(binding)
        }

        override fun onBindViewHolder(holder: CountryInfoHolder, position: Int) {
            holder.bind(getItem(position), onClick)
        }

        private var tempCountryInfoList = listOf<PhoneNumberUtils.CountryInfo>()
        private var isSearching = false

        fun submitToTempList(list: List<PhoneNumberUtils.CountryInfo>) {
            tempCountryInfoList = list
        }

        fun clearSearchSuggestions() {
            isSearching = false
            submitList(tempCountryInfoList)
        }

        fun search(query: String, onNoSuggestionsFound: () -> Unit) {
            isSearching = true
            val searchSuggestionList = mutableListOf<PhoneNumberUtils.CountryInfo>()
            tempCountryInfoList.forEach { countryInfo ->
                if (countryInfo.country.contains(query, ignoreCase = true) ||
                    countryInfo.code.contains(query, ignoreCase = true)
                ) searchSuggestionList.add(countryInfo)
            }
            if (searchSuggestionList.isNotEmpty()) {
                submitList(searchSuggestionList)
            } else onNoSuggestionsFound()
        }

    }

    override fun onResume() {
        super.onResume()
        countryInfoListAdapter.notifyDataSetChanged()
    }

    companion object {
        private val TAG = IsoCountrySelectionFragment::class.java.simpleName
    }

}