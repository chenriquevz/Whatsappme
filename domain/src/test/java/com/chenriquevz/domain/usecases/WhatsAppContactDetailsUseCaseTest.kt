package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.model.*
import com.chenriquevz.domain.utils.WhatsAppCalendarFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class WhatsAppContactDetailsUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var whatsAppContactDetailsUseCase: WhatsAppContactDetailsUseCase

    @Inject
    lateinit var dao: WhatsAppDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun get_whatsapp_contact_with_category_and_timestamp() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val category = Category("other")
        val history = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, history)
        dao.insertWhatsAppContactWithCategory(contact, listOf(category))

        val whatsAppContactWithCategoryAndTimeStamp =
            whatsAppContactDetailsUseCase.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()


        Assert.assertEquals(
            WhatsAppContactWithCategoryAndTimeStamp(
                contact,
                listOf(history), listOf(category)
            ), whatsAppContactWithCategoryAndTimeStamp
        )
    }

    @Test
    fun update_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379")
        val contactUpdated = contact.copy(name = "Henrique")
        val category = Category("other")
        val history = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, history)
        dao.insertWhatsAppContactWithCategory(contact, listOf(category))

        whatsAppContactDetailsUseCase.updateWhatsAppContactName(contactUpdated)

        val whatsAppContactWithCategoryAndTimeStamp =
            whatsAppContactDetailsUseCase.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()


        Assert.assertEquals(
            WhatsAppContactWithCategoryAndTimeStamp(
                contactUpdated,
                listOf(history), listOf(category)
            ), whatsAppContactWithCategoryAndTimeStamp
        )
    }

    @Test
    fun delete_all_history_from_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379")
        val category = Category("other")
        val history = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        val history2 = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 1, contact.phoneNumber)
        val history3 = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 2, contact.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, history)
        dao.insertWhatsAppHistoryWithContact(contact, history2)
        dao.insertWhatsAppHistoryWithContact(contact, history3)
        dao.insertWhatsAppContactWithCategory(contact, listOf(category))

        whatsAppContactDetailsUseCase.deleteWhatsAppHistoryFromWhatsAppContact(contact)

        val whatsAppContactWithCategoryAndTimeStamp =
            whatsAppContactDetailsUseCase.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()


        Assert.assertEquals(
            WhatsAppContactWithCategoryAndTimeStamp(
                contact,
                emptyList(), listOf(category)
            ), whatsAppContactWithCategoryAndTimeStamp
        )
    }

    @Test
    fun delete_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379")
        val category = Category("other")
        val history = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        val history2 = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 1, contact.phoneNumber)
        val history3 = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 2, contact.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, history)
        dao.insertWhatsAppHistoryWithContact(contact, history2)
        dao.insertWhatsAppHistoryWithContact(contact, history3)
        dao.insertWhatsAppContactWithCategory(contact, listOf(category))

        whatsAppContactDetailsUseCase.deleteWhatsAppContact(contact.phoneNumber)

        val whatsAppContactWithCategoryAndTimeStamp =
            whatsAppContactDetailsUseCase.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()
        val contactHistory = dao.getWhatsAppHistoryWithWhatsappContactAndCategories().firstOrNull()
            ?.filter { it.whatsAppHistory.phoneNumberHistory == contact.phoneNumber }
        val contactCategory = dao.getCategoriesWithWhatsAppContact().firstOrNull()
            ?.filter { it.phoneNumbers.contains(contact) }


        Assert.assertEquals(null, whatsAppContactWithCategoryAndTimeStamp)
        Assert.assertEquals(emptyList<WhatsAppHistoryWithWhatsappContact>(), contactHistory)
        Assert.assertEquals(emptyList<CategoryWithWhatsAppContacts>(), contactCategory)
    }

}