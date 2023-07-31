package com.example.calculatorapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class CalculatorFragment : Fragment() {

    private lateinit var equal: TextView
    private var currenOperation: String = ""
    private var currentInput: String = "0"
    private var prevValue: Double = 0.0
    private var isCalculated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val RootView = inflater.inflate(R.layout.fragment_calculator, container, false)

        equal = RootView.findViewById(R.id.equal)

        val numberButtons = arrayOf(
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9,
        )

        val operationButtons = arrayOf(
            R.id.btn_plus, R.id.btn_minus, R.id.btn_division, R.id.btn_multi, R.id.btn_percent
        )

        for (buttonId in numberButtons) {
            RootView.findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(it)
            }
        }

        for (buttonId in operationButtons) {
            RootView.findViewById<Button>(buttonId).setOnClickListener {
                onOperationClick(it)
            }
        }

        RootView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            currentInput = "0"
            updateEqual()
        }

        RootView.findViewById<Button>(R.id.btn_equal).setOnClickListener {
            onEqualClick()
        }

        RootView.findViewById<Button>(R.id.btn_negative).setOnClickListener {
            onNegativeClick()
        }

        RootView.findViewById<Button>(R.id.btn_percent).setOnClickListener {
            onPercentClick()
        }

        RootView.findViewById<Button>(R.id.btn_period).setOnClickListener {
            onClickPeriod()
        }


        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    fun onNumberClick(view: View) {
        val number = (view as Button).text.toString()
        if (isCalculated == true) {
            currentInput = ""
            isCalculated = false
            currentInput = number
        } else {
            if (currentInput == "0") {
                currentInput = number
            } else {
                currentInput += number
            }
        }
        updateEqual()
    }

    fun onOperationClick(view: View) {
        val buttonOperation = view as Button
        val operation = buttonOperation.text.toString()
        prevValue = currentInput.toDouble()
        currentInput = ""
        currenOperation = operation
    }

    fun onNegativeClick() {
        val currentValue = currentInput.toDouble()
        val negativeCurrentValue = 0 - currentValue
        currentInput = ""
        checkIntOrDouble(negativeCurrentValue)
        updateEqual()
    }

    fun onPercentClick() {
        val currentValue = currentInput.toDouble()
        val currenPercentValue = currentValue / 100
        currentInput = ""
        checkIntOrDouble(currenPercentValue)
        updateEqual()
    }

    fun onClickPeriod() {
        if (currentInput == "0") {
            currentInput = ""
            currentInput = "0.$currentInput"
        }
        updateEqual()

    }

    fun onEqualClick() {
        val currentValue = currentInput.toDouble()
        val result = when (currenOperation) {
            "+" -> prevValue + currentValue
            "-" -> prevValue - currentValue
            "x" -> prevValue * currentValue
            "/" -> prevValue / currentValue
            "%" -> prevValue / 100
            else -> currentValue
        }
        checkIntOrDouble(result)
        prevValue = currentInput.toDouble()
        updateEqual()
        isCalculated = true
    }

    fun updateEqual() {
        equal.text = currentInput
    }

    fun checkIntOrDouble(value: Double) {
        if (value % 1 == 0.0) {
            currentInput = value.toInt().toString()
        } else {
            currentInput = value.toString()
        }
    }



}