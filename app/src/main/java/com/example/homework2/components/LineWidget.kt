package com.example.homework2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LineWidget(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Circle()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 20.sp,
        )
    }
}
@Composable
fun Circle() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(3.dp, Color.Red, CircleShape)
            .size(5.dp)
            .background(Color.Red)
    )
}