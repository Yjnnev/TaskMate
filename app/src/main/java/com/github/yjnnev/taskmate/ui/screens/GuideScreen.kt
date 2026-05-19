package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.yjnnev.taskmate.ui.components.SteelPlate
import com.github.yjnnev.taskmate.ui.theme.Blue
import com.github.yjnnev.taskmate.ui.theme.Green
import com.github.yjnnev.taskmate.ui.theme.Indigo
import com.github.yjnnev.taskmate.ui.theme.NoteAppTheme
import com.github.yjnnev.taskmate.ui.theme.Orange
import com.github.yjnnev.taskmate.ui.theme.Violet
import com.github.yjnnev.taskmate.ui.theme.Yellow

@Composable
fun SampleHomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        // Row 1: Single Red Plate
        SteelPlate(
            title = "RED",
            baseColor = Color.Red,
            darkColor = Color(0xFF7F0000),
            modifier = Modifier.weight(1f)
        )

        // Row 2: Orange and Yellow Plates
        Row(modifier = Modifier.weight(1f)) {
            SteelPlate("ORANGE", Orange, Color(0xFFCC5500), Modifier.weight(1f), false)
            SteelPlate("YELLOW", Yellow, Color(0xFFB8860B), Modifier.weight(1f), false)
        }

        // Row 3: Green and Blue Plates
        Column(modifier = Modifier.weight(2f)) {
            SteelPlate("GREEN", Green, Color(0xFF006400), Modifier.weight(1f))
            SteelPlate("BLUE", Blue, Color(0xFF00008B), Modifier.weight(1f), false)
        }

        // Row 4: Indigo and Violet Plates
        Row(modifier = Modifier.weight(1f)) {
            SteelPlate("INDIGO", Indigo, Color(0xFF2E0854), Modifier.weight(1f), false)
            SteelPlate("VIOLET", Violet, Color(0xFF4B0082), Modifier.weight(1f), false)
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    NoteAppTheme(darkTheme = false) {
        SampleHomeScreen()
    }
}
