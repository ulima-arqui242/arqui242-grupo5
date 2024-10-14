package com.example.demo_android.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo_android.ui.theme.primaryGreen

@Composable
fun DayItem(
    id: Int,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFC6C6C6), shape = RoundedCornerShape(15.dp)),
        headlineContent = {
            Text(
                text = "Día $id: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        trailingContent = {
            IconButton(
                onClick = {
                    onClick()
                }
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "add", tint = primaryGreen)
            }
        }
    )
}