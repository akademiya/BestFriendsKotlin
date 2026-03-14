package com.vadym.gvd.bestfriendskotlin.father_kido.intro

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R
import java.util.Locale

/**
 * Універсальний менеджер TTS-подкасту.
 *
 * Використання:
 *   1. Створи екземпляр у Activity/Fragment
 *   2. Передай список рядків [getTexts], RecyclerView та ImageView кнопки
 *   3. Виклич [onDestroy] у onDestroy() хоста
 */
class PodcastTtsManager(
    private val context: Context,
    private val locale: Locale = Locale("uk")
) : TextToSpeech.OnInitListener {

    interface Callback {
        /** Викликається на UI-потоці після завершення кожного елемента */
        fun onItemDone(index: Int, total: Int)
        /** Викликається на UI-потоці коли все дочитано або зупинено */
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

    init {
        tts = TextToSpeech(context, this)
    }

    // ── OnInitListener ─────────────────────────────────────────────────────────

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(locale)
            ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                    && result != TextToSpeech.LANG_NOT_SUPPORTED

            if (!ttsReady) {
                Toast.makeText(context, "TTS: мова не підтримується", Toast.LENGTH_SHORT).show()
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}

                override fun onDone(utteranceId: String?) {
                    val idx = utteranceId?.toIntOrNull() ?: return
                    val next = idx + 1
                    (context as? android.app.Activity)?.runOnUiThread {
                        if (next < texts.size && isPlaying) {
                            rv?.smoothScrollToPosition(next)
                            callback?.onItemDone(next, texts.size)
                        } else if (next >= texts.size) {
                            stop()
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

    // ── Public API ─────────────────────────────────────────────────────────────

    /**
     * Прив'язує менеджер до конкретного екрану.
     * @param texts      список рядків для озвучення
     * @param recyclerView для авто-скролу під час читання
     * @param button     ImageView play/stop кнопки
     * @param callback   опціональний зворотній зв'язок
     */
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

    /** Перемикач play/stop — прив'яжи до кнопки через setOnClickListener */
    fun toggle() {
        if (isPlaying) stop() else start()
    }

    fun start() {
        if (!ttsReady) {
            Toast.makeText(context, "TTS не готовий", Toast.LENGTH_SHORT).show()
            return
        }
        if (texts.isEmpty()) return

        isPlaying = true
        btn?.setImageResource(R.drawable.ic_close)
        tts?.stop()
        rv?.smoothScrollToPosition(0)

        texts.forEachIndexed { i, text ->
            val params = Bundle()
            val queueMode = if (i == 0) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            tts?.speak(text, queueMode, params, i.toString())
        }
    }

    fun stop() {
        isPlaying = false
        tts?.stop()
        btn?.setImageResource(R.drawable.ic_audio_podcast)
    }

    /** Викликати з onPause() або onStop() хоста */
    fun onPause() = stop()

    /** Викликати з onDestroy() хоста — обов'язково! */
    fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        rv = null
        btn = null
        callback = null
    }
}