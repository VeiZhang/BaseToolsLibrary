package com.excellence.tooldemo.bean;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   :
 * </pre>
 */

public class ChatMessage
{
	private String mMsg = null;
	private boolean isAsker = true;

	public ChatMessage(String msg, boolean isAsker)
	{
		setMsg(msg);
		setAsker(isAsker);
	}

	public String getMsg()
	{
		return mMsg;
	}

	public void setMsg(String msg)
	{
		mMsg = msg;
	}

	public boolean isAsker()
	{
		return isAsker;
	}

	public void setAsker(boolean asker)
	{
		isAsker = asker;
	}
}
