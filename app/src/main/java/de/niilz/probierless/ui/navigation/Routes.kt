package de.niilz.probierless.ui.navigation

import kotlinx.serialization.Serializable

interface AppRoute

@Serializable
object MainViewRoute : AppRoute

@Serializable
object EditorRoute : AppRoute

@Serializable
object SettingsRoute : AppRoute
