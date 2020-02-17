package com.sixt.codingtask.view.fragments.car_list_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.databinding.RowCarBinding

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class CarAdapter : RecyclerView.Adapter<CarViewHolder>() {

    private var cars = ArrayList<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CarViewHolder {
        val binding = RowCarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return CarViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]

        //render
        holder.bind(car)
    }

    override fun onBindViewHolder(
        holder: CarViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            try {
                val car = payloads[0] as Car
                holder.bind(car)

            } catch (exception: java.lang.Exception) {
                Log.e("Exception", "onBindViewHolder: ", exception)
            }
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun update(data: List<Car>) = if (cars.isEmpty()) {
        cars.addAll(data)
        notifyItemRangeInserted(0, cars.size)
    } else {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return cars.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return cars[oldItemPosition] == data[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newProduct = data[newItemPosition]
                val oldProduct = cars[oldItemPosition]
                return newProduct.name == oldProduct.name &&
                        newProduct.carImageUrl == oldProduct.carImageUrl &&
                        newProduct.make == oldProduct.make &&
                        newProduct.series == oldProduct.series &&
                        newProduct.color == oldProduct.color
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val newProduct = data[newItemPosition]
                val oldProduct = cars[oldItemPosition]
                return if (newProduct != oldProduct) {
                    newProduct
                } else null
            }
        })
        cars = data as ArrayList<Car>
        result.dispatchUpdatesTo(this)
    }
}