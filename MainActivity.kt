package com.example.calculator
import android.os.Bundle


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                ScaffoldApp()
            }
        }
    }
}


@Composable
fun ScaffoldApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("converter") { Converter(navController) }

        }
    }
}



@Composable
fun HomeScreen(navController: NavController) {
    var inputNum by remember { mutableStateOf("") }
    var resultNum by remember { mutableStateOf("0") }
    var firstOperand by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf<String?>(null) }
    var displayEquation by remember { mutableStateOf("") }

    val buttons = listOf(
        listOf("7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        NavRimon(navController = navController)
        // Display the equation and result
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = displayEquation,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = resultNum,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(buttons.flatten()) { button ->
                FloatingActionButton(
                    onClick = {
                        when (button) {
                            "C" -> {
                                inputNum = ""
                                resultNum = "0"
                                firstOperand = ""
                                operator = null
                                displayEquation = ""
                            }
                            "=" -> {
                                if (firstOperand.isNotEmpty() && operator != null) {
                                    try {
                                        val secondOperand = inputNum
                                        val result = when (operator) {
                                            "+" -> firstOperand.toDouble() + secondOperand.toDouble()
                                            "-" -> firstOperand.toDouble() - secondOperand.toDouble()
                                            "×" -> firstOperand.toDouble() * secondOperand.toDouble()
                                            "÷" -> firstOperand.toDouble() / secondOperand.toDouble()
                                            else -> null
                                        }
                                        displayEquation = "$displayEquation "
                                        resultNum = result?.toString() ?: "Error"
                                        inputNum = ""
                                        firstOperand = resultNum
                                        operator = null
                                    } catch (e: Exception) {
                                        resultNum = "Error"
                                    }
                                }
                            }
                            "+", "-", "×", "÷" -> {
                                if (inputNum.isNotEmpty()) {
                                    firstOperand = inputNum
                                    operator = button
                                    displayEquation = "$firstOperand $operator"
                                    inputNum = ""
                                }
                            }
                            else -> {
                                inputNum += button
                                displayEquation = "$displayEquation$button"
                            }
                        }
                    },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = button,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}



@Composable
fun Converter(navController: NavController) {
    val converters = listOf(
        "Length" to R.drawable.length,
        "Weight" to R.drawable.mass,
        "Volume" to R.drawable.volume,
        "Temperature" to R.drawable.temperature,
        "Speed" to R.drawable.speed,
        "Area" to R.drawable.area,
        "Time" to R.drawable.time,
        "Age" to R.drawable.age,
        "Speed" to R.drawable.speed,
        "Time Left" to R.drawable.timeleft,
        "BMI" to R.drawable.bmi,
        "Data" to R.drawable.data,

        "Time" to R.drawable.time,
        "Age" to R.drawable.age,
        "Speed" to R.drawable.speed,
        "Time Left" to R.drawable.timeleft,
        "BMI" to R.drawable.bmi,
        "Data" to R.drawable.data
    )

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)

    ){
        NavRimon(navController)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(56.dp)

        ) {
            items(converters){(title, drawableRes) ->
                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    Image(
                        painter = painterResource(id = drawableRes),
                        contentDescription = title,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = title
                    )
                }

            }
        }

    }

}




@Composable
fun NavRimon(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { navController.navigate("home") }) {
                Text(
                    text = "Calculator",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            TextButton(onClick = { navController.navigate("converter") }) {
                Text(
                    text = "Converter",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = Color.White
                    )
                )
            }
        }
    }
}

