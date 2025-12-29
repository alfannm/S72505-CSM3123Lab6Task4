package com.example.tabsensorlab

// Prefs object acts as a centralized place
// to store SharedPreferences file name and keys
object Prefs {

    // Name of the SharedPreferences file
    const val FILE_NAME = "user_prefs"

    // Key used to store and retrieve the username
    const val KEY_USERNAME = "username"

    // Key used to store and retrieve the selected theme
    const val KEY_THEME = "theme"

    // Key used to store and retrieve the selected font size
    const val KEY_FONT_SIZE = "fontSize"
}
