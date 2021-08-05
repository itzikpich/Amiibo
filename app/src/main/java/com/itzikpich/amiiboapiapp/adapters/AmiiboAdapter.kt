package com.itzikpich.amiiboapiapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.itzikpich.amiiboapiapp.databinding.ListItemBinding
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.ListItemListener
import com.itzikpich.amiiboapiapp.utilities.loadFromUrlToGlide
import com.itzikpich.amiiboapiapp.view_holders.ViewBindingViewHolder
import javax.inject.Inject


class AmiiboAdapter @Inject constructor() : ListAdapter<AmiiboResponse.Amiibo, ViewBindingViewHolder>(DIFF_CALLBACK) {

    var listItemListener: ListItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewBindingViewHolder.createVH(parent, ListItemBinding::inflate)

    override fun onBindViewHolder(holder: ViewBindingViewHolder, position: Int) {
        getItem(position)?.let { item ->
            (holder.binding as? ListItemBinding)?.apply {
                this.itemName.text = item.character
                item.image?.let { this.itemImage.loadFromUrlToGlide(it) }
                this.itemPurchased.isEnabled = item.isPurchased
                this.root.setOnClickListener { listItemListener?.onClick(item.id) }
                this.root.setOnLongClickListener {
                    listItemListener?.onLongClick(item.id)
                    true
                }
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AmiiboResponse.Amiibo>() {
            override fun areItemsTheSame(oldItem: AmiiboResponse.Amiibo, newItem: AmiiboResponse.Amiibo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AmiiboResponse.Amiibo, newItem: AmiiboResponse.Amiibo): Boolean {
                return oldItem == newItem
            }
        }
    }

}