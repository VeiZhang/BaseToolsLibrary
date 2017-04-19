package com.excellence.tooldemo.bean;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 数据基类
 * </pre>
 */

public class People
{
	private String mMsg = null;

	public People(String msg)
	{
		setMsg(msg);
	}

	public String getMsg()
	{
		return mMsg;
	}

	public void setMsg(String msg)
	{
		mMsg = msg;
	}
}
