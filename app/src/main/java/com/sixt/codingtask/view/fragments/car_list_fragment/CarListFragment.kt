package com.sixt.codingtask.view.fragments.car_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixt.codingtask.R
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.databinding.FragmentListBinding
import com.sixt.codingtask.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class CarListFragment : Fragment() {

    private val viewModel: CarViewModel by activityViewModels()
    private lateinit var carAdapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
        Inflate the layout using dataBinding util method i.e.
        DataBindingUtil.
         */
        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list,
            container, false
        )

        return binding.run {

            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupUI()
    }

    // initialize the recycleView with empty list
    private fun setupUI() {

        carAdapter = CarAdapter().apply {

            update(viewModel.cars.value ?: emptyList())
        }

        with(recyclerView)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = carAdapter
        }
    }

    private fun setupViewModel() {

        with(viewModel)
        {
            cars.observe(viewLifecycleOwner, renderCarsObserver)
            onError.observe(viewLifecycleOwner, errorObserver)
        }
    }

    //observers
    private val renderCarsObserver = Observer<List<Car>> {

        carAdapter.update(it)
    }

    private val errorObserver = Observer<Any> {

        layoutError.visibility = View.VISIBLE

        if (it.toString().trim().isNotEmpty())
            textViewError.text = it.toString()
    }

}
