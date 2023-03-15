package com.chenriquevz.domain.utils

import javax.inject.Inject

class WhatsAppCalendarFake @Inject constructor(): TimeHelper {

    override fun timeNow(): Long {
        return timeNowSetup
    }

    companion object {
        var timeNowSetup: Long = 424242
    }

}