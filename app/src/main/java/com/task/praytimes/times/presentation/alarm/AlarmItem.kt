package com.task.praytimes.times.presentation.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val timing: String
)