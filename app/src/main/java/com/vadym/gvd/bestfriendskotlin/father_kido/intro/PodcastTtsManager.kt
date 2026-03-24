package com.vadym.gvd.bestfriendskotlin.father_kido.intro

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R
import java.util.Locale

class PodcastTtsManager(
    private val context: Context,
    private val locale: Locale = Locale.getDefault()
) : TextToSpeech.OnInitListener {

    interface Callback {
        fun onItemDone(index: Int, total: Int)
        fun onStopped()
    }

    private var tts: TextToSpeech? = null
    private var ttsReady = false
    var isPlaying = false
        private set

    private var texts: List<String> = emptyList()
    private var rv: RecyclerView? = null
    private var btn: ImageView? = null
    private var callback: Callback? = null
    private var currentIndex: Int = 0

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(locale)
            ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                    && result != TextToSpeech.LANG_NOT_SUPPORTED

            if (!ttsReady) {
                Toast.makeText(context, "TTS: мова не підтримується", Toast.LENGTH_SHORT).show()
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    val idx = utteranceId?.toIntOrNull() ?: return
                    (context as? android.app.Activity)?.runOnUiThread {
                        if (isPlaying) {
                            currentIndex = idx
                            (rv?.layoutManager as? LinearLayoutManager)
                                ?.scrollToPositionWithOffset(idx, 0)
                            callback?.onItemDone(idx, texts.size)
                        }
                    }
                }

                override fun onDone(utteranceId: String?) {
                    val idx = utteranceId?.toIntOrNull() ?: return
                    if (idx >= texts.lastIndex) {
                        (context as? android.app.Activity)?.runOnUiThread {
                            currentIndex = 0
                            stopInternal()
                            callback?.onStopped()
                        }
                    }
                }

                override fun onError(utteranceId: String?) {}
            })
        } else {
            Toast.makeText(context, "TTS ініціалізація не вдалась", Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(
        texts: List<String>,
        recyclerView: RecyclerView,
        button: ImageView,
        callback: Callback? = null
    ) {
        this.texts = texts
        this.rv = recyclerView
        this.btn = button
        this.callback = callback
    }

    fun toggle() {
        if (isPlaying) stopInternal() else speakFrom(currentIndex)
    }

    fun startFrom(index: Int) {
        currentIndex = index.coerceIn(0, texts.lastIndex)
        tts?.stop()
        isPlaying = false
        speakFrom(currentIndex)
    }

    private fun speakFrom(fromIndex: Int) {
        if (!ttsReady) {
            Toast.makeText(context, "TTS не готовий", Toast.LENGTH_SHORT).show()
            return
        }
        if (texts.isEmpty()) return

        isPlaying = true
        btn?.setImageResource(R.drawable.ic_close)

        texts.subList(fromIndex, texts.size).forEachIndexed { offset, text ->
            val realIndex = fromIndex + offset
            val queueMode = if (offset == 0) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            tts?.speak(text, queueMode, Bundle(), realIndex.toString())
        }
    }

    /** Зупиняє TTS і скидає іконку, але не чіпає currentIndex */
    private fun stopInternal() {
        isPlaying = false
        tts?.stop()
        btn?.setImageResource(R.drawable.ic_audio_podcast)
    }

    fun stop() {
        currentIndex = 0
        stopInternal()
    }

    fun onPause() = stopInternal()

    fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        rv = null
        btn = null
        callback = null
    }
}