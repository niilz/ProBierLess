package de.niilz.probierless.ui.navigation

object UiState {
    var state = UiStateEnum.MAIN
}

enum class UiStateEnum(val route: AppRoute) {
    MAIN(MainViewRoute), EDITOR(EditorRoute), SETTINGS(SettingsRoute)
}