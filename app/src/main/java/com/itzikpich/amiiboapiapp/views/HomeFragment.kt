package com.itzikpich.amiiboapiapp.views

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.itzikpich.amiiboapiapp.R
import com.itzikpich.amiiboapiapp.adapters.AmiiboAdapter
import com.itzikpich.amiiboapiapp.databinding.FragmentHomeBinding
import com.itzikpich.amiiboapiapp.models.ListItemListener
import com.itzikpich.amiiboapiapp.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val mainViewModel by viewModels<MainViewModel>()
    @Inject lateinit var amiiboAdapter: AmiiboAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true) // take control of the menu items clicks

        val adapter = amiiboAdapter.apply {
            listItemListener = object :ListItemListener {
                override fun onClick(id: String) {
                    onItemClick(id)
                }

                override fun onLongClick(id: String) {
                    onLongItemClick(id)
                }

            }
        }
        binding.homeList.adapter = adapter

        // on refresh will fetch new data from network
        binding.root.setOnRefreshListener {
            mainViewModel.clearTableAndRefetchAmiiboResponseFromNetwork()
        }

        // show message at first entry to notify the user about the long click possibility
        mainViewModel.showMessage.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                snackbar
                    .setAction("OK") { snackbar.dismiss() }
                    .setActionTextColor(Color.RED)
                    .show()
                mainViewModel.showMessage.value = null
            }
        }

        /** observe changes in the db, which is our Single source of truth,
         * every change on amiibo table of the db,
         * will be shown on the recyclerView
        */
        mainViewModel.amiiboList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(
                if (mainViewModel.isFiltering) mainViewModel.filteredAmiiboList
                else list
            )
        }

        // observe changes to show or hide loader
        mainViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.root.isRefreshing = loading
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_purchased -> {
                // change filtering state
                mainViewModel.isFiltering = !mainViewModel.isFiltering
                // set icon according to isFiltering
                item.icon = ContextCompat.getDrawable(binding.root.context,
                    if (!mainViewModel.isFiltering) R.drawable.baseline_shopping_gold else R.drawable.shopping_cart_white
                )
                // submit list according to isFiltering
                (binding.homeList.adapter as AmiiboAdapter).submitList(
                    if (mainViewModel.isFiltering)  mainViewModel.filteredAmiiboList
                    else mainViewModel.amiiboList.value
                )
                true
            }
            R.id.menu_refresh -> {
                mainViewModel.clearTableAndRefetchAmiiboResponseFromNetwork()
                true
            }
            R.id.menu_add -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_purchased).icon = ContextCompat.getDrawable(binding.root.context,
            if (!mainViewModel.isFiltering) R.drawable.baseline_shopping_gold else R.drawable.shopping_cart_white
        )
    }

    private fun onItemClick(id: String) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
    }

    private fun onLongItemClick(id: String) {
        // delete an Amiibo item from Amiibo table
        mainViewModel.clearAmiiboByIdFromDB(id)
    }
}