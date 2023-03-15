package com.chenriquevz.domain.utils

import java.util.*
import javax.inject.Inject

interface TimeHelper {
    fun timeNow(): Long
}
class WhatsAppCalendar @Inject constructor(): TimeHelper {
    override fun timeNow() = Calendar.getInstance().timeInMillis
}
