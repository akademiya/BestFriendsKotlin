package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class StageDotsView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(ctx, attrs, defStyle) {

    private var total   = 7
    private var current = 1

    private val paintDone    = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFF1D9E75.toInt() }
    private val paintCurrent = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFF5DCAA5.toInt() }
    private val paintEmpty   = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0x1A000000 }

    private val rect = RectF()
    private val gap  = 10f   // px між сегментами — підбери під свій смак

    fun setStage(currentStage: Int, total: Int = 7) {
        this.current = currentStage.coerceIn(1, total)
        this.total   = total
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (total == 0 || width == 0 || height == 0) return

        val totalGaps   = (total - 1) * gap
        val segW        = (width - totalGaps) / total
        val radius      = height / 2f

        for (i in 1..total) {
            val left  = (i - 1) * (segW + gap)
            val right = left + segW
            rect.set(left, 0f, right, height.toFloat())

            val paint = when {
                i < current  -> paintDone
                i == current -> paintCurrent
                else         -> paintEmpty
            }
            canvas.drawRoundRect(rect, radius, radius, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = (6 * resources.displayMetrics.density).toInt()  // висота полоски 6dp
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            resolveSize(h, heightMeasureSpec)
        )
    }
}