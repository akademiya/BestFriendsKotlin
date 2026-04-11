package com.vadym.gvd.bestfriendskotlin.quiz

import android.content.Context
import com.vadym.gvd.bestfriendskotlin.R

object QuizRepository {
    fun getQuestions(quizKey: String, ctx: Context): List<QuizQuestion> =
        when (quizKey) {
            ctx.getString(R.string.pledge) -> pledgeQuestions(ctx)
            ctx.getString(R.string.hdh)    -> hdhQuestions(ctx)
            ctx.getString(R.string.sunday_service) -> sundayServiceQuestions(ctx)
            ctx.getString(R.string.anshiil)        -> anshiilQuestions(ctx)
            ctx.getString(R.string.life_service)   -> lifeServiceQuestions(ctx)
            ctx.getString(R.string.prayer_tradition) -> prayerQuestions(ctx)
            ctx.getString(R.string.salt)           -> saltQuestions(ctx)
            else -> emptyList()
        }

    // ── Обітниця сім'ї (stage 1) ─────────────────────────────────────────────

    private fun pledgeQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.pledge_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q3),
            listOf("3", "5", "7", "10"), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q4),
            listOf("12:00", ctx.getString(R.string.pledge_q4_a1), "21:00", ctx.getString(R.string.pledge_q4_a2)),  1
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q5),
            listOf(ctx.getString(R.string.pledge_q5_a1),
                ctx.getString(R.string.pledge_q5_a2),
                ctx.getString(R.string.pledge_q5_a3),
                ctx.getString(R.string.pledge_q5_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q6),
            listOf(ctx.getString(R.string.pledge_q6_a1),
                ctx.getString(R.string.pledge_q6_a2),
                ctx.getString(R.string.pledge_q6_a3),
                ctx.getString(R.string.pledge_q6_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q7),
            listOf(ctx.getString(R.string.pledge_q7_a1),
                ctx.getString(R.string.pledge_q7_a2),
                ctx.getString(R.string.pledge_q7_a3),
                ctx.getString(R.string.pledge_q7_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q8),
            listOf(ctx.getString(R.string.pledge_q8_a1),
                ctx.getString(R.string.pledge_q8_a2),
                ctx.getString(R.string.pledge_q8_a3),
                ctx.getString(R.string.pledge_q8_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q9),
            listOf(ctx.getString(R.string.pledge_q9_a1),
                ctx.getString(R.string.pledge_q9_a2),
                ctx.getString(R.string.pledge_q9_a3),
                ctx.getString(R.string.pledge_q9_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.pledge_q10),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        )
    )

    // ── ХДХ (stage 2) ────────────────────────────────────────────────────────

    private fun hdhQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.hdh_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q3),
            listOf("1", "2", "3", "4"), 3
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q4),
            listOf(ctx.getString(R.string.hdh_q4_a1),
                ctx.getString(R.string.hdh_q4_a2),
                ctx.getString(R.string.hdh_q4_a3),
                ctx.getString(R.string.hdh_q4_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q5),
            listOf("5 min", "7 min", "12 min", "30 min"), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q6),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q7),
            listOf(ctx.getString(R.string.hdh_q7_a1),
                ctx.getString(R.string.hdh_q7_a2),
                ctx.getString(R.string.hdh_q7_a3),
                ctx.getString(R.string.hdh_q7_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q8),
            listOf(ctx.getString(R.string.hdh_q8_a1),
                ctx.getString(R.string.hdh_q8_a2),
                ctx.getString(R.string.hdh_q8_a3),
                ctx.getString(R.string.hdh_q8_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q9),
            listOf(ctx.getString(R.string.hdh_q9_a1),
                ctx.getString(R.string.hdh_q9_a2),
                ctx.getString(R.string.hdh_q9_a3),
                ctx.getString(R.string.hdh_q9_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.hdh_q10),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        )
    )

    // ── Недільна служба (stage 3) ─────────────────────────────────────────────

    private fun sundayServiceQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.ss_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q3),
            listOf("8:00", "10:00", "12:00", "14:00"), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q4),
            listOf(ctx.getString(R.string.ss_q4_a1),
                ctx.getString(R.string.ss_q4_a2),
                ctx.getString(R.string.ss_q4_a3),
                ctx.getString(R.string.ss_q4_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q5),
            listOf("1", "2", "3", "4"), 3
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q6),
            listOf(ctx.getString(R.string.ss_q6_a1),
                ctx.getString(R.string.ss_q6_a2),
                ctx.getString(R.string.ss_q6_a3),
                ctx.getString(R.string.ss_q6_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q7),
            listOf(ctx.getString(R.string.ss_q7_a1),
                ctx.getString(R.string.ss_q7_a2),
                ctx.getString(R.string.ss_q7_a3),
                ctx.getString(R.string.ss_q7_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q8),
            listOf(ctx.getString(R.string.ss_q8_a1),
                ctx.getString(R.string.ss_q8_a2),
                ctx.getString(R.string.ss_q8_a3),
                ctx.getString(R.string.ss_q8_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q9),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ss_q10),
            listOf(ctx.getString(R.string.ss_q10_a1),
                ctx.getString(R.string.ss_q10_a2),
                ctx.getString(R.string.ss_q10_a3),
                ctx.getString(R.string.ss_q10_a4)), 1
        )
    )

    // ── Аншііль (stage 4) ─────────────────────────────────────────────────────

    private fun anshiilQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.anshiil_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q2),
            listOf(ctx.getString(R.string.anshiil_q2_a1),
                ctx.getString(R.string.anshiil_q2_a1),
                ctx.getString(R.string.no),
                ctx.getString(R.string.anshiil_q2_a1)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q3),
            listOf(ctx.getString(R.string.anshiil_q3_a1),
                ctx.getString(R.string.anshiil_q3_a2),
                ctx.getString(R.string.anshiil_q3_a3),
                ctx.getString(R.string.anshiil_q3_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q4),
            listOf(ctx.getString(R.string.anshiil_q4_a1),
                ctx.getString(R.string.anshiil_q4_a2),
                ctx.getString(R.string.anshiil_q4_a3),
                ctx.getString(R.string.anshiil_q4_a4)), 3
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q5),
            listOf("12", "52", "4", "1"), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q6),
            listOf(ctx.getString(R.string.anshiil_q6_a1),
                ctx.getString(R.string.anshiil_q6_a2),
                ctx.getString(R.string.anshiil_q6_a3),
                ctx.getString(R.string.anshiil_q6_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q7),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q8),
            listOf(ctx.getString(R.string.anshiil_q8_a1),
                ctx.getString(R.string.anshiil_q8_a2),
                ctx.getString(R.string.anshiil_q8_a3),
                ctx.getString(R.string.anshiil_q8_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q9),
            listOf(ctx.getString(R.string.anshiil_q9_a1),
                ctx.getString(R.string.anshiil_q9_a2),
                ctx.getString(R.string.anshiil_q9_a3),
                ctx.getString(R.string.anshiil_q9_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.anshiil_q10),
            listOf(ctx.getString(R.string.anshiil_q10_a1),
                ctx.getString(R.string.anshiil_q10_a2),
                ctx.getString(R.string.anshiil_q10_a3),
                ctx.getString(R.string.anshiil_q10_a4)), 2
        )
    )

    // ── Життя служіння (stage 5) ──────────────────────────────────────────────

    private fun lifeServiceQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.ls_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q3),
            listOf(ctx.getString(R.string.ls_q3_a1),
                ctx.getString(R.string.ls_q3_a2),
                ctx.getString(R.string.ls_q3_a3),
                ctx.getString(R.string.ls_q3_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q4),
            listOf(ctx.getString(R.string.ls_q4_a1),
                ctx.getString(R.string.ls_q4_a2),
                ctx.getString(R.string.ls_q4_a3),
                ctx.getString(R.string.ls_q4_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q5),
            listOf(ctx.getString(R.string.ls_q5_a1),
                ctx.getString(R.string.ls_q5_a2),
                ctx.getString(R.string.ls_q5_a3),
                ctx.getString(R.string.ls_q5_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q6),
            listOf("1", "3", "7", ctx.getString(R.string.ls_q6_a4)), 3
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q7),
            listOf(ctx.getString(R.string.ls_q7_a1),
                ctx.getString(R.string.ls_q7_a2),
                ctx.getString(R.string.ls_q7_a3),
                ctx.getString(R.string.ls_q7_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q8),
            listOf(ctx.getString(R.string.ls_q8_a1),
                ctx.getString(R.string.ls_q8_a2),
                ctx.getString(R.string.ls_q8_a3),
                ctx.getString(R.string.ls_q8_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q9),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.ls_q10),
            listOf(ctx.getString(R.string.ls_q10_a1),
                ctx.getString(R.string.ls_q10_a2),
                ctx.getString(R.string.ls_q10_a3),
                ctx.getString(R.string.ls_q10_a4)), 2
        )
    )

    // ── Молитва (stage 6) ─────────────────────────────────────────────────────

    private fun prayerQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.prayer_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q3),
            listOf("3 min", "7 min", "12 min", "21 min"), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q4),
            listOf(ctx.getString(R.string.prayer_q4_a1),
                ctx.getString(R.string.prayer_q4_a2),
                ctx.getString(R.string.prayer_q4_a3),
                ctx.getString(R.string.prayer_q4_a4)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q5),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q6),
            listOf(ctx.getString(R.string.prayer_q6_a1),
                ctx.getString(R.string.prayer_q6_a2),
                ctx.getString(R.string.prayer_q6_a3),
                ctx.getString(R.string.prayer_q6_a4)), 3
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q7),
            listOf(ctx.getString(R.string.prayer_q7_a1),
                ctx.getString(R.string.prayer_q7_a2),
                ctx.getString(R.string.prayer_q7_a3),
                ctx.getString(R.string.prayer_q7_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q8),
            listOf("3", "5", "7", "10"), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q9),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.prayer_q10),
            listOf(ctx.getString(R.string.prayer_q10_a1),
                ctx.getString(R.string.prayer_q10_a2),
                ctx.getString(R.string.prayer_q10_a3),
                ctx.getString(R.string.prayer_q10_a4)), 2
        )
    )

    // ── Свята сіль (stage 7) ──────────────────────────────────────────────────

    private fun saltQuestions(ctx: Context) = listOf(
        QuizQuestion(
            ctx.getString(R.string.salt_q1),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q2),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q3),
            listOf(ctx.getString(R.string.salt_q3_a1),
                ctx.getString(R.string.salt_q3_a2),
                ctx.getString(R.string.salt_q3_a3),
                ctx.getString(R.string.salt_q3_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q4),
            listOf(ctx.getString(R.string.salt_q4_a1),
                ctx.getString(R.string.salt_q4_a2),
                ctx.getString(R.string.salt_q4_a3),
                ctx.getString(R.string.salt_q4_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q5),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q6),
            listOf(ctx.getString(R.string.salt_q6_a1),
                ctx.getString(R.string.salt_q6_a2),
                ctx.getString(R.string.salt_q6_a3),
                ctx.getString(R.string.salt_q6_a4)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q7),
            listOf(ctx.getString(R.string.salt_q7_a1),
                ctx.getString(R.string.salt_q7_a2),
                ctx.getString(R.string.salt_q7_a3),
                ctx.getString(R.string.salt_q7_a4)), 2
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q8),
            listOf(ctx.getString(R.string.salt_q8_a1),
                ctx.getString(R.string.salt_q8_a2),
                ctx.getString(R.string.salt_q8_a3),
                ctx.getString(R.string.salt_q8_a4)), 0
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q9),
            listOf(ctx.getString(R.string.yes), ctx.getString(R.string.no)), 1
        ),
        QuizQuestion(
            ctx.getString(R.string.salt_q10),
            listOf(ctx.getString(R.string.salt_q10_a1),
                ctx.getString(R.string.salt_q10_a2),
                ctx.getString(R.string.salt_q10_a3),
                ctx.getString(R.string.salt_q10_a4)), 0
        )
    )
}