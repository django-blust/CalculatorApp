package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    SimpleCalculator()
                }
            }
        }
    }
}

@Composable
fun SimpleCalculator() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = input,
                fontSize = 28.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = result,
                fontSize = 36.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        val buttons = listOf(
            listOf("7", "8", "9", "C"),
            listOf("4", "5", "6", "+"),
            listOf("1", "2", "3", "-"),
            listOf("=","0", "/")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "C" -> {
                                    input = ""
                                    result = ""
                                }
                                "=" -> {
                                    result = calculateSimpleResult(input)
                                }
                                else -> {
                                    input += label
                                }
                            }
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = label, fontSize = 24.sp, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

fun calculateSimpleResult(input: String): String {
    return try {
        val parts = input.split("+", "-", "/")
        if (parts.size != 2) return "Error"
        val num1 = parts[0].toFloat()
        val num2 = parts[1].toFloat()

        when {
            input.contains("+") -> (num1 + num2).toString()
            input.contains("-") -> (num1 - num2).toString()
            input.contains("/") -> if (num2 != 0f) (num1 / num2).toString() else "Error"
            else -> "Error"
        }
    } catch (e: Exception) {
        "Error"
    }
}
