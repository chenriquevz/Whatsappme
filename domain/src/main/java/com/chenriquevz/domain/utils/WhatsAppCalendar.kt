package com.chenriquevz.domain.utils

import java.util.*
import javax.inject.Inject

class WhatsAppCalendar @Inject constructor() {

    fun timeNow() = Calendar.getInstance().timeInMillis
}