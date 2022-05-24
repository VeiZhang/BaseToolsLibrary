package com.excellence.basetoolslibrary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

object LiveDataUtils {

    /**
     * 秒时间戳
     */
    @JvmStatic
    val TIMESTAMP_LIVE_DATA = LiveDataReactiveStreams
            .fromPublisher(Flowable.interval(0, 1, TimeUnit.SECONDS)
                    .map { TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) })

    /**
     * 毫秒时间戳
     */
    @JvmStatic
    val MILL_TIMESTAMP_LIVE_DATA = LiveDataReactiveStreams
            .fromPublisher(Flowable.interval(0, 1, TimeUnit.SECONDS)
                    .map { System.currentTimeMillis() })

    /**
     * 定时器
     */
    @JvmStatic
    fun timer(delay: Long, unit: TimeUnit): LiveData<Long> {
        return LiveDataReactiveStreams.fromPublisher(Flowable.timer(delay, unit))
    }
}