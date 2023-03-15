package com.chenriquevz.domain.data

import androidx.room.*
import com.chenriquevz.domain.model.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WhatsAppDao {
    @Transaction
    @Query("SELECT * FROM WhatsAppContact WHERE phoneNumber IN (:phoneNumber)")
    fun getWhatsAppContactWithCategoryAndHistory(phoneNumber: String): Flow<WhatsAppContactWithCategoryAndTimeStamp>

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategoriesWithWhatsAppContact(): Flow<List<CategoryWithWhatsAppContacts>>

    @Transaction
    @Query("SELECT * FROM WhatsAppHistory")
    fun getWhatsAppHistoryWithWhatsappContactAndCategories(): Flow<List<WhatsAppHistoryWithWhatsappContact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWhatsAppContact(whatsAppContact: WhatsAppContact)

    //TODO remove
    @Query("SELECT * FROM WhatsAppContact")
    fun getWhatsAppContact(): Flow<WhatsAppContact>

    @Update
    suspend fun updateWhatsAppContactName(whatsAppContact: WhatsAppContact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWhatsAppHistory(whatsAppHistory: WhatsAppHistory)

    @Delete(entity = Category::class)
    suspend fun deleteCategory(categoryName: CategoryForDelete)

    @Delete(entity = WhatsAppContact::class)
    suspend fun deleteWhatsAppContact(phoneNumber: WhatsAppContactForDelete)

    @Delete(entity = WhatsAppHistory::class)
    suspend fun deleteWhatsAppHistory(timeStamp: WhatsAppHistoryForDelete)

    @Delete(entity = WhatsAppHistory::class)
    suspend fun deleteWhatsAppHistoryFromWhatsAppContact(phoneNumber: WhatsAppHistoryPhoneNumberForDelete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWhatsAppContactWithCategoryCrossReference(whatsAppContactWithCategory: WhatsAppContactWithCategory)
    @Delete()
    suspend fun deleteWhatsAppContactWithCategoryCrossReference(whatsAppContactWithCategory: WhatsAppContactWithCategory)


    @Transaction
    suspend fun deleteWhatsAppContactAndHistory(phoneNumber: String) {
        deleteWhatsAppHistoryFromWhatsAppContact(WhatsAppHistoryPhoneNumberForDelete(phoneNumber))
        deleteWhatsAppContact(WhatsAppContactForDelete(phoneNumber))

    }
    @Transaction
    suspend fun insertWhatsAppHistoryWithContact(
        whatsAppContact: WhatsAppContact,
        whatsAppHistory: WhatsAppHistory
    ) {
        insertWhatsAppContact(whatsAppContact)
        insertWhatsAppHistory(whatsAppHistory)
    }

    @Transaction
    suspend fun insertWhatsAppContactWithCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) {
        insertWhatsAppContact(whatsAppContact)
        categories.forEach { category ->
            insertCategory(category)
            insertWhatsAppContactWithCategoryCrossReference(WhatsAppContactWithCategory(whatsAppContact.phoneNumber, category.categoryName))
        }
    }

    @Transaction
    suspend fun deleteCategoryFromWhatsAppContact(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) {
        categories.forEach { category ->
            deleteWhatsAppContactWithCategoryCrossReference(WhatsAppContactWithCategory(whatsAppContact.phoneNumber, category.categoryName))
        }
    }

}