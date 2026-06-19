package com.mariii.readdiary.domain.model

data class ReminderSettings(

    val daily: Boolean = true,

    val everyDays: Int = 1,

    val hour: Int = 12,

    val minute: Int = 0
)