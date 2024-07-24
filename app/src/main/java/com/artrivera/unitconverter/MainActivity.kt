package com.artrivera.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artrivera.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold { innerPadding ->
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("Kg") }
    var toUnit by remember { mutableStateOf("Lb") }
    var result by remember { mutableDoubleStateOf(0.0) }
    var conversionFactor by remember { mutableDoubleStateOf(1.0) }
    var fromUnitExpanded by remember { mutableStateOf(false) }
    var toUnitExpanded by remember { mutableStateOf(false) }

    val fromUnitButtonText = fromUnit.ifEmpty { "Select Unit" }
    val toUnitButtonText = toUnit.ifEmpty { "Select Unit" }

    fun toggleFromUnitDropdown() {
        fromUnitExpanded = !fromUnitExpanded
    }

    fun toggleToUnitDropdown() {
        toUnitExpanded = !toUnitExpanded
    }

    fun setConversionFactor() {
        conversionFactor = when {
            fromUnit == "Centimeters" && toUnit == "Meters" -> 0.01
            fromUnit == "Centimeters" && toUnit == "Feet" -> 0.0328084
            fromUnit == "Centimeters" && toUnit == "Millimeters" -> 10.0
            fromUnit == "Meters" && toUnit == "Centimeters" -> 100.0
            fromUnit == "Meters" && toUnit == "Feet" -> 3.28084
            fromUnit == "Meters" && toUnit == "Millimeters" -> 1000.0
            fromUnit == "Feet" && toUnit == "Centimeters" -> 30.48
            fromUnit == "Feet" && toUnit == "Meters" -> 0.3048
            fromUnit == "Feet" && toUnit == "Millimeters" -> 304.8
            fromUnit == "Millimeters" && toUnit == "Centimeters" -> 0.1
            fromUnit == "Millimeters" && toUnit == "Meters" -> 0.001
            fromUnit == "Millimeters" && toUnit == "Feet" -> 0.00328084
            fromUnit == "Kg" && toUnit == "Lb" -> 2.20462
            fromUnit == "Lb" && toUnit == "Kg" -> 0.453592
            else -> 1.0
        }
    }

    fun calculateResult() {
        result = try {
            inputValue.toDouble() * conversionFactor
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    fun selectFromUnit(unit: String) {
        fromUnit = unit
        toggleFromUnitDropdown()
        setConversionFactor()
        calculateResult()
    }

    fun selectToUnit(unit: String) {
        toUnit = unit
        toggleToUnitDropdown()
        setConversionFactor()
        calculateResult()
    }





    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                setConversionFactor()
                calculateResult()
            },
            placeholder = { Text("Enter a value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row {
            Box {
                Button(onClick = {
                    toggleFromUnitDropdown()
                }) {
                    Text(text = fromUnitButtonText)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Unit")
                }
                DropdownMenu(expanded = fromUnitExpanded, onDismissRequest = {
                    toggleFromUnitDropdown()
                }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        selectFromUnit("Centimeters")
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        selectFromUnit("Meters")
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        selectFromUnit("Feet")
                    })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        selectFromUnit("Millimeters")
                    })
                    DropdownMenuItem(text = { Text("Kg") }, onClick = {
                        selectFromUnit("Kg")
                    })
                    DropdownMenuItem(text = { Text("Lb") }, onClick = {
                        selectFromUnit("Lb")
                    })
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            Box {
                Button(onClick = {
                    toggleToUnitDropdown()
                }) {
                    Text(text = toUnitButtonText)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Unit")
                }
                DropdownMenu(expanded = toUnitExpanded, onDismissRequest = {
                    toggleToUnitDropdown()
                }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        selectToUnit("Centimeters")
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        selectToUnit("Meters")
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        selectToUnit("Feet")
                    })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        selectToUnit("Millimeters")
                    })
                    DropdownMenuItem(text = { Text("Kg") }, onClick = {
                        selectToUnit("Kg")
                    })
                    DropdownMenuItem(text = { Text("Lb") }, onClick = {
                        selectToUnit("Lb")
                    })
                }
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Result: $result", fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}