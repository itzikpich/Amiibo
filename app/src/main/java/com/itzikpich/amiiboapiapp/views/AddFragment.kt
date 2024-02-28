package com.itzikpich.amiiboapiapp.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itzikpich.amiiboapiapp.R
import com.itzikpich.amiiboapiapp.databinding.FragmentAddBinding
import com.itzikpich.amiiboapiapp.utilities.loadFromUrlToGlide
import com.itzikpich.amiiboapiapp.view_models.AddViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class AddFragment: Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val addViewModel by viewModels<AddViewModel>()

    // use this as startActivityFroResult is deprecated
    private lateinit var imageRequest: ActivityResultLauncher<Intent>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true) // take control of the menu items clicks

        imageRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                (result.data?.data)?.let { uri ->
                    addViewModel.imageUri.value = uri
                }
            }
        }
        binding.apply {
            addImageButton.setOnClickListener {
                Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_OPEN_DOCUMENT
                    imageRequest.launch(Intent.createChooser(this, "please select"))
                }
            }
            this.saveButton.setOnClickListener {
                if (!addTitle.text.isNullOrBlank() && addViewModel.imageUri.value != null) {
                    addViewModel.addAmiibo(addTitle.text.toString()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                } else {
                    addViewModel.showMessage("Item must contain title and image")
                }
            }
        }

        // observe imageUri to show on screen the selected image
        addViewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            binding.addImageToShow.loadFromUrlToGlide(uri)
        }

        // disable save button while data is saved to db
        addViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.saveButton.isEnabled = !loading
        }

        addViewModel.showMessage.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                addViewModel.showMessage.value = null
            }
        }
    }

    // hide menu items
    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_purchased).isVisible = false
        menu.findItem(R.id.menu_refresh).isVisible = false
        menu.findItem(R.id.menu_add).isVisible = false
    }
}