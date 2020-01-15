package com.jiangyt.lottery.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

/**
 * Desc: com.jiangyt.lottery.widget
 * <p>
 * @author Create by sinochem on 2020-01-09
 * <p>
 * Version: 1.0.0
 */
class RandomTextView : TextView {

    companion object {
        /**
         * 高位快
         */
        const val FIRSTF_FIRST: Int = 0
        /**
         * 高位慢
         */
        const val FIRSTF_LAST: Int = 1
        /**
         * 速度相同
         */
        const val ALL: Int = 2
        /**
         * 自定义速度
         */
        const val USER: Int = 3
    }

    //var pianyiliangType: Int
    /**
     * 滚动总行数，可设置
     */
    var maxLine: Int = 10
        set(value) {
            field = value
        }
    /**
     * 当前字符串长度
     */
    private var numLength: Int = 0
    /**
     * 当前Text
     */
    private var text: String? = null
    /**
     * 滚动速度数组
     */
    private var scrollSpeedList: IntArray? = null
    /**
     * 总滚动距离数组
     */
    private var offsetSum: IntArray? = null
    /**
     * 滚动完成判断
     */
    private var overLine: IntArray? = null

    private var paint: Paint? = null
    /**
     * 第一次绘制
     */
    private var firstIn: Boolean = true
    /**
     * 滚动中
     */
    private var scrolling: Boolean = true
    /**
     * text int 值列表
     */
    private var txtList: IntArray? = null
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

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        Log.d("JYT_RTV", " ---- draw ---- ")
        if (firstIn) {
            firstIn = false
            super.onDraw(canvas)
            paint = getPaint()
            val fontMetricsInt: Paint.FontMetricsInt = paint!!.fontMetricsInt
            measuredH = measuredHeight
            Log.d("JYT_RTV", " ---- draw ---- $measuredH")
            baseline =
                (measuredH - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top
            val widths: FloatArray = FloatArray(4)
            paint!!.getTextWidths("9999", widths)
            f0 = widths[0]
            invalidate()
        }
        drawNumber(canvas!!)
    }

    /**
     * 按照设置的偏移速度类型滚动
     * @param offsetSpeedType 偏移速度类型
     */
    fun setOffsetSpeedType(offsetSpeedType: Int) {
        // 获取文本
        this.text = getText().toString()
        offsetSum = IntArray(text!!.length) { 0 }
        overLine = IntArray(text!!.length) { 0 }
        scrollSpeedList = IntArray(text!!.length)
        when (offsetSpeedType) {
            FIRSTF_FIRST -> {
                for (i in text!!.indices) {
                    scrollSpeedList!![i] = 20 - i
                }
            }
            FIRSTF_LAST -> {
                for (i in text!!.indices) {
                    scrollSpeedList!![i] = 15 + i
                }
            }
            else -> {
                for (i in text!!.indices) {
                    scrollSpeedList!![i] = 15
                }
            }
        }
    }

    /**
     * 设置偏移数据
     * @param list 数据
     */
    fun setOffset(list: IntArray) {
        this.text = getText().toString()
        offsetSum = IntArray(list.size) { 0 }
        overLine = IntArray(list.size) { 0 }
        scrollSpeedList = list
    }

    /**
     * 将int字符串拆分成int数组
     */
    fun parseText2List(txt: String): IntArray {
        val arrayList = IntArray(txt.length)
        for (i in txt.indices) {
            val temp: String = txt.substring(i, i + 1)
            arrayList[i] = temp.toInt()
        }
        return arrayList
    }

    /**
     * 设置上方数字0-9递减
     */
    private fun setBack(c: Int, back: Int): Int {
        if (back == 0) return c
        var temp = back
        temp %= 10
        var re = c - back
        if (re < 0) re += 10
        return re
    }

    /**
     * 开始滚动
     */
    fun start() {
        this.text = getText().toString()
        numLength = text!!.length
        txtList = parseText2List(text!!)
        postDelayed(task, 17)
        scrolling = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        destory()
    }

    /**
     * 销毁
     */
    fun destory() {
        scrolling = false
        removeCallbacks(task)
    }

    private val task: Runnable = Runnable {
        if (scrolling) {
            executeTask()
        }
    }

    private fun executeTask() {
        Log.d("JYT_RTV", "offset: $scrolling")
        postDelayed(task, 20)
        for (j in 0 until numLength) {
            offsetSum!![j] -= scrollSpeedList!![j]
        }
        invalidate()
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

    private fun drawNumber(canvas: Canvas) {
        for (j in 0 until numLength) {
            for (i in 1 until maxLine) {
                if (i == maxLine - 1 && i * baseline!! + offsetSum!![j] <= baseline!!) {
                    scrollSpeedList!![j] = 0
                    overLine!![j] = 1
                    var auto = 0
                    for (k in 0 until numLength) {
                        auto += overLine!![k]
                    }
                    if (auto == numLength * 2 - 1) {
                        removeCallbacks(task)
                        // 修复停止后绘制问题
                        if (scrolling) {
                            invalidate()
                        }
                        this.scrolling = false
                    }
                }
                if (overLine!![j] == 0) {
                    drawText(
                        canvas,
                        setBack(txtList!![j], maxLine - i - 1).toString(),
                        0 + f0!! * j,
                        (i * baseline!! + offsetSum!![j]).toFloat(),
                        paint!!
                    )
                } else {
                    // 定位后画一次就好
                    if (overLine!![j] == 1) {
                        overLine!![j]++
                        drawText(
                            canvas,
                            txtList!![j].toString(),
                            0 + f0!! * j,
                            baseline!!.toFloat(),
                            paint!!
                        )
                    }
                }
            }
        }
    }
}