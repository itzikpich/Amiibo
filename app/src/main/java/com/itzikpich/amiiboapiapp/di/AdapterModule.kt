package com.itzikpich.amiiboapiapp.di

import androidx.recyclerview.widget.ListAdapter
import com.itzikpich.amiiboapiapp.adapters.AmiiboAdapter
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.view_holders.ViewBindingViewHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AdapterModule {

    @Binds
    abstract fun provideAmiiboAdapter(amiiboAdapter: AmiiboAdapter): ListAdapter<AmiiboResponse.Amiibo, ViewBindingViewHolder>

}