package com.excellence.basetoolslibrary.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

import static com.excellence.basetoolslibrary.utils.FileUtils.isFileExists;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 常见的Intent相关
 * </pre> 
 */
public class IntentUtils
{
	/**
	 * 判断Intent是否存在
	 *
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean isIntentAvailable(Context context, Intent intent)
	{
		return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0;
	}

	/**
	 * Intent跳转
	 *
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean startIntent(Context context, Intent intent)
	{
		try
		{
			if (isIntentAvailable(context, intent))
			{
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 跳转Settings
	 *
	 * @param context
	 * @return
	 */
	public static boolean startSettingIntent(Context context)
	{
		return startIntent(context, new Intent(Settings.ACTION_SETTINGS));
	}

	/**
	 * 隐式开启WiFi
	 *
	 * @param context
	 * @return
	 */
	public static boolean startWiFiIntent(Context context)
	{
		return startIntent(context, new Intent(Settings.ACTION_WIFI_SETTINGS));
	}

	/**
	 * 直接开启WiFi，防止被拦截
	 *
	 * @param context
	 * @return
	 */
	public static boolean startWiFiIntentDirectly(Context context)
	{
		Intent intent = new Intent();
		ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
		intent.setComponent(componentName);
		return startIntent(context, intent);
	}

	/**
	 * 安装应用
	 *
	 * @param context
	 * @param file
	 * @return
	 */
	public static boolean startInstallIntent(Context context, File file)
	{
		if (!isFileExists(file))
		{
			return false;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri data;
		String type = "application/vnd.android.package-archive";
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
		{
			data = Uri.fromFile(file);
		}
		else
		{
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			String authority = context.getPackageName() + ".provider";
			data = FileProvider.getUriForFile(context, authority, file);
		}
		intent.setDataAndType(data, type);
		return startIntent(context, intent);
	}

	/**
	 * 卸载应用
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean startUninstallIntent(Context context, String packageName)
	{
		Intent intent = new Intent(Intent.ACTION_DELETE);
		intent.setData(Uri.parse("package:" + packageName));
		return startIntent(context, intent);
	}

	/**
	 * 分享文本
	 *
	 * @param context
	 * @param content
	 * @return
	 */
	public static boolean startShareTextIntent(Context context, String content)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		return startIntent(context, intent);
	}

	/**
	 * 分享图片
	 *
	 * @param context
	 * @param content
	 * @return
	 */
	public static boolean startShareImageIntent(Context context, String content, Uri uri)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		intent.setType("image/*");
		return startIntent(context, intent);
	}

	/**
	 * 分享图片
	 *
	 * @param context
	 * @param content
	 * @param uris
	 * @return
	 */
	public static boolean startShareImageIntent(Context context, String content, ArrayList<Uri> uris)
	{
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		intent.setType("image/*");
		return startIntent(context, intent);
	}

	/**
	 * 跳转拨号界面
	 *
	 * @param context
	 * @param phoneNumber
	 * @return
	 */
	public static boolean startDialIntent(Context context, String phoneNumber)
	{
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
		return startIntent(context, intent);
	}

	/**
	 * 拨打电话
	 *
	 * @param context
	 * @param phoneNumber
	 * @return
	 */
	public static boolean startCallIntent(Context context, String phoneNumber)
	{
		Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
		return startIntent(context, intent);
	}

	/**
	 * 发送短信
	 *
	 * @param phoneNumber
	 * @param content
	 * @return
	 */
	public static boolean startSendSmsIntent(Context context, String phoneNumber, String content)
	{
		Uri uri = Uri.parse("smsto:" + phoneNumber);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		return startIntent(context, intent);
	}

	/**
	 * 打开相机
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static boolean startCaptureIntent(Context context, Uri uri)
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return startIntent(context, intent);
	}

}
