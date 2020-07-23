package com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.R

class EmotionalFaceView(context: Context, attrs : AttributeSet) : View(context, attrs){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var faceColor = DEFAULT_FACE_COLOR
    private var eyeColor = DEFAULT_EYE_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR

    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var size = 0

    private val mouthPath = Path()

    var happinessState = HAPPY
        set(state) {
            field = state
            invalidate()
        }

    init{
        paint.isAntiAlias = true
        setUpAttributes(attrs)
    }

    private fun setUpAttributes(attrs: AttributeSet){

        context.withStyledAttributes(attrs, R.styleable.EmotionalFaceView){
            happinessState = getString(R.styleable.EmotionalFaceView_state)?.toLongOrNull() ?: HAPPY
            faceColor = getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
            eyeColor = getColor(R.styleable.EmotionalFaceView_eyeColor, DEFAULT_EYE_COLOR)
            mouthColor = getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
            borderColor = getColor(R.styleable.EmotionalFaceView_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = getDimension(R.styleable.EmotionalFaceView_borderWidth, DEFAULT_BORDER_WIDTH)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas){

        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size/2f

        canvas.drawCircle(radius, radius, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawCircle(radius, radius, radius - borderWidth/2f , paint)

    }

    private fun drawEyes(canvas: Canvas){

        paint.color = eyeColor
        paint.style = Paint.Style.FILL

        val leftRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftRect,paint)

        val rightRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas.drawOval(rightRect, paint)
    }

    private fun drawMouth(canvas: Canvas){

        mouthPath.reset()

        mouthPath.moveTo(size * 0.22f, size * 0.7f)

        if(happinessState == HAPPY){

            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f)
            mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f)
        } else {
            mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)

        }

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(mouthPath, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = measuredWidth.coerceAtMost(measuredHeight)

        setMeasuredDimension(size, size)
    }
    companion object{
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYE_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val SAD = 1L

    }

}