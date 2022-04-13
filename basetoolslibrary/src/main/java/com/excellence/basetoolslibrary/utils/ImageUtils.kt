package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 图片相关工具类
 * </pre>
 */
object ImageUtils {

    /**
     * 资源转Drawable
     *
     * @param context
     * @param resourceId
     * @return
     */
    @JvmStatic
    fun resource2Drawable(context: Context, @DrawableRes resourceId: Int): Drawable {
        return context.resources.getDrawable(resourceId)
    }

    /**
     * 资源转Bitmap
     *
     * @param context
     * @param resourceId
     * @return
     */
    @JvmStatic
    fun resource2Bitmap(context: Context, @DrawableRes resourceId: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, resourceId)
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    @JvmStatic
    fun drawable2Bitmap(drawable: Drawable): Bitmap? {
        return when (drawable) {
            is BitmapDrawable -> {
                drawable.bitmap
            }
            is NinePatchDrawable -> {
                val bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                        if (drawable.getOpacity() != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight())
                drawable.draw(canvas)
                bitmap
            }
            else -> {
                null
            }
        }
    }

    /**
     * bitmap转drawable
     *
     * @param context
     * @param bitmap bitmap对象
     * @return drawable
     */
    @JvmStatic
    fun bitmap2Drawable(context: Context, bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(context.resources, bitmap)
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    @JvmStatic
    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) {
            return null
        }
        val ret = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return ret
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    @JvmStatic
    fun viewCache2Bitmap(view: View?): Bitmap? {
        if (view == null) {
            return null
        }
        view.buildDrawingCache()
        val bitmap = view.drawingCache
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    /**
     * 创建空白位图
     *
     * @param width
     * @param height
     * @return
     */
    @JvmStatic
    fun createBitmap(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    /**
     * 在位图上绘资源图片
     *
     * @param context
     * @param drawable
     * @param width
     * @param height
     * @return
     */
    @JvmStatic
    fun createBitmap(context: Context, @DrawableRes drawable: Int, width: Int, height: Int): Bitmap {
        val maskDrawable = ContextCompat.getDrawable(context, drawable)!!

        val maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val maskCanvas = Canvas(maskBitmap)
        maskDrawable.setBounds(0, 0, width, height)
        maskDrawable.draw(maskCanvas)
        return maskBitmap
    }

    /**
     * 加遮罩，多层叠加
     *
     * @param context
     * @param blankBitmap
     * @param source
     * @return
     */
    @JvmStatic
    fun addBitmapShadows(context: Context, blankBitmap: Bitmap, source: Bitmap, resourceId: Int): Bitmap {
        val canvas = Canvas(blankBitmap)

        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(source, 0f, 0f, paint)
        canvas.save()

        /**
         * 加资源遮罩
         */
        val maskBitmap = createBitmap(context, resourceId, source.width, source.height)
        canvas.drawBitmap(maskBitmap, 0f, 0f, paint)

        canvas.restore()
        return blankBitmap
    }

    /**
     * 加一层遮罩叠加
     *
     * @param blankBitmap
     * @return
     */
    @JvmStatic
    fun addBitmapShadow(blankBitmap: Bitmap, source: Bitmap): Bitmap {
        val canvas = Canvas(blankBitmap)
        canvas.scale(1f, 1f)

        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(source, 0f, 0f, paint)

        return blankBitmap
    }
}