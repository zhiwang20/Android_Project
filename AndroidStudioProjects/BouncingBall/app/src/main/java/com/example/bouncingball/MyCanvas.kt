package com.example.bouncingball

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import stanford.androidlib.graphics.GCanvas
import stanford.androidlib.graphics.GColor
import stanford.androidlib.graphics.GOval
import java.util.*

class MyCanvas(context: Context, attrs: AttributeSet)
    : GCanvas(context, attrs) {

    private val RAINDROP_SIZE = 20f
    private val RAINDROP_DY = 4f
    private val SIZE = 100f
    private var dx = 15f
    private var dy = 9f
    private var ball = GOval(0f,0f,SIZE,SIZE)
    private var ticks = 0

    override fun init() {
        val paint = Paint()
        paint.setARGB(255,254,121,209)
        ball.fillColor = paint
        add(ball)
        animate(60) {
            moveBall()
        }
    }

    fun moveBall() {
        updateRainDrops()
        ball.moveBy(dx, dy)
        if (ball.x <= 0 || ball.rightX+SIZE >= width) {
            dx = -dx
        }

        // 上下越界
        if (ball.y <= 0 || ball.bottomY+SIZE >= height) {
            dy = -dy
        }
    }

    fun makeRainDrop() {
        val x = Random().nextInt((width-RAINDROP_SIZE).toInt())
        val y = 0
        val drop = GOval(x.toFloat(),y.toFloat(),RAINDROP_SIZE,RAINDROP_SIZE)
        drop.fillColor = GColor.GRAY
        add(drop)
    }

    fun updateRainDrops() {
        ticks ++
        if(ticks == 60) {
            makeRainDrop()
            ticks = 0
        }

        for (shape in this) {
            if(shape != ball)
                shape.moveBy(0f,RAINDROP_DY)
        }

    }

}