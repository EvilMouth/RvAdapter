package com.zyhang.rvAdapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

/**
 * ProjectName:YHRvAdapter
 * Description:
 * Created by zyhang on 2017/7/15.下午5:28
 * Modify by:
 * Modify time:
 * Modify remark:
 */

internal class DividingTextView : TextView {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintColor = Color.BLACK
    private var paintSize = 3F
    private var paintPadding = 3F

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        gravity = Gravity.CENTER

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.DividingTextView)
            paintColor = ta.getColor(R.styleable.DividingTextView_dtv_lineColor, paintColor)
            paintSize = ta.getDimension(R.styleable.DividingTextView_dtv_lineSize, paintSize)
            paintPadding = ta.getDimension(R.styleable.DividingTextView_dtv_linePadding, paintPadding)
            ta.recycle()
        }

        paint.color = paintColor
        paint.strokeWidth = paintSize
    }

    override fun onDraw(canvas: Canvas) {

        val width = width.toFloat()
        val height = height.toFloat()
        val layoutWidth = layout.width
        val padding = paintPadding

        //draw startLine
        canvas.drawLine(0F, height / 2, (width - layoutWidth) / 2 - padding, height / 2, paint)

        //draw endLine
        canvas.drawLine((width - (width - layoutWidth) / 2) + padding, height / 2, width, height / 2, paint)

        super.onDraw(canvas)
    }
}