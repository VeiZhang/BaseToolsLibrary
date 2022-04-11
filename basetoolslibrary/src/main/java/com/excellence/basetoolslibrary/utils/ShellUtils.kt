package com.excellence.basetoolslibrary.utils

import com.excellence.basetoolslibrary.utils.ConvertUtils.inputStream2StringBuilder

/**
 * <pre>
 * author : VeiZhang
 * blog   : https://veizhang.github.io/
 * time   : 2017/1/23
 * desc   : 命令相关工具类
 * </pre>
 */
object ShellUtils {

    /**
     * 执行命令
     *
     * @param command 字符串数组命令
     * @return 执行结果
     */
    @JvmStatic
    fun execProcessBuilderCommand(vararg command: String?): CommandResult {
        var process: Process? = null
        var msg: StringBuilder? = null
        var resultCode = -1
        try {
            process = ProcessBuilder(*command).redirectErrorStream(true).start()
            msg = StringBuilder(inputStream2StringBuilder(process.inputStream))
            resultCode = process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return CommandResult(resultCode, msg?.toString())
    }

    /**
     * 执行命令
     *
     * @param shell 字符串命令
     * @return 执行结果
     */
    @JvmStatic
    fun execRuntimeCommand(shell: String?): CommandResult {
        var process: Process? = null
        var msg: StringBuilder? = null
        var resultCode = -1
        try {
            process = Runtime.getRuntime().exec(shell)
            msg = StringBuilder(inputStream2StringBuilder(process.inputStream))
            msg.append(inputStream2StringBuilder(process.errorStream))
            resultCode = process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return CommandResult(resultCode, msg?.toString())
    }

    /**
     * 命令结果
     */
    class CommandResult @JvmOverloads constructor(var resultCode: Int = -1, var resultString: String?)
}