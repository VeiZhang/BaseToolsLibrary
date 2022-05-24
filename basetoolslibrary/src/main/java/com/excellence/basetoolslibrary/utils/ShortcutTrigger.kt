package com.excellence.basetoolslibrary.utils

import android.view.KeyEvent
import java.util.*

/**
 * 连续的按键生成快捷键，如四位数字键快捷键 0000
 */
class ShortcutTrigger(intervalTime: Long = DEFAULT_INTERVAL) {

    private val mIntervalTime: Long = intervalTime
    private var mLastKeyTime: Long = 0
    private var mShortcut = ""
    private var mListener: OnShortcutTriggerListener? = null
    private val mShortcutList: MutableList<String> = ArrayList()

    fun inputKey(keyCode: Int) {
        val curTime = System.currentTimeMillis()
        if (keyCode < KeyEvent.KEYCODE_0 || keyCode > KeyEvent.KEYCODE_9) {
            mShortcut = ""
            mLastKeyTime = curTime
            return
        }
        val key = (keyCode - KeyEvent.KEYCODE_0).toString()
        if (curTime - mLastKeyTime >= mIntervalTime
                || !isShortcutPrefix(key)) {
            mShortcut = ""
        }
        mShortcut += key
        mLastKeyTime = curTime
        if (mListener != null) {
            for (shortcut in mShortcutList) {
                if (shortcut == mShortcut) {
                    mListener!!.onShortcutTrigger(mShortcut)
                    return
                }
            }
        }
    }

    private fun isShortcutPrefix(key: String): Boolean {
        val tmpShortcut = mShortcut + key
        for (shortcut in mShortcutList) {
            if (shortcut.startsWith(tmpShortcut)) {
                return true
            }
        }
        return false
    }

    fun setOnShortcutTriggerListener(listener: OnShortcutTriggerListener?) {
        mListener = listener
    }

    fun addShortcut(shortcut: String) {
        mShortcutList.add(shortcut)
    }

    fun addShortcut(shortcutList: List<String>) {
        mShortcutList.addAll(shortcutList)
    }

    fun clearShortcutList() {
        mShortcutList.clear()
    }

    interface OnShortcutTriggerListener {
        fun onShortcutTrigger(shortcut: String?)
    }

    companion object {
        private const val DEFAULT_INTERVAL: Long = 2000
    }
}