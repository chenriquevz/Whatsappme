package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.CategoryWithWhatsAppContacts
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.repository.WhatsAppCategoryRepository
import com.chenriquevz.domain.repository.WhatsAppContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppCategoryUseCase @Inject constructor(
    private val whatsAppCategoryRepository: WhatsAppCategoryRepository,
    private val whatsAppContactRepository: WhatsAppContactRepository
) {

    fun getCategoryWithContacts(): Flow<List<CategoryWithWhatsAppContacts>> =
        whatsAppCategoryRepository.getCategoriesWithWhatsAppContact()

    fun addContactCategory(whatsAppContact: WhatsAppContact, categoryList: List<Category>) =
        whatsAppContactRepository.insertWhatsAppContactWithCategory(
            whatsAppContact,
            categoryList
        )

    fun removeContactCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) = whatsAppContactRepository.deleteCategoryFromWhatsAppContact(whatsAppContact, categories)

    fun newCategory(category: Category) {
        whatsAppCategoryRepository.insertCategory(category)
    }

    fun deleteCategory(category: Category) {
        whatsAppCategoryRepository.deleteCategory(category)
    }

}