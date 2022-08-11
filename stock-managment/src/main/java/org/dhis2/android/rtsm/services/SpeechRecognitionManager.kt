package org.dhis2.android.rtsm.services

import androidx.lifecycle.LiveData
import com.baosystems.icrc.psm.data.SpeechRecognitionState

interface SpeechRecognitionManager {
    fun start()
    fun restart()
    fun stop()
    fun cleanUp()
    fun resetStatus()
    fun getStatus(): LiveData<SpeechRecognitionState>
    fun supportNegativeNumberInput(allow: Boolean)
}