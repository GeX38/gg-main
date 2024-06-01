package com.example.gg

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val organizer: String = "",
    val registeredUsers: MutableList<String> = mutableListOf()
)
