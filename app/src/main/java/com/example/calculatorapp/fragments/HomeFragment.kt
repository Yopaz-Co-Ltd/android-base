package com.example.calculatorapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.calculatorapp.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.navigateToCalculator).setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToCalculatorFragment()
            view.findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.navigateToTodoApp).setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToTodoFragment()
            view.findNavController().navigate(action)
        }
    }
}
