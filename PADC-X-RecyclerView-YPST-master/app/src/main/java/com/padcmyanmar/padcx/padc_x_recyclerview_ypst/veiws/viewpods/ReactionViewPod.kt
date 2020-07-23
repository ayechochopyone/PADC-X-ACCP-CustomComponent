package com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.data.vos.NewsVO
import kotlinx.android.synthetic.main.view_pod_reaction.view.*

class ReactionViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mDelegate : Delegate? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }

    fun setDelegate(delegate : Delegate){
        mDelegate = delegate
    }

    private fun setUpListener(){
        tvLike.setOnClickListener {
        mDelegate?.onTapLike()
        }

        tvComment.setOnClickListener {
        mDelegate?.onTapComment()
        }

        tvShare.setOnClickListener {
        mDelegate?.onTapShare()
        }
    }

    fun bindData(data : NewsVO){
        tvLikedUser.text = data.likedUsers.count().toString()
        tvCommentedUser.text = data.comments.count().toString()
    }

    interface Delegate{
        fun onTapLike()
        fun onTapShare()
        fun onTapComment()
    }

}