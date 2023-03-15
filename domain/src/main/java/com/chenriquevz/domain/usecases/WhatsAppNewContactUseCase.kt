package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppHistory
import com.chenriquevz.domain.repository.WhatsAppContactRepository
import com.chenriquevz.domain.repository.WhatsAppHistoryRepository
import com.chenriquevz.domain.utils.TimeHelper
import javax.inject.Inject

class WhatsAppNewContactUseCase @Inject constructor(
    private val whatsAppContactRepository: WhatsAppContactRepository,
    private val historyRepository: WhatsAppHistoryRepository,
    private val calendar: TimeHelper
) {

    suspend fun newContact(whatsAppContact: WhatsAppContact) {
        historyRepository.insertWhatsAppHistory(whatsAppContact, WhatsAppHistory(calendar.timeNow(), whatsAppContact.phoneNumber))
    }

    suspend fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact) =
        whatsAppContactRepository.updateWhatsAppContactName(whatsAppContact)

}