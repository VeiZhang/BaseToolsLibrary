package com.excellence.basetoolslibrary.utils;

import java.util.List;
import java.util.Locale;

import com.excellence.basetoolslibrary.assist.HanziToPinyin;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/6
 *     desc   : 拼音相关工具类
 * </pre>
 */

public class PinyinUtils
{
	/**
	 * 中文转拼音
	 * {@link Exception}:There is no Chinese collator, HanziToPinyin is disabled，
	 * 出现该异常时，注意添加对应的{@link Locale}如{@link Locale#CHINESE}
	 * 修改位置 {@link HanziToPinyin#getInstance()}:L406
	 *
	 * @param ccs 中文
	 * @return 拼音字符串
	 */
	public static String ccs2Pinyin(String ccs)
	{
		StringBuilder stringBuilder = new StringBuilder();
		List<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(ccs);
		if (EmptyUtils.isNotEmpty(tokens))
		{
			for (HanziToPinyin.Token token : tokens)
			{
				if (token.type == HanziToPinyin.Token.PINYIN)
					stringBuilder.append(token.target);
				else
					stringBuilder.append(token.source);
			}
		}
		return stringBuilder.toString().toLowerCase();
	}

	/**
	 * 获取中文首字母
	 *
	 * @param ccs 中文
	 * @return 首字母
	 */
	public static String getPinYinHeadChar(String ccs)
	{
		String letter = ccs2Pinyin(ccs);
		return StringUtils.isEmpty(letter) ? null : String.valueOf(letter.charAt(0));
	}
}
