package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.model.WhatsAppHistory
import com.chenriquevz.domain.model.WhatsAppHistoryWithWhatsappContact
import com.chenriquevz.domain.repository.WhatsAppContactRepository
import com.chenriquevz.domain.repository.WhatsAppHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppHistoryListUseCase @Inject constructor(
    private val historyRepository: WhatsAppHistoryRepository,
    private val whatsAppContactRepository: WhatsAppContactRepository
) {

    fun getHistoryList(): Flow<List<WhatsAppHistoryWithWhatsappContact>> =
        historyRepository.getWhatsAppHistory()

    fun deleteHistoryEntry(whatsAppHistory: WhatsAppHistory) =
        historyRepository.deleteWhatsAppHistory(whatsAppHistory)

    fun addContactAsFavorite(whatsAppContact: WhatsAppContact) =
        whatsAppContactRepository.insertWhatsAppContactWithCategory(
            whatsAppContact,
            listOf(
                Category(Category.DefaultCategories.FAVORITE)
            )
        )

    fun removeContactAsFavorite(
        whatsAppContact: WhatsAppContact,
    ) = whatsAppContactRepository.deleteCategoryFromWhatsAppContact(
        whatsAppContact,
        listOf(
            Category(Category.DefaultCategories.FAVORITE)
        )
    )

}