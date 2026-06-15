package com.example.nexusviewer

data class DashboardResponse(
    val entities: List<Map<String, String>>,
    val entityTotal: Int
)