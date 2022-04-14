package com.excellence.tooldemo.bean

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 数据基类
 * </pre>
 */
open class People(msg: String?) {
    var msg: String? = null

    init {
        this.msg = msg
    }
}