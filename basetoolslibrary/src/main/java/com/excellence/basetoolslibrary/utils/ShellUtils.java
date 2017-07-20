package com.excellence.basetoolslibrary.utils;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/23
 *     desc   : 命令相关工具类
 * </pre>
 */

public class ShellUtils
{
	/**
	 * 执行命令
	 *
	 * @param command 字符串数组命令
	 * @return 执行结果
	 */
	public static CommandResult execProcessBuilderCommand(String... command)
	{
		Process process = null;
		StringBuilder msg = null;
		int resultCode = -1;
		try
		{
			process = new ProcessBuilder(command).redirectErrorStream(true).start();
			msg = new StringBuilder(ConvertUtils.inputStream2StringBuilder(process.getInputStream()));
			resultCode = process.waitFor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (process != null)
			{
				process.destroy();
			}
		}
		return new CommandResult(resultCode, msg == null ? null : msg.toString());
	}

	/**
	 * 执行命令
	 *
	 * @param shell 字符串命令
	 * @return 执行结果
	 */
	public static CommandResult execRuntimeCommand(String shell)
	{
		Process process = null;
		StringBuilder msg = null;
		int resultCode = -1;
		try
		{
			process = Runtime.getRuntime().exec(shell);
			msg = new StringBuilder(ConvertUtils.inputStream2StringBuilder(process.getInputStream()));
			msg.append(ConvertUtils.inputStream2StringBuilder(process.getErrorStream()));
			resultCode = process.waitFor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (process != null)
			{
				process.destroy();
			}
		}
		return new CommandResult(resultCode, msg == null ? null : msg.toString());
	}

	/**
	 * 命令结果
	 */
	public static class CommandResult
	{
		public int resultCode = -1;
		public String resultString = null;

		public CommandResult(String resultString)
		{
			this.resultString = resultString;
		}

		public CommandResult(int resultCode, String resultString)
		{
			this.resultCode = resultCode;
			this.resultString = resultString;
		}
	}
}
