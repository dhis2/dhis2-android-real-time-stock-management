package com.baosystems.icrc.psm.data

import com.baosystems.icrc.psm.data.models.StockEntry
import org.dhis2.android.rtsm.ui.base.ItemWatcher

data class RowAction(
    val entry: StockEntry,
    val position: Int,
    val callback: ItemWatcher.OnQuantityValidated?
)
