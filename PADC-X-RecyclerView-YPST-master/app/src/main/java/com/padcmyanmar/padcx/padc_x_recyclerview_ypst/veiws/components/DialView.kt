package com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.R
import java.lang.Math.min
import kotlin.math.cos
import kotlin.math.sin

private enum class FanSpeed(val label : Int){
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);

    fun next() = when (this) {
        FanSpeed.OFF -> FanSpeed.LOW
        FanSpeed.LOW -> FanSpeed.MEDIUM
        FanSpeed.MEDIUM -> FanSpeed.HIGH
        FanSpeed.HIGH -> FanSpeed.OFF
    }

}


private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

private var radius = 0f
private var fanSpeed = FanSpeed.OFF

private var fanSpeedLowColor = Color.YELLOW
private var fanSpeedMediumColor = Color.GREEN
private var fanSpeedMaxColor = Color.RED

private val pointposition : PointF = PointF(0.0f,0.0f)
class DialView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize =55.0f
        typeface = Typeface.create("",Typeface.BOLD)
    }

    init {
        isClickable = true

        // 5
        context.withStyledAttributes(attrs, R.styleable.DialView) {
            fanSpeedLowColor = getColor(R.styleable.DialView_fanColor1, 0)
            fanSpeedMediumColor = getColor(R.styleable.DialView_fanColor2, 0)
            fanSpeedMaxColor = getColor(R.styleable.DialView_fanColor3, 0)
        }
    }

    // 3
    override fun performClick(): Boolean {
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()

        invalidate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(w,h)/2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        // Angles are in radians.
        pos.ordinal
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackgrouond(canvas)

        drawIndicatorCircle(canvas)

        drawTextLabel(canvas)
    }

    private fun drawBackgrouond(canvas: Canvas){

        paint.color = when (fanSpeed){
            FanSpeed.OFF -> Color.GRAY
            FanSpeed.LOW -> fanSpeedLowColor
            FanSpeed.MEDIUM -> fanSpeedMediumColor
            FanSpeed.HIGH -> fanSpeedMaxColor
        }
        canvas.drawCircle(width/2f , height/2f , radius, paint)
    }

    private fun drawIndicatorCircle(canvas: Canvas) {

        val makerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointposition.computeXYForSpeed(fanSpeed, makerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointposition.x, pointposition.y, radius / 12, paint)
    }

    private fun drawTextLabel(canvas: Canvas){

        val labelRadius = radius + RADIUS_OFFSET_LABEL

        for (i in FanSpeed.values()){
            pointposition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointposition.x, pointposition.y, paint)
        }
    }
}