package com.chenriquevz.domain.repository

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppContactForDelete
import com.chenriquevz.domain.model.WhatsAppContactWithCategoryAndTimeStamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppContactRepository @Inject constructor(private val whatsAppDao: WhatsAppDao) {

    fun getWhatsAppContactWithCategoryAndHistory(phoneNumber: String) =
        whatsAppDao.getWhatsAppContactWithCategoryAndHistory(phoneNumber)

    suspend fun insertWhatsAppContact(whatsAppContact: WhatsAppContact) =
        whatsAppDao.insertWhatsAppContact(whatsAppContact)

    suspend fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact) =
        whatsAppDao.updateWhatsAppContactName(whatsAppContact)

    suspend fun deleteWhatsAppContact(phoneNumber: String) =
        whatsAppDao.deleteWhatsAppContactAndHistory(phoneNumber)

    suspend fun insertWhatsAppContactWithCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) = whatsAppDao.insertWhatsAppContactWithCategory(whatsAppContact, categories)
    suspend fun deleteCategoryFromWhatsAppContact(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) = whatsAppDao.deleteCategoryFromWhatsAppContact(whatsAppContact, categories)
}