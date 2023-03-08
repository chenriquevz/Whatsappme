package com.chenriquevz.domain.data

import androidx.room.*
import com.chenriquevz.domain.model.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WhatsAppDao {
    @Transaction
    @Query("SELECT * FROM WhatsAppContact WHERE phoneNumber IN (:phoneNumber)")
    fun getWhatsAppContactWithCategoryAndHistory(phoneNumber: Long): Flow<WhatsAppContactWithCategoryAndTimeStamp>

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategoriesWithWhatsAppContact(): Flow<CategoryWithWhatsAppContacts>

    @Transaction
    @Query("SELECT * FROM WhatsAppHistory")
    fun getWhatsAppHistoryWithWhatsappContactAndCategories(): Flow<List<WhatsAppHistoryWithWhatsappContact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWhatsAppContact(whatsAppContact: WhatsAppContact)

    @Update
    fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWhatsAppHistory(whatsAppHistory: WhatsAppHistory)

    @Delete(entity = Category::class)
    fun deleteCategory(categoryName: CategoryForDelete)

    @Delete(entity = WhatsAppContact::class)
    fun deleteWhatsAppContact(phoneNumber: WhatsAppContactForDelete)

    @Delete(entity = WhatsAppHistory::class)
    fun deleteWhatsAppHistory(timeStamp: WhatsAppHistoryForDelete)

    @Delete(entity = WhatsAppHistory::class)
    fun deleteWhatsAppHistoryFromWhatsAppContact(phoneNumber: WhatsAppHistoryPhoneNumberForDelete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWhatsAppContactWithCategoryCrossReference(whatsAppContactWithCategory: WhatsAppContactWithCategory)
    @Transaction
    fun insertWhatsAppContactWithCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) {
        insertWhatsAppContact(whatsAppContact)
        categories.forEach { category ->
            insertCategory(category)
            insertWhatsAppContactWithCategoryCrossReference(WhatsAppContactWithCategory(whatsAppContact.phoneNumber, category.categoryName))
        }
    }

}