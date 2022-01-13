package com.example.lunarlander

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.MotionEvent
import stanford.androidlib.graphics.*
import java.util.*

class LanderCanvas(context: Context, attrs: AttributeSet)
    : GCanvas(context, attrs) {

    // static constants，share value, not copy anywhere
    companion object {
        private const val FRAMES_PER_SECOND = 30

        private const val MAX_SAFE_LANDING_VELOCITY = 7.0f

        private const val ASTEROID_VELOCITY = -12.0f

        private const val GRAVITY_ACCELERATION = .5f

        private const val THRUST_ACCELERATION = -.3f

        private val ASTEROID_SIZE = 20f
        private val ASTEROID_DX = 12f
    }

    private lateinit var rocket: GSprite
    private lateinit var moonSurface: GSprite
    private lateinit var rocketImage: Bitmap
    private var rocketImageThrust = ArrayList<Bitmap>()




    override fun init() {
//        GSprite.setDebug(true)
        backgroundColor = GColor.BLACK


        var moonSurfaceImage = BitmapFactory.decodeResource(resources,R.drawable.moonsurface)
        moonSurfaceImage = moonSurfaceImage.scaleToWidth(this.width.toFloat())
        moonSurface = GSprite(moonSurfaceImage)
        moonSurface.bottomY = height.toFloat()
        //第二版碰撞检测
        moonSurface.collisionMarginTop = moonSurface.height / 4f
        add(moonSurface)

        rocketImage = BitmapFactory.decodeResource(resources,R.drawable.rocket)
        rocketImage = rocketImage.scaleToWidth(width/4f)
        rocket = GSprite(rocketImage)
        rocket.accelerationY = GRAVITY_ACCELERATION
        add(rocket)

        for(i in 1 until 4){
            var rocketImageThrustOne = BitmapFactory.decodeResource(resources,R.drawable.rocket+i)
            rocketImageThrustOne = rocketImageThrustOne.scaleToWidth(width/4f)
            rocketImageThrust.add(rocketImageThrustOne)
        }

        setOnTouchListener{
            _, event ->
            handleTouchEvent(event)
            true
        }

    }

    private fun handleTouchEvent(event: MotionEvent){
//        val x = event.x
//        val y = event.y
        if(event.action == MotionEvent.ACTION_DOWN) {
            rocket.accelerationY = THRUST_ACCELERATION
            rocket.bitmaps = rocketImageThrust
            rocket.framesPerBitmap = FRAMES_PER_SECOND /3
        } else if (event.action == MotionEvent.ACTION_UP){
            rocket.accelerationY = GRAVITY_ACCELERATION
            rocket.bitmap = rocketImage
        }
    }


    private var frame = 0
    private var score = 0
    private val scoreLabel = GLabel()
    private fun tick(){
        rocket.update()
        updateAsteroid()


        if(++frame % 30 == 0) {
            ++score
            showScore(score.toString())
        }
        doCollision()
    }

    private fun showScore(str: String){
        scoreLabel.text = "Time：$str s"
        scoreLabel.color = GColor.RED
        scoreLabel.fontSize = 50f
        scoreLabel.x = width/2f
        scoreLabel.y = height/6f
        add(scoreLabel)
    }

    private fun doCollision(){
        if (rocket.collidesWith(moonSurface)){
            if(rocket.velocityY <= MAX_SAFE_LANDING_VELOCITY){
                showMessage("SAFE LANDING You Win!",width/6f,height/2f)
            } else {
                // you lose
                showMessage("TOO FAST LANDING You Lose!",width/6f,height/2f)
            }

            rocket.velocityY = 0f
            rocket.accelerationY = 0f
            animationStop()
        }

    }

    private fun showMessage(str: String, x: Float, y: Float){
        val message = GLabel(str,x,y)
        message.color = GColor.RED
        message.fontSize = 50f
        add(message)
    }


    private var ticks = 0
    fun startGame() {
        animate(FRAMES_PER_SECOND){
            tick()
        }
    }

    fun stopGame() {
        animationStop()
    }

    private fun makeAsteroid() {
        val x = width
        val y = Random().nextInt((height-ASTEROID_SIZE).toInt())
        val asteroidGOval = GOval(x.toFloat(),y.toFloat(),ASTEROID_SIZE,ASTEROID_SIZE)
        asteroidGOval.fillColor = GColor.GRAY
        add(asteroidGOval)
    }

    private fun updateAsteroid() {
        ticks++
        if (ticks == 30){
            makeAsteroid()
            ticks = 0
        }

        for (shape in this) {
            if (shape != rocket && shape != moonSurface && shape != rocketImageThrust && shape != scoreLabel) {
                shape.moveBy(-ASTEROID_DX, 0f)
                if (shape.leftX < 0) {
                    remove(shape)
                }
            }
        }

    }



}