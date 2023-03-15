package com.chenriquevz.domain.di

import com.chenriquevz.domain.utils.TimeHelper
import com.chenriquevz.domain.utils.WhatsAppCalendar
import com.chenriquevz.domain.utils.WhatsAppCalendarFake
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [UtilsModule::class])
interface UtilsModuleForTesting {

    @Binds
    abstract fun bindTimeHelper(whatsAppCalendar: WhatsAppCalendarFake): TimeHelper

}