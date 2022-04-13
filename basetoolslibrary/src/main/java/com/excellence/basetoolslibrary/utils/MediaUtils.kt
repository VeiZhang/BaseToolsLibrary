package com.excellence.basetoolslibrary.utils

import android.media.MediaMetadataRetriever

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/9/20
 *     desc   : 多媒体：音视频相关
 * </pre>
 */
object MediaUtils {

    /**
     * 读取多媒体信息的键
     *
     * @param path
     * @param keyCode
     * @return
     */
    @JvmStatic
    fun getKey(path: String?, keyCode: Int): String? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        return mmr.extractMetadata(keyCode)
    }

    /**
     * 读取多媒体信息的专辑
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getAlbum(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_ALBUM)
    }

    /**
     * 读取多媒体信息的艺术家
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getArtist(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_ARTIST)
    }

    /**
     * 读取多媒体信息的作者
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getAuthor(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_AUTHOR)
    }

    /**
     * 读取多媒体信息的作曲家
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getComposer(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_COMPOSER)
    }

    /**
     * 读取多媒体信息的日期
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getDate(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_DATE)
    }

    /**
     * 读取多媒体信息的分类
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getGenre(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_GENRE)
    }

    /**
     * 读取多媒体信息的名称
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getTitle(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_TITLE)
    }

    /**
     * 读取多媒体信息的年份
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getYear(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_YEAR)
    }

    /**
     * 读取多媒体信息的时长
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getDuration(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_DURATION)
    }

    /**
     * 读取多媒体信息的类型
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getMimeType(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
    }

    /**
     * 读取多媒体信息是否有音频
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getHasAudio(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)
    }

    /**
     * 读取多媒体信息是否有视频
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getHasVideo(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)
    }

    /**
     * 读取多媒体信息的宽度
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getWidth(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
    }

    /**
     * 读取多媒体信息的高度
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getHeight(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
    }

    /**
     * 读取多媒体信息的码率
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getBitrate(path: String?): String? {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_BITRATE)
    }
}