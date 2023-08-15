package com.example.calculatorapp.fragments.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calculatorapp.R

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    companion object {
        const val DEFAULT_INPUT_VALUE = "0"
        const val PLUS_SIGN = "+"
        const val MINUS_SIGN = "-"
        const val MULTI_SIGN = "x"
        const val DIVISION_SIGN = "/"
        const val PERCENT_SIGN = "%"
    }

    private var equalTextView: TextView? = null
    private var currentOperation: String = ""
    private var currentInput: String = DEFAULT_INPUT_VALUE
    private var previousValue: Double = 0.0
    private var isCalculated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        equalTextView = view.findViewById(R.id.equal)

        val numberButtonIds = arrayOf(
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

        val operationButtonIds = arrayOf(
            R.id.btn_plus, R.id.btn_minus, R.id.btn_division, R.id.btn_multi, R.id.btn_percent
        )

        for (buttonId in numberButtonIds) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(it)
            }
        }

        for (buttonId in operationButtonIds) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                onOperationClick(it)
            }
        }

        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            currentInput = DEFAULT_INPUT_VALUE
            updateEqual()
        }

        view.findViewById<Button>(R.id.btn_equal).setOnClickListener {
            onEqualClick()
        }

        view.findViewById<Button>(R.id.btn_negative).setOnClickListener {
            onNegativeClick()
        }

        view.findViewById<Button>(R.id.btn_percent).setOnClickListener {
            onPercentClick()
        }

        view.findViewById<Button>(R.id.btn_period).setOnClickListener {
            onClickPeriod()
        }
    }

    fun onNumberClick(view: View) {
        val number = (view as Button).text.toString()
        when {
            isCalculated -> {
                currentInput = ""
                isCalculated = false
                currentInput = number
            }

            currentInput == DEFAULT_INPUT_VALUE -> {
                currentInput = number
            }

            else -> {
                currentInput += number
            }
        }
        updateEqual()
    }

    fun onOperationClick(view: View) {
        val buttonOperation = view as Button
        val operation = buttonOperation.text.toString()
        previousValue = currentInput.toDouble()
        currentInput = ""
        currentOperation = operation
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
        previousValue = currenPercentValue
        currentInput = ""
        checkIntOrDouble(currenPercentValue)
        updateEqual()
    }

    fun onClickPeriod() {
        if (currentInput == DEFAULT_INPUT_VALUE) {
            currentInput = ""
            currentInput = "0.$currentInput"
        }
        updateEqual()
    }

    fun onEqualClick() {
        val currentValue = currentInput.toDouble()
        val result = when (currentOperation) {
            PLUS_SIGN -> previousValue + currentValue
            MINUS_SIGN -> previousValue - currentValue
            MULTI_SIGN -> previousValue * currentValue
            DIVISION_SIGN -> previousValue / currentValue
            PERCENT_SIGN -> previousValue / 100
            else -> currentValue
        }
        checkIntOrDouble(result)
        previousValue = currentInput.toDouble()
        updateEqual()
        isCalculated = true
    }

    fun updateEqual() {
        equalTextView?.text = currentInput
    }

    fun checkIntOrDouble(value: Double) {
        if (value % 1 == 0.0) {
            currentInput = value.toInt().toString()
        } else {
            currentInput = value.toString()
        }
    }


}
