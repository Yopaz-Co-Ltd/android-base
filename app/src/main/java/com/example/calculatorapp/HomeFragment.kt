package com.example.calculatorapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.navigateToCalculator).setOnClickListener{view ->
            val action = HomeFragmentDirections.actionHomeFragmentToCalculatorFragment()
            view.findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.navigateToTodoApp).setOnClickListener{view ->
            val action = HomeFragmentDirections.actionHomeFragmentToTodoFragment()
            view.findNavController().navigate(action)
        }
    }
}
