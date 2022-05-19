package com.excellence.basetoolslibrary.utils

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/18
 *     desc   :
 * </pre>
 */
object MathUtils {

    /**
     * 求最大公约数
     *
     * @return
     */
    @JvmStatic
    fun gcd(m: Int, n: Int): Int {
        var m = m
        var n = n
        if (n == 0) {
            return 0
        }
        var r = m % n
        while (r > 0) {
            m = n
            n = r
            r = m % n
        }
        return n
    }

    /**
     * 约分
     *
     * @return
     */
    @JvmStatic
    fun fraction(m: Int, n: Int): Pair<Int, Int> {
        val r = gcd(m, n)
        return if (r == 0) {
            Pair(m, n)
        } else Pair(m / r, n / r)
    }

}