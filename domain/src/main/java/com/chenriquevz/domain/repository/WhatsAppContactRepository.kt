package com.chenriquevz.domain.repository

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppContactForDelete
import com.chenriquevz.domain.model.WhatsAppContactWithCategoryAndTimeStamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppContactRepository @Inject constructor(private val whatsAppDao: WhatsAppDao) {

    fun getWhatsAppContactWithCategoryAndHistory(phoneNumber: Long): Flow<WhatsAppContactWithCategoryAndTimeStamp> =
        whatsAppDao.getWhatsAppContactWithCategoryAndHistory(phoneNumber)

    fun insertWhatsAppContact(whatsAppContact: WhatsAppContact) =
        whatsAppDao.insertWhatsAppContact(whatsAppContact)

    fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact) =
        whatsAppDao.updateWhatsAppContactName(whatsAppContact)

    fun deleteWhatsAppContact(whatsAppContact: WhatsAppContact) =
        whatsAppDao.deleteWhatsAppContact(WhatsAppContactForDelete(whatsAppContact.phoneNumber))

    fun insertWhatsAppContactWithCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) = whatsAppDao.insertWhatsAppContactWithCategory(whatsAppContact, categories)
}