package com.chenriquevz.domain.data

import androidx.room.*
import com.chenriquevz.domain.model.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WhatsAppDao {
    @Transaction
    @Query("SELECT * FROM WhatsAppContact WHERE phoneNumber IN (:phoneNumber)")
    fun getWhatsAppEntryWithCategoryAndTimeStamp(phoneNumber: Long): Flow<WhatsAppContactWithCategoryAndTimeStamp>

    @Transaction
    @Query("SELECT * FROM Category WHERE categoryName IN (:category)")
    fun getCategoriesAndWhatsappEntriesPair(category: String): Flow<CategoryWithWhatsAppContacts>

    @Transaction
    @Query("SELECT * FROM WhatsAppHistory")
    fun getWhatsAppHistoryWithWhatsappContact(): Flow<List<WhatsAppHistoryWithWhatsappContact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWhatsAppEntry(whatsAppContact: WhatsAppContact)

    @Update
    fun updateWhatsAppEntryName(whatsAppContact: WhatsAppContact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWhatsAppHistory(whatsAppHistory: WhatsAppHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWhatsAppEntryCategory(whatsAppEntriesWithCategory: WhatsAppContactWithCategory)

    @Delete(entity = Category::class)
    fun deleteCategory(categoryName: CategoryForDelete)

    @Delete(entity = WhatsAppContact::class)
    fun deleteWhatsAppEntry(phoneNumber: WhatsAppContactForDelete)

    @Delete(entity = WhatsAppHistory::class)
    fun deleteWhatsAppHistory(timeStamp: WhatsAppHistoryForDelete)

    @Delete(entity = WhatsAppHistory::class)
    fun deleteWhatsAppHistoryFromAPhoneNumber(phoneNumber: WhatsAppHistoryPhoneNumberForDelete)

    @Transaction
    fun insertWhatsAppEntriesWithCategory(
        whatsAppContact: WhatsAppContact,
        categories: List<Category>
    ) {
        insertWhatsAppEntry(whatsAppContact)
        categories.forEach { category ->
            insertCategory(category)
            insertWhatsAppEntryCategory(WhatsAppContactWithCategory(whatsAppContact.phoneNumber, category.categoryName))
        }
    }

}