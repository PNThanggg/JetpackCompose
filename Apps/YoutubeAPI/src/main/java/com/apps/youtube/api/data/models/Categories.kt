package com.apps.youtube.api.data.models

object Categories {
    val categoryMap: Map<String, String?> = mapOf(
        "All" to null, // Null means "all" categories (no filter)
        "Trending" to null, // Handled by YouTube Trending API, not a category
        "Mobile app development" to "20", "News" to "25", // News & Politics
        "Coding" to "28", // Science & Technology
        "8K resolution" to "2", "Technology" to "28", // Science & Technology
        "Entertainment" to "24", // Travel & Events
        "Health & Fitness" to "17", // Sports (closest match for health & fitness)
        "Music" to "10", // Music
        "4K resolution" to "3", "Thrillers" to "9", "Gaming" to "20", // Gaming
        "Movies & TV Shows" to "24", // Entertainment
        "Sports" to "17", // Sports
        "Fashion & Beauty" to "26", // Howto & Style
        "Comedy" to "23", // Comedy
        "Food" to "26", // Howto & Style (Food falls under this)
        "Science & Nature" to "28" // Science & Technology
    )
}

object ChannelTabs {
    val tabs: Map<String, String> = mapOf(
        "Home" to "1", "Videos" to "2", "Playlists" to "3", "Community" to "4"
    )
}