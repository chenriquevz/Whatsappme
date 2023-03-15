package com.chenriquevz.domain.repository

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.CategoryForDelete
import com.chenriquevz.domain.model.CategoryWithWhatsAppContacts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhatsAppCategoryRepository @Inject constructor(private val whatsAppDao: WhatsAppDao) {

    fun getCategoriesWithWhatsAppContact(): Flow<List<CategoryWithWhatsAppContacts>> =
        whatsAppDao.getCategoriesWithWhatsAppContact()

    suspend fun insertCategory(category: Category) = whatsAppDao.insertCategory(category)

    suspend fun deleteCategory(category: Category) = whatsAppDao.deleteCategory(CategoryForDelete(category.categoryName))

}