package com.baosystems.icrc.psm.utils

import com.baosystems.icrc.psm.commons.Constants.PERIOD_12H
import com.baosystems.icrc.psm.commons.Constants.PERIOD_1H
import com.baosystems.icrc.psm.commons.Constants.PERIOD_30M
import com.baosystems.icrc.psm.commons.Constants.PERIOD_6H
import com.baosystems.icrc.psm.commons.Constants.PERIOD_DAILY
import com.baosystems.icrc.psm.commons.Constants.PERIOD_MANUAL
import com.baosystems.icrc.psm.commons.Constants.PERIOD_WEEKLY
import org.hisp.dhis.android.core.settings.DataSyncPeriod
import org.hisp.dhis.android.core.settings.MetadataSyncPeriod
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

fun LocalDateTime.humanReadableDateTime(): String = this.format(DateUtils.getDateTimePattern())

fun LocalDateTime.humanReadableDate(): String = this.format(DateUtils.getDatePattern())

fun String.toDate(): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.parse(this)
}

fun MetadataSyncPeriod.toSeconds(): Int {
    return when (this) {
        MetadataSyncPeriod.EVERY_HOUR -> PERIOD_1H
        MetadataSyncPeriod.EVERY_12_HOURS -> PERIOD_12H
        MetadataSyncPeriod.EVERY_24_HOURS -> PERIOD_DAILY
        MetadataSyncPeriod.EVERY_7_DAYS -> PERIOD_WEEKLY
        MetadataSyncPeriod.MANUAL -> PERIOD_MANUAL
    }
}

fun DataSyncPeriod.toSeconds(): Int {
    return when (this) {
        DataSyncPeriod.EVERY_30_MIN -> PERIOD_30M
        DataSyncPeriod.EVERY_HOUR -> PERIOD_1H
        DataSyncPeriod.EVERY_6_HOURS -> PERIOD_6H
        DataSyncPeriod.EVERY_12_HOURS -> PERIOD_12H
        DataSyncPeriod.EVERY_24_HOURS -> PERIOD_DAILY
        DataSyncPeriod.MANUAL -> PERIOD_MANUAL
    }
}