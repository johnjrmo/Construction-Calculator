package com.example.righttriangle

import android.os.Bundle
import android.app.Activity
import android.widget.EditText
import com.example.righttriangle.databinding.ActivityMainBinding
import kotlin.math.atan2
import kotlin.math.hypot

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    private val fields: List<EditText> by lazy {
        listOf(binding.legAFeet, binding.legAInches, binding.legBFeet, binding.legBInches)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculate() }
        fields.forEach { it.setOnEditorActionListener { _, _, _ -> calculate(); true } }
    }

    private fun value(field: EditText): Double = field.text.toString().toDoubleOrNull() ?: 0.0

    private fun calculate() {
        val aFeet = value(binding.legAFeet)
        val aInches = value(binding.legAInches)
        val bFeet = value(binding.legBFeet)
        val bInches = value(binding.legBInches)
        binding.error.text = ""
        if (aFeet < 0 || bFeet < 0 || aInches < 0 || bInches < 0) {
            binding.error.text = "Values cannot be negative."
            return
        }
        if (aInches >= 12 || bInches >= 12) {
            binding.error.text = "Inches must be less than 12."
            return
        }
        val legA = aFeet * 12 + aInches
        val legB = bFeet * 12 + bInches
        if (legA <= 0 || legB <= 0) {
            binding.error.text = "Enter a value for both legs."
            return
        }
        val diagonal = hypot(legA, legB)
        binding.hypotenuse.text = formatFeetInches(diagonal)
        binding.angleA.text = "${"%.2f".format(Math.toDegrees(atan2(legB, legA)))}°"
        binding.angleB.text = "${"%.2f".format(Math.toDegrees(atan2(legA, legB)))}°"
        binding.summary.text = "Calculated from both legs"
    }

    private fun formatFeetInches(totalInches: Double): String {
        val feet = (totalInches / 12).toInt()
        val inches = totalInches - feet * 12
        val formattedInches = if (inches < 0.01) "0" else "%.2f".format(inches).trimEnd('0').trimEnd('.')
        return "$feet ft $formattedInches in"
    }
}
