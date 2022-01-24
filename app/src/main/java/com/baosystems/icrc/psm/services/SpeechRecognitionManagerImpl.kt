package com.baosystems.icrc.psm.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.MutableLiveData
import com.baosystems.icrc.psm.data.SpeechRecognitionState
import timber.log.Timber
import java.util.*


class SpeechRecognitionManagerImpl(private val context: Context) : SpeechRecognitionManager,
    RecognitionListener {
    private var speechRecognizer: SpeechRecognizer? = null
    private var readyForSpeech = false

    private val _speechRecognitionStatus: MutableLiveData<SpeechRecognitionState> =
        MutableLiveData(SpeechRecognitionState.NotInitialized)

    init {
        setup()
    }

    private fun setup() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        // Check if speech recognition is available
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            initialize()
        } else {
            Timber.e("Speech recognition is not available")

            _speechRecognitionStatus.postValue(SpeechRecognitionState.NotAvailable)
        }
    }

    private fun initialize() {
        speechRecognizer?.setRecognitionListener(this)
    }

    override fun start() {
        readyForSpeech = false

        speechRecognizer?.startListening(getIntent())
    }

    override fun restart() {
        speechRecognizer = null

        setup()
        start()
    }

    override fun stop() {
        speechRecognizer?.stopListening()
        _speechRecognitionStatus.postValue(SpeechRecognitionState.Stopped)
    }

    override fun cleanUp() {
        speechRecognizer?.destroy()
    }

    override fun getStatus() = _speechRecognitionStatus

    private fun getIntent(): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)

        return intent
    }

    override fun onReadyForSpeech(params: Bundle?) {
        readyForSpeech = true

        _speechRecognitionStatus.postValue(SpeechRecognitionState.Started)
    }

    override fun onBeginningOfSpeech() {
        Timber.d("Speech started - The user has started to speak...")
    }

    override fun onEndOfSpeech() {
        Timber.d("End of Speech - User has stopped speaking...")
    }

    override fun onError(errorCode: Int) {
        // handle buggy situation where onError(5) is always called before onReadyForSpeech()
        if (!readyForSpeech && errorCode == SpeechRecognizer.ERROR_CLIENT)
            return

        _speechRecognitionStatus.postValue(SpeechRecognitionState.Errored(errorCode))
    }

    override fun onResults(bundle: Bundle?) {
        //                micButton.setImageResource(R.drawable.ic_mic_foreground)
        val data = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        data?.let {
            if (data.size > 0)
                _speechRecognitionStatus.postValue(SpeechRecognitionState.Completed(data[0]))
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
}