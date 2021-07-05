package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 图片相关工具类
 * </pre>
 */

public class ImageUtils {

    /**
     *  资源转Drawable
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static Drawable resource2Drawable(Context context, @DrawableRes int resourceId) {
        return context.getResources().getDrawable(resourceId);
    }

    /**
     * 资源转Bitmap
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static Bitmap resource2Bitmap(Context context, @DrawableRes int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * bitmap转drawable
     *
     * @param context
     * @param bitmap bitmap对象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap view2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap viewCache2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 创建空白位图
     *
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createBitmap(int width, int height) {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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
    public static Bitmap createBitmap(Context context, @DrawableRes int drawable, int width, int height) {
        Drawable maskDrawable = ContextCompat.getDrawable(context, drawable);

        Bitmap maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas maskCanvas = new Canvas(maskBitmap);
        maskDrawable.setBounds(0, 0, width, height);
        maskDrawable.draw(maskCanvas);
        return maskBitmap;
    }

    /**
     * 加遮罩，多层叠加
     *
     * @param context
     * @param blankBitmap
     * @param source
     * @return
     */
    public static Bitmap addBitmapShadows(Context context, Bitmap blankBitmap, Bitmap source, int resourceId) {
        Canvas canvas = new Canvas(blankBitmap);

        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
        canvas.save();

        /**
         * 加资源遮罩
         */
        Bitmap maskBitmap = createBitmap(context, resourceId, source.getWidth(), source.getHeight());
        canvas.drawBitmap(maskBitmap, 0, 0, paint);

        canvas.restore();
        return blankBitmap;
    }

    /**
     * 加一层遮罩叠加
     *
     * @param blankBitmap
     * @return
     */
    private Bitmap addBitmapShadow(Bitmap blankBitmap, Bitmap source) {
        Canvas canvas = new Canvas(blankBitmap);
        canvas.scale(1, 1);

        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);

        return blankBitmap;
    }
}
