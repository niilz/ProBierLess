package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Settings(innerPadding: PaddingValues = PaddingValues(0.dp)) {
    //val settingsViewModel = viewModel<SettingsViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display drink grid without any counts
        // Instruction:
        // Wähle die Getränke, die du maximal pro Tag trinken möchtest
        // Wähle an wie vielen Tagen du maximal dies Menge trinken möchtest
    }
}