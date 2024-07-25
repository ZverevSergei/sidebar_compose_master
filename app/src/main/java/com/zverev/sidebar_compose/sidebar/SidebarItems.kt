package com.zverev.sidebar_compose.sidebar

import androidx.compose.ui.graphics.vector.ImageVector

data class SidebarItems(
    val icon: ImageVector,
    val text: String,
    val badgeCount: Int,
    val hasBadge: Boolean
)