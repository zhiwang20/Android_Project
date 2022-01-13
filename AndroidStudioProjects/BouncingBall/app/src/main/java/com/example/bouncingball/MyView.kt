package com.example.bouncingball

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyView(context: Context, attrs: AttributeSet)
    : View(context, attrs) {
    // 次构造器
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    private val SIZE = 100f
    private var ballX = 0f
    private var ballY = 0f
    private var dx = 15f
    private var dy = 9f

    private val paint = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        // TODO
        paint.setARGB(255, 254, 121, 209)
        paint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        paint.textSize = 50f
        // 需要修改app下的build.gradle中的minSdkVersion 21
        //canvas.drawOval(10f,10f,300f,300f,paint)
        // RectF(左，上，右，下)，是绘制的位置信息
        canvas.drawOval(RectF(ballX,ballY,ballX+SIZE,ballY+SIZE),paint)
        canvas.drawText("Hello CS 193A",400f,400f,paint)
        // 绘制BitMap
        /*
        var restful = BitmapFactory.decodeResource(resources, R.drawable.lianhua)
        restful = Bitmap.createScaledBitmap(restful,300,300,false)
        canvas.drawBitmap(restful,20f,450f,null)
        */
    }

    // 常规移动
    /*
    fun move() {
        ballX += dx
        ballY += dy
        // 强制一个View重新绘制canvas
        postInvalidate()
    }
    */
    // 碰撞检测移动
    fun move() {
        ballX += dx
        // 左右越界
        if (ballX <= 0 || ballX+SIZE >= width) {
            dx = -dx
        }

        ballY += dy
        // 上下越界
        if (ballY <= 0 || ballY+SIZE >= height) {
            dy = -dy
        }
        // 强制一个View重新绘制canvas
        postInvalidate()
    }

}