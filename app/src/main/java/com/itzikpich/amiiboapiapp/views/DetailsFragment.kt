package com.itzikpich.amiiboapiapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.itzikpich.amiiboapiapp.R
import com.itzikpich.amiiboapiapp.databinding.FragmentDetailsBinding
import com.itzikpich.amiiboapiapp.utilities.loadFromUrlToGlide
import com.itzikpich.amiiboapiapp.view_models.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment: Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true) // take control of the menu items clicks

        // listen to updates on the Amiibo item
        detailsViewModel.amiibo.observe(viewLifecycleOwner) { amiibo ->
            binding.apply {
                amiibo.image?.let { detailsImage.loadFromUrlToGlide(it) }
                detailsCharacter.text = amiibo.character
                detailsGameSeries.text = amiibo.gameSeries
                detailsAmiiboSeries.text = amiibo.amiiboSeries
                detailsButton.isEnabled = !amiibo.isPurchased
                detailsButton.text = resources.getString(if (amiibo.isPurchased) R.string.purchased else R.string.purchase)
                detailsButton.setOnClickListener {
                    detailsViewModel.onItemClicked()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        // hide all menu items
        menu.findItem(R.id.action_purchased).isVisible = false
        menu.findItem(R.id.menu_refresh).isVisible = false
        menu.findItem(R.id.menu_add).isVisible = false
    }

}