package com.sixt.codingtask.view.fragments.car_map_fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.sixt.codingtask.R
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.databinding.FragmentMapBinding
import com.sixt.codingtask.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_error.*


/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: CarViewModel by activityViewModels()
    private val options = MarkerOptions()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
        Inflate the layout using dataBinding util method i.e.
        DataBindingUtil.
         */
        val binding: FragmentMapBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map,
            container, false
        )

        binding.lifecycleOwner = this


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return binding.root
    }

    private fun setupViewModel() {

        with(viewModel)
        {
            cars.observe(viewLifecycleOwner, renderCarsObserver)
            onError.observe(viewLifecycleOwner, onMessageErrorObserver)
        }
    }

    //observers
    private val renderCarsObserver = Observer<List<Car>> {

        updateMap(it)
    }

    private fun updateMap(cars: List<Car>) {

        if (cars.isNotEmpty()) {
            val builder = LatLngBounds.Builder()

            for (car in cars) {
                val point = LatLng(car.latitude, car.longitude)
                options.position(point)
                options.title(car.name)
                options.snippet(
                    getString(
                        R.string.car_item_make_concatenation,
                        car.make,
                        car.series
                    )
                )
                mMap.addMarker(options)
                builder.include(point)
            }

            val bounds = builder.build()
            // offset from edges of the map in pixels
            val padding = 0
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(cameraUpdate)
        }
    }

    private val onMessageErrorObserver = Observer<Any> {

        layoutError.visibility = View.VISIBLE

        if (it.toString().trim().isNotEmpty())
            textViewError.text = it.toString()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setupViewModel()

    }

}
