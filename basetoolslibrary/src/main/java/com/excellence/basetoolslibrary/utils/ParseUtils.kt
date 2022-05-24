package com.excellence.basetoolslibrary.utils

import android.text.TextUtils
import android.util.Log
import com.excellence.basetoolslibrary.utils.EmptyUtils.isNotEmpty
import java.util.*

/**
 * <pre>
 * author : VeiZhang
 * blog   : http://tiimor.cn
 * time   : 2021/3/1
 * desc   : 解析工具，字段中包含的标准不一样
</pre> *
 */
object ParseUtils {

    private val TAG = ParseUtils::class.java.simpleName

    /**
     * 数值/评分解析 [java.text.DecimalFormat] 不同国家的小数点可能不一样
     * 1.欧洲标准，小数点可能是 ","
     * 2.其他标准，小数点是"."
     * 3.带基准，6.5/10 -> 目前不考虑
     *
     * @param number
     * @return
     */
    fun number(number: String): Float {
        var number = number
        var rating = 0f
        if (isNotEmpty(number) && !isNA(number)) {
            try {
                /**
                 * 提取数值，然后取第一位作为个位数
                 * 1 -> 1
                 * 10 -> 1.0
                 * 100 -> 1.00
                 */
                number = number.replace("\\D+".toRegex(), "")
                if (number.length > 1) {
                    number = StringBuilder(number).insert(1, ".").toString()
                }
                rating = number.toFloat()
            } catch (e: NumberFormatException) {
                Log.e(TAG, "rating format error: $number")
            }
        }
        return rating
    }

    /**
     * 文本时间解析
     * 1.时间戳文本 -> 返回时间戳
     * 2.不统一规则的日期 -> 毫秒时间戳
     *
     * @param timeStr
     * @return
     */
    fun timestamp(timeStr: String, timePatternList: List<String?>): Long {
        var timestamp: Long = -1
        if (isNotEmpty(timeStr) && !isNA(timeStr)) {
            try {
                if (TextUtils.isDigitsOnly(timeStr)) {
                    timestamp = timeStr.toLong()
                } else {
                    for (pattern in timePatternList) {
                        timestamp = TimeUtils.string2Millisec(timeStr, pattern)
                        if (timestamp != -1L) {
                            break
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                Log.e(TAG, "date time format error: $timeStr")
            }
        }
        return timestamp
    }

    private fun isNA(naStr: String): Boolean {
        return "N/A".equals(naStr, ignoreCase = true)
    }

    /**
     * 文本时间解析出年份
     *
     * @param yearStr
     * @return
     */
    fun year(yearStr: String, timePatternList: List<String?>): String {
        var year = ""
        if (isNotEmpty(yearStr) && !isNA(yearStr)) {
            try {
                var timestamp: Long = -1
                for (pattern in timePatternList) {
                    timestamp = TimeUtils.string2Millisec(yearStr, pattern)
                    if (timestamp != -1L) {
                        break
                    }
                }
                if (timestamp > 0) {
                    /**
                     * [java.text.SimpleDateFormat.getCalendar]
                     */
                    val c = Calendar.getInstance()
                    c.timeInMillis = timestamp
                    year = c[Calendar.YEAR].toString()
                }
            } catch (ignore: Exception) {
                Log.e(TAG, "format year: $yearStr")
            }
        }
        return year
    }
}