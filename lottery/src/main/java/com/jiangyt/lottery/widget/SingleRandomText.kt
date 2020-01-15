package com.jiangyt.lottery.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import java.util.*

/**
 * Desc: com.jiangyt.lottery.widget
 * <p>
 * @author Create by sinochem on 2020-01-10
 * <p>
 * Version: 1.0.0
 */
class SingleRandomText : TextView {

    /**
     * 第一次绘制
     */
    private var firstIn: Boolean = true
    /**
     * 需要显示的文本
     */
    private val values: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    /**
     * 字体宽度
     */
    private var f0: Float? = null
    /**
     * 基准线
     */
    private var baseline: Int? = null
    /**
     * 文字高度
     */
    private var measuredH: Int = 0
    /**
     * 画笔
     */
    private var paint: Paint = getPaint()
    private val random: Random = Random()
    /**
     * 默认索引
     */
    private var index: Int = random.nextInt(10)
    private var speed: Long = 80L

    constructor(context: Context) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        if (firstIn) {
            firstIn = false
            super.onDraw(canvas)
            //paint = getPaint()
            val fontMetricsInt: Paint.FontMetricsInt = paint.fontMetricsInt
            measuredH = measuredHeight
            baseline =
                (measuredH - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top
            val widths = FloatArray(4)
            paint.getTextWidths("9", widths)
            f0 = widths[0]
            invalidate()
        }
        drawNumber(canvas!!)
    }

    private fun drawNumber(canvas: Canvas) {
        drawText(
            canvas,
            values[index].toString(),
            0 + f0!!,
            (1 * baseline!!).toFloat(),
            paint
        )
    }

    fun startTask() {
        speed = (random.nextInt(5) + 3L) * 10
        if (handler.hasCallbacks(task)) {
            handler.removeCallbacks(task)
        }
        handler.postDelayed(task, speed)
    }

    fun stopTask() {
        if (handler != null && handler.hasCallbacks(task))
            handler.removeCallbacks(task)
    }

    private val task: Runnable = Runnable {
        runTask()
    }

    private fun runTask() {
        postDelayed(task, speed)
        index = ++index % 10
        postInvalidate()
    }

    /**
     * 绘制文本
     * @param canvas 画布
     * @param text 文本
     * @param x 横坐标
     * @param y 纵坐标
     * @param paint 画笔
     */
    private fun drawText(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        if (y >= -measuredH && y <= 2 * measuredH)
            canvas.drawText(text, x, y, paint)
        else return
    }

}