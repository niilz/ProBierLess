package de.niilz.probierless.ui.navigation

import androidx.navigation.NavHostController


object NavControllerManager {
    fun navigateTo(uiState: UiStateEnum, navController: NavHostController?) {
        UiState.state = uiState
        navController?.navigate(uiState.route) ?: throw NavigationException("NavController is null")
    }
}