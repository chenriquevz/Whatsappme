package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppContactForDelete
import com.chenriquevz.domain.model.WhatsAppHistory
import com.chenriquevz.domain.model.WhatsAppHistoryPhoneNumberForDelete
import com.chenriquevz.domain.repository.WhatsAppContactRepository
import com.chenriquevz.domain.repository.WhatsAppHistoryRepository
import java.util.*
import javax.inject.Inject

class WhatsAppContactDetailsUseCase @Inject constructor(
    private val whatsAppContactRepository: WhatsAppContactRepository,
    private val historyRepository: WhatsAppHistoryRepository,
) {

    fun getWhatsAppContactWithCategoryAndHistory(phoneNumber: String) =
        whatsAppContactRepository.getWhatsAppContactWithCategoryAndHistory(phoneNumber)

    fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact) =
        whatsAppContactRepository.updateWhatsAppContactName(whatsAppContact)

    fun deleteWhatsAppContact(phoneNumber: String) =
        whatsAppContactRepository.deleteWhatsAppContact(phoneNumber)

    fun deleteWhatsAppHistoryFromWhatsAppContact(whatsAppContact: WhatsAppContact) =
        historyRepository.deleteWhatsAppHistoryFromWhatsAppContact(whatsAppContact)


}