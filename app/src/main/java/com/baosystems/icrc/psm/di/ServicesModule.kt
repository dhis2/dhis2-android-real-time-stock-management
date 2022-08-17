package com.baosystems.icrc.psm.di

import org.dhis2.android.rtsm.services.*
import org.dhis2.android.rtsm.services.rules.RuleValidationHelper
import org.dhis2.android.rtsm.services.rules.RuleValidationHelperImpl
import org.dhis2.android.rtsm.services.scheduler.BaseSchedulerProvider
import org.dhis2.android.rtsm.services.scheduler.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServicesModule {
    @Binds
    abstract fun providesSchedulerProvider(impl: SchedulerProviderImpl): BaseSchedulerProvider

    @Binds
    abstract fun provideMetadataManager(impl: MetadataManagerImpl): MetadataManager

    @Binds
    abstract fun provideUserManager(impl: UserManagerImpl): UserManager

    @Binds
    abstract fun provideStockManager(impl: StockManagerImpl): StockManager

    @Binds
    abstract fun provideProgramRuleValidationHelper(
        impl: RuleValidationHelperImpl
    ): RuleValidationHelper
}