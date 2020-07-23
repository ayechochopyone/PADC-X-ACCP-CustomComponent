package com.padcmyanmar.padcx.padc_x_recyclerview_ypst.delegates

import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.viewpods.EmptyViewPod
import com.padcmyanmar.padcx.padc_x_recyclerview_ypst.veiws.viewpods.ReactionViewPod

/**
 * Created by Ye Pyae Sone Tun
 * on 2020-01-18.
 */

interface NewsItemDelegate : ReactionViewPod.Delegate{
    fun onTapNewsItem(newsId : Int)
}