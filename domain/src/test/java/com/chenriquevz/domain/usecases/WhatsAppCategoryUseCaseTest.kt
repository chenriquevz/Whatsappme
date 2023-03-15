package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.model.Category
import com.chenriquevz.domain.model.CategoryWithWhatsAppContacts
import com.chenriquevz.domain.model.WhatsAppContact
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
class WhatsAppCategoryUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var whatsAppCategoryUseCase: WhatsAppCategoryUseCase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun add_categories_to_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val categoryDefault = Category("default")
        val categoryOther = Category("other")
        val categories = listOf(categoryDefault, categoryOther)

        whatsAppCategoryUseCase.addContactCategory(contact, categories)

        val categoriesWithContact = whatsAppCategoryUseCase.getCategoryWithContacts().firstOrNull()

        Assert.assertEquals(CategoryWithWhatsAppContacts(categoryDefault, listOf(contact)), categoriesWithContact?.firstOrNull())
        Assert.assertEquals(CategoryWithWhatsAppContacts(categoryOther, listOf(contact)), categoriesWithContact?.getOrNull(1))

    }

    @Test
    fun remove_categories_from_contact() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val categoryDefault = Category("default")
        val categoryOther = Category("other")
        val categories = listOf(categoryDefault, categoryOther)

        whatsAppCategoryUseCase.addContactCategory(contact, categories)
        whatsAppCategoryUseCase.removeCategoryFromContact(contact, listOf(categoryOther))

        val categoriesWithContact = whatsAppCategoryUseCase.getCategoryWithContacts().firstOrNull()

        Assert.assertEquals(CategoryWithWhatsAppContacts(categoryDefault, listOf(contact)), categoriesWithContact?.firstOrNull())
        Assert.assertEquals(CategoryWithWhatsAppContacts(categoryOther, emptyList()), categoriesWithContact?.getOrNull(1))

    }

    @Test
    fun check_categories_deletion_with_previous_contacts() = runTest {

        val contact = WhatsAppContact("+5521981761379", "Henrique")
        val categoryDefault = Category("default")
        val categoryOther = Category("other")
        val categories = listOf(categoryDefault, categoryOther)

        whatsAppCategoryUseCase.addContactCategory(contact, categories)
        whatsAppCategoryUseCase.deleteCategory(categoryOther)

        val categoriesWithContact = whatsAppCategoryUseCase.getCategoryWithContacts().firstOrNull()

        Assert.assertEquals(CategoryWithWhatsAppContacts(categoryDefault, listOf(contact)), categoriesWithContact?.firstOrNull())
        Assert.assertEquals(null, categoriesWithContact?.getOrNull(1))

    }


}