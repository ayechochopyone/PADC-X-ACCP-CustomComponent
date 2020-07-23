package com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.withStyledAttributes
import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.R

class PrefixCustomEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    private var mPrefix = "+95"
    private var mPrefixColor = Color.BLACK

    private val mPrefixRect = Rect()

    init {
        context.withStyledAttributes(attrs, R.styleable.PrefixCustomEditText) {
            mPrefix = getString(R.styleable.PrefixCustomEditText_prefix) ?: mPrefix
            mPrefixColor = getColor(R.styleable.PrefixCustomEditText_prefixColor, mPrefixColor)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        paint.getTextBounds(mPrefix, 0, mPrefix.length, mPrefixRect)

        mPrefixRect.right += paint.measureText(" ").toInt()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {

        paint.color = mPrefixColor

        canvas?.drawText(
            mPrefix,
            super.getCompoundPaddingLeft().toFloat(),
            baseline.toFloat(),
            paint
        )

        super.onDraw(canvas)
    }

    override fun getCompoundPaddingLeft(): Int {
        return super.getCompoundPaddingLeft() + mPrefixRect.width()
    }


}