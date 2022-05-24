package com.excellence.basetoolslibrary.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/19
 *     desc   :
 * </pre>
 */
object RxJavaUtils {

    /**
     * 销毁订阅
     *
     * @param disposable
     */
    @JvmStatic
    fun dispose(disposable: Disposable?) {
        disposable?.let {
            if (it is CompositeDisposable) {
                /**
                 * CompositeDisposable 使用 dispose 会影响后续加入的订阅，因此只能用clear
                 */
                it.clear()
                return
            }

            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}