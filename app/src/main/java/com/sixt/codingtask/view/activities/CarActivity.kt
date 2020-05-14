package com.sixt.codingtask.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sixt.codingtask.R
import com.sixt.codingtask.databinding.ActivityCarBinding
import com.sixt.codingtask.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.activity_car.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class CarActivity : AppCompatActivity() {

    private val viewModel: CarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
        Inflate the layout using dataBinding util method i.e.
        DataBindingUtil.
         */
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_car
        ) as ActivityCarBinding

        // Finding the Navigation Controller
        val navController = findNavController(R.id.navHost)

        // Setting Navigation Controller with the BottomNavigationView
        navigationView.setupWithNavController(navController)
    }
}
