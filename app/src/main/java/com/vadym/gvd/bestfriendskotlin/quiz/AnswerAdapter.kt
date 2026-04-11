package com.vadym.gvd.bestfriendskotlin.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R

class AnswerAdapter(
    private val answers: List<String>,
    private val onSelected: (Int) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.VH>() {

    private var selectedIndex: Int? = null
    private var revealedCorrect: Int? = null

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvAnswer: TextView = view.findViewById(R.id.tv_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_answer, parent, false))

    override fun getItemCount() = answers.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        h.tvAnswer.text = answers[pos]

        // Колір фону залежно від стану
        val bgRes = when {
            revealedCorrect != null && pos == revealedCorrect -> R.drawable.bg_answer_correct
            revealedCorrect != null && pos == selectedIndex   -> R.drawable.bg_answer_wrong
            pos == selectedIndex                              -> R.drawable.bg_answer_selected
            else                                              -> R.drawable.bg_answer_default
        }
        h.itemView.setBackgroundResource(bgRes)

        h.itemView.setOnClickListener {
            if (revealedCorrect != null) return@setOnClickListener  // вже відповіли
            selectedIndex = pos
            notifyDataSetChanged()
            onSelected(pos)
        }
    }

    fun revealAnswer(selected: Int, correct: Int) {
        selectedIndex   = selected
        revealedCorrect = correct
        notifyDataSetChanged()
    }
}