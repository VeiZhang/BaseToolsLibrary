package com.excellence.basetoolslibrary.utils;

import android.media.MediaMetadataRetriever;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/9/20
 *     desc   : 多媒体：音视频相关
 * </pre> 
 */
public class MediaUtils {

    /**
     * 读取多媒体信息的键
     *
     * @param path
     * @param keyCode
     * @return
     */
    public static String getKey(String path, int keyCode) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        return mmr.extractMetadata(keyCode);
    }

    /**
     * 读取多媒体信息的专辑
     *
     * @param path
     * @return
     */
    public static String getAlbum(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_ALBUM);
    }

    /**
     * 读取多媒体信息的艺术家
     *
     * @param path
     * @return
     */
    public static String getArtist(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }

    /**
     * 读取多媒体信息的作者
     *
     * @param path
     * @return
     */
    public static String getAuthor(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_AUTHOR);
    }

    /**
     * 读取多媒体信息的作曲家
     *
     * @param path
     * @return
     */
    public static String getComposer(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_COMPOSER);
    }

    /**
     * 读取多媒体信息的日期
     *
     * @param path
     * @return
     */
    public static String getDate(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_DATE);
    }

    /**
     * 读取多媒体信息的分类
     *
     * @param path
     * @return
     */
    public static String getGenre(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_GENRE);
    }

    /**
     * 读取多媒体信息的名称
     *
     * @param path
     * @return
     */
    public static String getTitle(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_TITLE);
    }

    /**
     * 读取多媒体信息的年份
     *
     * @param path
     * @return
     */
    public static String getYear(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_YEAR);
    }

    /**
     * 读取多媒体信息的时长
     *
     * @param path
     * @return
     */
    public static String getDuration(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_DURATION);
    }

    /**
     * 读取多媒体信息的类型
     *
     * @param path
     * @return
     */
    public static String getMimeType(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
    }

    /**
     * 读取多媒体信息是否有音频
     *
     * @param path
     * @return
     */
    public static String getHasAudio(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
    }

    /**
     * 读取多媒体信息是否有视频
     *
     * @param path
     * @return
     */
    public static String getHasVideo(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
    }

    /**
     * 读取多媒体信息的宽度
     *
     * @param path
     * @return
     */
    public static String getWidth(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
    }

    /**
     * 读取多媒体信息的高度
     *
     * @param path
     * @return
     */
    public static String getHeight(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
    }

    /**
     * 读取多媒体信息的码率
     *
     * @param path
     * @return
     */
    public static String getBitrate(String path) {
        return getKey(path, MediaMetadataRetriever.METADATA_KEY_BITRATE);
    }

}
