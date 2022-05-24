package com.excellence.basetoolslibrary.utils

import android.os.SystemClock
import android.view.KeyEvent

/**
 * <pre>
 * author : VeiZhang
 * blog   : http://tiimor.cn
 * time   : 2022/1/25
 * desc   : dispatchKey控制按键速度
 * </pre>
 */
class KeyController(timeInterval: Long = DEFAULT_TIME_INTERVAL) {

    private val mTimeInterval: Long = timeInterval
    private var mTimeLast: Long = 0
    private var mTimeSpace: Long = 0

    fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            val nowTime = SystemClock.elapsedRealtime()
            val timeDelay = nowTime - mTimeLast
            if (mTimeSpace <= mTimeInterval && timeDelay <= mTimeInterval) {
                mTimeSpace += timeDelay
                return true
            }
            mTimeSpace = 0
            mTimeLast = SystemClock.elapsedRealtime()
        }
        return false
    }

    companion object {
        /**
         * 长按速度限制
         */
        private const val DEFAULT_TIME_INTERVAL: Long = 50
    }
}