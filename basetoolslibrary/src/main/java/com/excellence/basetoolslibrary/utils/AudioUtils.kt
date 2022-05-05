package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.media.AudioManager
import android.os.Build

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/21
 *     desc   :
 * </pre>
 */
object AudioUtils {

    @JvmStatic
    fun getAudioManager(context: Context): AudioManager {
        return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    @JvmStatic
    fun getMaxVolume(context: Context, streamType: Int): Int {
        return getAudioManager(context).getStreamMaxVolume(streamType)
    }

    @JvmStatic
    fun getMinVolume(context: Context, streamType: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getAudioManager(context).getStreamMinVolume(streamType)
        } else {
            0
        }
    }

    @JvmStatic
    fun getVolume(context: Context, streamType: Int): Int {
        return getAudioManager(context).getStreamVolume(streamType)
    }

    @JvmStatic
    fun setVolume(context: Context, streamType: Int, volume: Int) {
        getAudioManager(context).setStreamVolume(streamType, volume, AudioManager.FLAG_PLAY_SOUND)
    }

    @JvmStatic
    fun adjustStreamVolume(context: Context, streamType: Int, direction: Int) {
        getAudioManager(context).adjustStreamVolume(streamType, direction, AudioManager.FLAG_PLAY_SOUND)
    }

    @JvmStatic
    fun adjustVolume(context: Context, direction: Int) {
        getAudioManager(context).adjustVolume(direction, AudioManager.FLAG_PLAY_SOUND)
    }
}