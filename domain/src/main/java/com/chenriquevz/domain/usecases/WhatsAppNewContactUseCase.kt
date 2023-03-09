package com.chenriquevz.domain.usecases

import android.util.Log
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppHistory
import com.chenriquevz.domain.repository.WhatsAppContactRepository
import com.chenriquevz.domain.repository.WhatsAppHistoryRepository
import com.chenriquevz.domain.utils.WhatsAppCalendar
import javax.inject.Inject

class WhatsAppNewContactUseCase @Inject constructor(
    private val whatsAppContactRepository: WhatsAppContactRepository,
    private val historyRepository: WhatsAppHistoryRepository,
    private val calendar: WhatsAppCalendar
) {

    fun newContact(whatsAppContact: WhatsAppContact)  {
        whatsAppContactRepository.insertWhatsAppContact(whatsAppContact)
        historyRepository.insertWhatsAppHistory(WhatsAppHistory(calendar.timeNow(), whatsAppContact.phoneNumber))
    }

    fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact) =
        whatsAppContactRepository.updateWhatsAppContactName(whatsAppContact)

}