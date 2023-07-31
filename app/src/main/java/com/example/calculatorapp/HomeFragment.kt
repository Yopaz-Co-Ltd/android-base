package com.example.calculatorapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        rootView.findViewById<Button>(R.id.navigateToCalculator).setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_calculatorFragment)
        }

        rootView.findViewById<Button>(R.id.navigateToTodoApp).setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_todoFragment)
        }

        return rootView
    }


}