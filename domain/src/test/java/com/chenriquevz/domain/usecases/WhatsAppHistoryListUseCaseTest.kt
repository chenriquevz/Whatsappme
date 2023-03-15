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
class WhatsAppHistoryListUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var whatsAppHistoryListUseCase: WhatsAppHistoryListUseCase

    @Inject
    lateinit var dao: WhatsAppDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun get_history_list() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val contactHistory = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        val contact2 = WhatsAppContact("+5521982929212")
        val contactHistory2 =
            WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 1, contact2.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, contactHistory)
        dao.insertWhatsAppHistoryWithContact(contact2, contactHistory2)


        val historyList = whatsAppHistoryListUseCase.getHistoryList().firstOrNull()


        Assert.assertEquals(2, historyList?.size)
        Assert.assertEquals(
            WhatsAppHistoryWithWhatsappContact(
                contactHistory,
                WhatsAppContactWithCategoryAndTimeStamp(
                    contact,
                    listOf(contactHistory),
                    emptyList()
                )
            ), historyList?.firstOrNull()
        )
        Assert.assertEquals(
            WhatsAppHistoryWithWhatsappContact(
                contactHistory2,
                WhatsAppContactWithCategoryAndTimeStamp(
                    contact2,
                    listOf(contactHistory2),
                    emptyList()
                )
            ), historyList?.getOrNull(1)
        )
    }


    @Test
    fun delete_history_from_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val contactHistory = WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup, contact.phoneNumber)
        val contact2 = WhatsAppContact("+5521982929212")
        val contactHistory2 =
            WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 1, contact2.phoneNumber)
        val contactHistory21 =
            WhatsAppHistory(WhatsAppCalendarFake.timeNowSetup + 2, contact2.phoneNumber)
        dao.insertWhatsAppHistoryWithContact(contact, contactHistory)
        dao.insertWhatsAppHistoryWithContact(contact2, contactHistory2)
        dao.insertWhatsAppHistoryWithContact(contact2, contactHistory21)

        whatsAppHistoryListUseCase.deleteHistoryEntry(contactHistory21)

        val historyList = whatsAppHistoryListUseCase.getHistoryList().firstOrNull()


        Assert.assertEquals(2, historyList?.size)
        Assert.assertEquals(
            WhatsAppHistoryWithWhatsappContact(
                contactHistory,
                WhatsAppContactWithCategoryAndTimeStamp(
                    contact,
                    listOf(contactHistory),
                    emptyList()
                )
            ), historyList?.firstOrNull()
        )
        Assert.assertEquals(
            WhatsAppHistoryWithWhatsappContact(
                contactHistory2,
                WhatsAppContactWithCategoryAndTimeStamp(
                    contact2,
                    listOf(contactHistory2),
                    emptyList()
                )
            ), historyList?.getOrNull(1)
        )
    }

    @Test
    fun add_contact_as_favorite() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        dao.insertWhatsAppContact(contact)

        whatsAppHistoryListUseCase.addContactAsFavorite(contact)

        val contactWithCategory =
            dao.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber).firstOrNull()


        Assert.assertEquals(
            WhatsAppContactWithCategoryAndTimeStamp(
                contact,
                emptyList(), listOf(
                    Category(Category.DefaultCategories.FAVORITE)
                )
            ), contactWithCategory
        )
    }

    @Test
    fun remove_contact_from_favorite() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        dao.insertWhatsAppContact(contact)
        dao.insertWhatsAppContactWithCategory(
            contact, listOf(
                Category(Category.DefaultCategories.FAVORITE)
            )
        )

        whatsAppHistoryListUseCase.removeContactAsFavorite(contact)

        val contactWithoutCategory =
            dao.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber).firstOrNull()


        Assert.assertEquals(
            WhatsAppContactWithCategoryAndTimeStamp(
                contact,
                emptyList(), emptyList()
            ), contactWithoutCategory
        )
    }

}