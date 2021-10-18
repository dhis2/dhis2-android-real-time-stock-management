package com.baosystems.icrc.psm.viewmodels.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baosystems.icrc.psm.data.TransactionType
import com.baosystems.icrc.psm.data.models.IdentifiableModel
import com.baosystems.icrc.psm.service.MetadataManager

class ManageStockViewModelFactory(
    private val metadataManager: MetadataManager,
    private val transactionType: TransactionType,
    private val facility: IdentifiableModel,
    private val transactionDate: String,
    private val distributedTo: IdentifiableModel?
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ManageStockViewModel(
            metadataManager,
            transactionType,
            facility,
            transactionDate,
            distributedTo
        ) as T
    }
}