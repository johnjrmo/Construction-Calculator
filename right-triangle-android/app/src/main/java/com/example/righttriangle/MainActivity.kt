package com.example.righttriangle

import android.os.Bundle
import android.app.Activity
import android.widget.EditText
import android.widget.Button
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
        binding.triangleTabButton.setOnClickListener { showTriangleTab() }
        binding.feetInchesTabButton.setOnClickListener { showFeetInchesTab() }
        binding.volumeTabButton.setOnClickListener { showVolumeTab() }
        binding.tonsTabButton.setOnClickListener { showTonsTab() }
        binding.calculateButton.setOnClickListener { calculate() }
        binding.feetInchesCalculateButton.setOnClickListener { calculateFeetInches() }
        binding.volumeCalculateButton.setOnClickListener { calculateVolume() }
        binding.tonsCalculateButton.setOnClickListener { calculateTons() }
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

    private fun calculateFeetInches() {
        val firstFeet = value(binding.firstFeet)
        val firstInches = value(binding.firstInches)
        val secondFeet = value(binding.secondFeet)
        val secondInches = value(binding.secondInches)
        val first = firstFeet * 12 + firstInches
        val second = secondFeet * 12 + secondInches
        val operation = binding.operationSpinner.selectedItem.toString()
        binding.feetInchesError.text = ""
        if (firstFeet < 0 || firstInches < 0 || secondFeet < 0 || secondInches < 0) {
            binding.feetInchesError.text = "Values cannot be negative."
            return
        }
        if (firstInches >= 12 || secondInches >= 12) {
            binding.feetInchesError.text = "Inches must be less than 12."
            return
        }
        if (operation == "Divide" && second == 0.0) {
            binding.feetInchesError.text = "Cannot divide by zero."
            return
        }
        when (operation) {
            "Add" -> binding.feetInchesResult.text = formatSignedFeetInches(first + second)
            "Subtract" -> binding.feetInchesResult.text = formatSignedFeetInches(first - second)
            "Multiply" -> binding.feetInchesResult.text = "${formatDecimal(first * second / 144)} sq ft"
            "Divide" -> binding.feetInchesResult.text = formatDecimal(first / second)
        }
        binding.feetInchesSummary.text = "$operation result"
    }

    private fun showTriangleTab() {
        binding.triangleContent.visibility = android.view.View.VISIBLE
        binding.feetInchesContent.visibility = android.view.View.GONE
        binding.volumeContent.visibility = android.view.View.GONE
        binding.tonsContent.visibility = android.view.View.GONE
        setTabState(binding.triangleTabButton, binding.feetInchesTabButton, binding.volumeTabButton, binding.tonsTabButton)
    }

    private fun showFeetInchesTab() {
        binding.triangleContent.visibility = android.view.View.GONE
        binding.feetInchesContent.visibility = android.view.View.VISIBLE
        binding.volumeContent.visibility = android.view.View.GONE
        binding.tonsContent.visibility = android.view.View.GONE
        setTabState(binding.feetInchesTabButton, binding.triangleTabButton, binding.volumeTabButton, binding.tonsTabButton)
    }

    private fun showVolumeTab() {
        binding.triangleContent.visibility = android.view.View.GONE
        binding.feetInchesContent.visibility = android.view.View.GONE
        binding.volumeContent.visibility = android.view.View.VISIBLE
        binding.tonsContent.visibility = android.view.View.GONE
        setTabState(binding.volumeTabButton, binding.triangleTabButton, binding.feetInchesTabButton, binding.tonsTabButton)
    }

    private fun showTonsTab() {
        binding.triangleContent.visibility = android.view.View.GONE
        binding.feetInchesContent.visibility = android.view.View.GONE
        binding.volumeContent.visibility = android.view.View.GONE
        binding.tonsContent.visibility = android.view.View.VISIBLE
        setTabState(binding.tonsTabButton, binding.triangleTabButton, binding.feetInchesTabButton, binding.volumeTabButton)
    }

    private fun setTabState(selected: Button, vararg unselected: Button) {
        selected.alpha = 1f
        unselected.forEach { it.alpha = 0.55f }
    }

    private fun calculateVolume() {
        val heightFeet = value(binding.heightFeet)
        val heightInches = value(binding.heightInches)
        val widthFeet = value(binding.widthFeet)
        val widthInches = value(binding.widthInches)
        val lengthFeet = value(binding.lengthFeet)
        val lengthInches = value(binding.lengthInches)
        binding.volumeError.text = ""
        val dimensions = listOf(heightFeet, heightInches, widthFeet, widthInches, lengthFeet, lengthInches)
        if (dimensions.any { it < 0 }) {
            binding.volumeError.text = "Values cannot be negative."
            return
        }
        if (listOf(heightInches, widthInches, lengthInches).any { it >= 12 }) {
            binding.volumeError.text = "Inches must be less than 12."
            return
        }
        val height = heightFeet * 12 + heightInches
        val width = widthFeet * 12 + widthInches
        val length = lengthFeet * 12 + lengthInches
        if (height == 0.0 || width == 0.0 || length == 0.0) {
            binding.volumeError.text = "Enter a value for all three dimensions."
            return
        }
        val cubicYards = height * width * length / 46656.0
        binding.volumeResult.text = "${formatDecimal(cubicYards)} cubic yd"
        binding.volumeSummary.text = "Order ${kotlin.math.ceil(cubicYards).toInt()} cubic yd"
    }

    private fun calculateTons() {
        val cubicYards = value(binding.tonsCubicYards)
        binding.tonsError.text = ""
        if (cubicYards < 0) {
            binding.tonsError.text = "Cubic yards cannot be negative."
            return
        }
        if (cubicYards == 0.0) {
            binding.tonsError.text = "Enter the cubic yards required."
            return
        }
        val material = binding.materialSpinner.selectedItem.toString()
        val tonsPerCubicYard = when (material) {
            "Sand" -> 1.4
            "Gravel" -> 1.5
            else -> 2.0
        }
        val tons = cubicYards * tonsPerCubicYard
        binding.tonsResult.text = "${formatDecimal(tons)} tons"
        binding.tonsSummary.text = "$material estimate at $tonsPerCubicYard tons per cubic yd"
    }

    private fun formatFeetInches(totalInches: Double): String {
        val feet = (totalInches / 12).toInt()
        val inches = totalInches - feet * 12
        val formattedInches = if (inches < 0.01) "0" else "%.2f".format(inches).trimEnd('0').trimEnd('.')
        return "$feet ft $formattedInches in"
    }

    private fun formatSignedFeetInches(totalInches: Double): String {
        val sign = if (totalInches < 0) "-" else ""
        return sign + formatFeetInches(kotlin.math.abs(totalInches))
    }

    private fun formatDecimal(number: Double): String = "%.4f".format(number).trimEnd('0').trimEnd('.')
}
