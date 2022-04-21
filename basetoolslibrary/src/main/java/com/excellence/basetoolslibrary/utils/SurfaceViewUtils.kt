package com.excellence.basetoolslibrary.utils

import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/21
 *     desc   :
 * </pre>
 */
object SurfaceViewUtils {

    /**
     * 谨慎使用，跟平台和视频格式有关，有遇到905x平台上播放H265，清除画布导致播放卡顿
     */
    @JvmStatic
    fun clearSurfaceView(surfaceView: SurfaceView?) {
        surfaceView?.let { clearSurfaceView(it.holder) }
    }

    /**
     * 谨慎使用，跟平台和视频格式有关，有遇到905x平台上播放H265，清除画布导致播放卡顿
     */
    @JvmStatic
    fun clearSurfaceView(surfaceHolder: SurfaceHolder) {
        try {
            val canvas = surfaceHolder.lockCanvas()
            canvas?.let {
                val paint = Paint()
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                it.drawPaint(paint)
                surfaceHolder.unlockCanvasAndPost(it)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}