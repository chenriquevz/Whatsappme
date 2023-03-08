package com.chenriquevz.domain.repository

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppHistoryRepository @Inject constructor(private val whatsAppDao: WhatsAppDao) {

    fun getWhatsAppHistory(): Flow<List<WhatsAppHistoryWithWhatsappContact>> =
        whatsAppDao.getWhatsAppHistoryWithWhatsappContactAndCategories()

    fun insertWhatsAppHistory(whatsAppHistory: WhatsAppHistory) =
        whatsAppDao.insertWhatsAppHistory(whatsAppHistory)

    fun deleteWhatsAppHistory(whatsAppHistory: WhatsAppHistory) =
        whatsAppDao.deleteWhatsAppHistory(WhatsAppHistoryForDelete(whatsAppHistory.timeStamp))

    fun deleteWhatsAppHistoryFromWhatsAppContact(whatsAppContact: WhatsAppContact) =
        whatsAppDao.deleteWhatsAppHistoryFromWhatsAppContact(WhatsAppHistoryPhoneNumberForDelete(whatsAppContact.phoneNumber))
}