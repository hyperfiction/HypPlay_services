package com.google.android;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import org.haxe.extension.Extension;

public class GmsExtension extends Extension
{
	public GmsExtension()
	{
		trace("constructor");
	}

	public String testCallInstance()
	{
		trace("testCallInstance");
		return "ok";
	}

	public static Context getContext()
	{
		trace("getContext ::: " + mainContext);
		return mainContext;
	}

	public static GoogleAnalytics getAnalytics()
	{
		trace("getAnalytics ::: " + mainContext);
		GoogleAnalytics result = GoogleAnalytics.getInstance(mainContext);
		return result;
	}

	public static void trace(String s)
	{
		Log.w(TAG, s);
	}
	static String TAG = "GmsExtension";
}
