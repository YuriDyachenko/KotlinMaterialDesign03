package dyachenko.kotlinmaterialdesign03.model.settings

import dyachenko.kotlinmaterialdesign03.R

object SettingsData {
    const val PREFERENCE_NAME = "Settings"
    const val CURRENT_THEME_PREF_NAME = "THEME"
    const val CURRENT_EARTH_LON = "EARTH_LON"
    const val CURRENT_EARTH_LAT = "EARTH_LAT"
    const val CURRENT_EARTH_DIM = "EARTH_DIM"

    const val THEME_PURPLE_ID = R.style.Theme_KotlinMaterialDesign_Purple
    const val THEME_TEAL_ID = R.style.Theme_KotlinMaterialDesign_Teal
    const val THEME_PURPLE = 0
    const val THEME_TEAL = 1

    const val TODAY_PHOTO = 0
    const val YESTERDAY_PHOTO = 1
    const val DAY_BEFORE_YESTERDAY_PHOTO = 2

    const val EARTH_DEF_LON = 37.883211F
    const val EARTH_DEF_LAT = 47.908572F
    const val EARTH_DEF_DIM = 0.1F

    private const val MARS_ROVER_CAMERA_FHAZ = "fhaz"

    var currentTheme = THEME_PURPLE
    var dayOfPhoto = TODAY_PHOTO
    var earthLon = EARTH_DEF_LON
    var earthLat = EARTH_DEF_LAT
    var earthDim = EARTH_DEF_DIM
    var marsRoverCamera = MARS_ROVER_CAMERA_FHAZ
}