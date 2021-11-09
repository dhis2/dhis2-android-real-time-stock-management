package com.baosystems.icrc.psm.viewmodels.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.baosystems.icrc.psm.data.models.ReviewStockData
import com.baosystems.icrc.psm.data.models.StockEntry
import com.baosystems.icrc.psm.service.StockManager
import com.baosystems.icrc.psm.service.scheduler.BaseSchedulerProvider
import com.baosystems.icrc.psm.utils.Constants
import com.baosystems.icrc.psm.viewmodels.PSMViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ReviewStockViewModel(
    private val disposable: CompositeDisposable,
    private val schedulerProvider: BaseSchedulerProvider,
    private val stockManager: StockManager,
    private val data: ReviewStockData,
): PSMViewModel() {
    val transaction = data.transaction

    private var search = MutableLiveData<String>()
    private val searchRelay = PublishRelay.create<String>()

    private val populatedItems = data.entries

    private val _reviewedItems = Transformations.switchMap(search, this::performSearch)
    val reviewedItems: LiveData<List<StockEntry>>
        get() = _reviewedItems

    init {
        configureSearchRelay()
        loadPopulatedItems()
    }

    private fun loadPopulatedItems() {
        search.value = ""
    }

    // TODO: Find a way to reuse this function, as the same is being used by ManageStockMModel
    private fun configureSearchRelay() {
        disposable.add(
            searchRelay
                .debounce(Constants.SEARCH_QUERY_DEBOUNCE, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { result ->
                        Timber.d("Distinct: $result")
                        search.postValue(result)
                    },
                    {
                        // TODO: Report the error to the user
                        it.printStackTrace()
                        Timber.w(it, "Unable to fetch search results")
                    })
        )
    }

    fun removeItem(item: StockEntry) {
//        stockItems.
//        stockItems..remove(item)
        Timber.d("Stock list after deletion: %s", reviewedItems)
    }

    fun updateQuantity(item: StockEntry, value: Long) {
//        stockItems.re
    }

    fun getItemQuantity(item: StockEntry) = item.qty

    fun onSearchQueryChanged(query: String) {
        searchRelay.accept(query)
    }

    private fun performSearch(q: String?): LiveData<List<StockEntry>> {
        val result = if (q == null || q.isEmpty())
            populatedItems.toList()
        else
            populatedItems.filter { it.name.contains(q, true) }

        return MutableLiveData(result)
    }
}