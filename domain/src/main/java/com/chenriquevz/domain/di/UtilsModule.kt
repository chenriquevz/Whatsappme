package com.chenriquevz.domain.di

import com.chenriquevz.domain.utils.TimeHelper
import com.chenriquevz.domain.utils.WhatsAppCalendar
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {

    @Binds
    abstract fun bindTimeHelper(whatsAppCalendar: WhatsAppCalendar): TimeHelper

}