package com.example.homework2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework2.components.SecondScreen
import com.example.homework2.ui.theme.Homework2Theme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val daysOfWeek = listOf(
            "пн", "вт", "ср", "чт", "пт", "сб", "вс"
        )

        val daysOfMonth = generateMonthDays()

        setContent {
            Homework2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavStart(daysOfWeek, daysOfMonth)
                }
            }
        }
    }
}
val LocalIndex = compositionLocalOf { mutableStateOf(-1) }

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavStart(daysOfWeek: List<String>, daysOfMonth: List<String>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { MainScreen(daysOfWeek, daysOfMonth, navController) }
        composable("{day}/second_screen") {  backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")
            SecondScreen(day!!)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(daysOfWeek: List<String>, daysOfMonth: List<String>, navController: NavHostController) {
    val index = remember { mutableStateOf(-1) }

    CompositionLocalProvider(
        values = arrayOf(LocalIndex provides index),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DaysHat(daysOfWeek)
            DaysOfTheWeek(daysOfMonth)
            Button(
                onClick = {
                          if(index.value > 0) {
                              navController.navigate("${daysOfMonth[index.value]}/second_screen")
                          }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            ){
                Text("Открыть расписание", fontSize = 14.sp)
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun DaysHat(daysOfWeek: List<String>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(7),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(daysOfWeek.size) { item ->
            Card(
                modifier = Modifier.padding(4.dp),
            ) {
                Text(
                    text = daysOfWeek[item],
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DaysOfTheWeek(daysOfMonth: List<String>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(7),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(daysOfMonth.size) { item ->
            val currentLocalIndex = LocalIndex.current
            val color = if(currentLocalIndex.value == item) Color.Red else Color.White
            Card(
                modifier = Modifier.padding(4.dp),
                backgroundColor = color,
                onClick = {
                    if(daysOfMonth[item].isNotEmpty()) {
                        currentLocalIndex.value = item
                    }
                }
            ) {
                Text(
                    text = daysOfMonth[item],
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

fun generateMonthDays(): List<String> {
    val daysInMonth = LocalDate.now().lengthOfMonth()

    val firstDayOffset = LocalDate.now().withDayOfMonth(1).dayOfWeek.ordinal

    val daysOfMonth: MutableList<String> = mutableListOf()
    for (i in 0 until firstDayOffset + daysInMonth) {
        if (i < firstDayOffset) {
            daysOfMonth.add("")
        } else {
            daysOfMonth.add("${i - firstDayOffset + 1}")
        }
    }
    return daysOfMonth
}