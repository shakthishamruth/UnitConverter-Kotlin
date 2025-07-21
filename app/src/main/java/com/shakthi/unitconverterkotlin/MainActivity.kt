package com.shakthi.unitconverterkotlin

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shakthi.unitconverterkotlin.ui.theme.UnitConverterKotlinTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterKotlinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@Composable
fun UnitConverter() {

    var inputValue by remember { mutableStateOf("0") }
    var outputValue by remember { mutableStateOf("0") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(1.0) }
    val oConversionFactor = remember { mutableStateOf(1.0) }

    fun convertUnit() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnit()
            },
            label = { Text(text = "Enter Value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "$inputValue $inputUnit is equal to $outputValue $outputUnit")

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            // Input Box
            Box {
                Button(
                    onClick = { iExpanded = true }, modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "ArrowDropDown")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                        conversionFactor.value = 1.0
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                        conversionFactor.value = 0.3048
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                        conversionFactor.value = 0.01
                        convertUnit()
                    })
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = "→", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.width(16.dp))

            // Output Box
            Box {
                Button(
                    onClick = { oExpanded = true }, modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "ArrowDropDown")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        oConversionFactor.value = 1.0
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        oConversionFactor.value = 0.3048
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeters"
                        oConversionFactor.value = 0.01
                        convertUnit()
                    })
                }
            }
        }
    }
}

