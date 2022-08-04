package com.baosystems.icrc.psm.services.scheduler

import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

class TestSchedulerProvider @Inject constructor(private val scheduler: TestScheduler):
    BaseSchedulerProvider {
    override fun computation() = scheduler
    override fun io() = scheduler
    override fun ui() = scheduler
}