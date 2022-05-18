package com.excellence.basetoolslibrary.utils;

import com.excellence.basetoolslibrary.assist.HanziToPinyin;

import java.util.List;
import java.util.Locale;

import static com.excellence.basetoolslibrary.utils.RegexUtils.REGEX_ZH;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/6
 *     desc   : 拼音相关工具类
 * </pre>
 */

public class PinyinUtils {

    /**
     * 中文转拼音
     * {@link Exception}:There is no Chinese collator, HanziToPinyin is disabled，
     * 出现该异常时，注意添加对应的{@link Locale}如{@link Locale#CHINESE}
     * 修改位置 {@link HanziToPinyin#getInstance()}:L406
     *
     * @param ccs 中文汉字
     * @return 拼音字符串
     */
    public static String ccs2Pinyin(String ccs) {
        if (StringUtils.isEmpty(ccs)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        List<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(ccs);
        if (EmptyUtils.isNotEmpty(tokens)) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    stringBuilder.append(token.target);
                } else {
                    stringBuilder.append(token.source);
                }
            }
        }
        return stringBuilder.toString().toLowerCase();
    }

    /**
     * 中文转拼音
     *
     * @param ccs 中文汉字
     * @return 拼音字符串
     */
    public static String ccs2Pinyin(CharSequence ccs) {
        return StringUtils.isEmpty(ccs) ? null : ccs2Pinyin(ccs.toString());
    }

    /**
     * 获取中文首字母
     *
     * @param ccs 中文汉字
     * @return 首字母
     */
    public static String getPinyinHeadChar(String ccs) {
        String letter = ccs2Pinyin(ccs);
        return StringUtils.isEmpty(letter) ? null : String.valueOf(letter.charAt(0));
    }

    /**
     * 获取中文首字母
     *
     * @param ccs 中文汉字
     * @return 首字母
     */
    public static String getPinyinHeadChar(CharSequence ccs) {
        return StringUtils.isEmpty(ccs) ? null : getPinyinHeadChar(ccs.toString());
    }

    /**
     * 获取所有中文首字母
     *
     * @param ccs 中文汉字
     * @return 所有中文首字母
     */
    public static String getPinyinHeadChars(String ccs) {
        if (StringUtils.isEmpty(ccs)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ccs.length(); i++) {
            stringBuilder.append(getPinyinHeadChar(String.valueOf(ccs.charAt(i))));
        }
        return stringBuilder.toString();
    }

    /**
     * 获取所有中文首字母
     *
     * @param ccs 中文汉字
     * @return 所有中文首字母
     */
    public static String getPinyinHeadChars(CharSequence ccs) {
        return StringUtils.isEmpty(ccs) ? null : getPinyinHeadChars(ccs.toString());
    }

    /**
     * 判断是否全是汉字
     *
     * @param ccs
     * @return {@code true}:是<br>{@code false}:否
     */
    public static boolean isAllHanzi(String ccs) {
        if (StringUtils.isEmpty(ccs)) {
            return false;
        }
        for (int i = 0; i < ccs.length(); i++) {
            if (!Character.toString(ccs.charAt(i)).matches(REGEX_ZH)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否含有中文字符
     *
     * @param text
     * @return {@code true}:是<br>{@code false}:否
     */
    public static boolean hasDoubleCharacter(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }

        char[] charArray = text.toCharArray();
        for (char ch : charArray) {
            if (ch >= 0x0391 && ch <= 0xFFE5) {
                return true;
            }
        }
        return false;
    }
}
