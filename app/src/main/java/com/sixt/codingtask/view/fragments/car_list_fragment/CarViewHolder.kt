package com.sixt.codingtask.view.fragments.car_list_fragment

import androidx.recyclerview.widget.RecyclerView
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.databinding.RowCarBinding

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 *
 * CarViewHolder to hold an item view and metadata about its place within the RecyclerView.
 * Hold strong references to ViewHolder objects and that RecyclerView instances may hold
 * strong references to extra off-screen item views for caching purposes.
 * With a constructor which accepts `RowCarBinding` as the only parameter to bind it
 * with row_car layout.
 *
 */
class CarViewHolder(private val binding: RowCarBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(car: Car) {

        binding.model = car
        binding.executePendingBindings()
    }
}