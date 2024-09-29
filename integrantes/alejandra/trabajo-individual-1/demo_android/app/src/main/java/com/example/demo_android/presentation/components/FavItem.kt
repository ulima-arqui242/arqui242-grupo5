package com.example.demo_android.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo_android.presentation.models.Restaurant

@Composable
fun FavItem(
    onClick: () -> Unit,
    fav: Restaurant,
    selectedOption: Restaurant
) {
    ListItem(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFC6C6C6), shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            },
        headlineContent = {
            Text(
                text = fav.name.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        colors = ListItemDefaults.colors(
            overlineColor = Color(0xffC6C6C6),
            containerColor = if(selectedOption == fav) Color.LightGray else Color.Transparent
        ),
        trailingContent = {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "fav", tint = Color.Black)
        }
    )
}