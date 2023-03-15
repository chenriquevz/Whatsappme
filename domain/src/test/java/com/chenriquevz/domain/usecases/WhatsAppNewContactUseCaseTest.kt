package com.chenriquevz.domain.usecases

import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.data.WhatsAppDataBase
import com.chenriquevz.domain.model.WhatsAppContact
import com.chenriquevz.domain.utils.WhatsAppCalendarFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class WhatsAppNewContactUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var whatsAppNewContactUseCase: WhatsAppNewContactUseCase

    @Inject
    lateinit var dao: WhatsAppDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun validate_new_user_insert() = runTest {

        val contact = WhatsAppContact("+5521981761379")

        whatsAppNewContactUseCase.newContact(contact)

        val contactRetrieved =
            dao.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()

        Assert.assertEquals(contact, contactRetrieved?.whatsAppContact)
        Assert.assertEquals(WhatsAppCalendarFake.timeNowSetup, contactRetrieved?.timeStamps?.firstOrNull()?.timeStamp)
    }

    @Test
    fun validate_user_update() = runTest {

        val contact = WhatsAppContact("+5521981761379")
        val contactUpdated = contact.copy(name = "Henrique")

        whatsAppNewContactUseCase.newContact(contact)
        whatsAppNewContactUseCase.updateWhatsAppContactName(contactUpdated)

        val contactRetrieved =
            dao.getWhatsAppContactWithCategoryAndHistory(contact.phoneNumber)
                .firstOrNull()

        Assert.assertEquals(contactUpdated, contactRetrieved?.whatsAppContact)
        Assert.assertEquals(WhatsAppCalendarFake.timeNowSetup, contactRetrieved?.timeStamps?.firstOrNull()?.timeStamp)
    }

}