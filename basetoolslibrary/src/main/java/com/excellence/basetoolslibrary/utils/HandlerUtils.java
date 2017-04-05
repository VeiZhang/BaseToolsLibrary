package com.excellence.basetoolslibrary.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : Handler相关工具类，
 *     			转载自：https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/HandlerUtils.java
 *     			在销毁时清除所有消息，需要手动执行removeCallbacksAndMessages(null);
 *				可以使用{@link com.excellence.basetoolslibrary.assist.WeakHandler}
 * </pre>
 */

public class HandlerUtils
{
	public static class HandlerHolder extends Handler
	{
		WeakReference<OnReceiveMessageListener> mListenerWeakReference;

		/**
		 * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
		 *
		 * @param listener
		 *            收到消息回调接口
		 */
		public HandlerHolder(OnReceiveMessageListener listener)
		{
			mListenerWeakReference = new WeakReference<>(listener);
		}

		@Override
		public void handleMessage(Message msg)
		{
			if (mListenerWeakReference != null && mListenerWeakReference.get() != null)
			{
				mListenerWeakReference.get().handlerMessage(msg);
			}
		}
	}

	/**
	 * 收到消息回调接口
	 */
	public interface OnReceiveMessageListener
	{
		void handlerMessage(Message msg);
	}
}
