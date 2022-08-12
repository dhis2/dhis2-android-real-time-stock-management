package org.dhis2.android.rtsm.data

import org.dhis2.android.rtsm.ui.base.ItemWatcher
import org.dhis2.android.rtsm.data.models.StockEntry

data class RowAction(
    val entry: StockEntry,
    val position: Int,
    val callback: ItemWatcher.OnQuantityValidated?
)
