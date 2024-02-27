package com.lunardev.kotlinallfeature.feature.menu.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.lunardev.kotlinallfeature.R

enum class Sections(@StringRes val titleResId: Int) {
    Local(R.string.menu_section_local),
    API(R.string.menu_section_api),
    External(R.string.menu_section_external)
}

enum class Menus(val title: Int, val icon: ImageVector, val section: Sections) {
    ImagePicker(R.string.menu_content_image_picker, Icons.Filled.Image, Sections.Local),
    Camera(R.string.menu_content_camera, Icons.Filled.Camera, Sections.Local),
    GPS(R.string.menu_content_gps, Icons.Filled.LocationOn, Sections.Local),
    Fingerprint(R.string.menu_content_fingerprint, Icons.Filled.Fingerprint, Sections.Local),

    FirebaseNotification(R.string.menu_content_firebase_notification,
        Icons.Filled.Notifications, Sections.External),
    GoogleMap(R.string.menu_content_google_map,
        Icons.Filled.Map, Sections.External),
}

sealed class MenuDestinations(val route: String) {

    data object Camera : MenuDestinations(route = "menu/camera")
    data object ImagePicker : MenuDestinations(route = "menu/image-picker")
    data object GPS : MenuDestinations(route = "menu/gps")
    data object Fingerprint : MenuDestinations(route = "menu/fingerprint")
    data object FirebaseNotification : MenuDestinations(route = "menu/firebase-notification")
    data object GoogleMap : MenuDestinations(route = "menu/google-map")
}