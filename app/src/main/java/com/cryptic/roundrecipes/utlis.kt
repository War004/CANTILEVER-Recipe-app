package com.cryptic.roundrecipes

fun formatTime(totalSeconds: Int): String {
    if (totalSeconds < 0) return "N/A"
    if (totalSeconds < 60) return "$totalSeconds sec"
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes > 0 -> "${minutes} min"
        else -> "0 min"
    }
}