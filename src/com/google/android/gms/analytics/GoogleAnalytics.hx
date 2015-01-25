package com.google.android.gms.analytics;

import android.content.Context;

import com.google.android.GmsExtension;
import com.google.android.gms.analytics.Tracker;

class GoogleAnalytics
{
	static var instance:GoogleAnalytics;
	static var native:NativeGoogleAnalytics;
	static var jniInstance:Dynamic;

	function new()
	{
		native = new NativeGoogleAnalytics();
		jniInstance = GmsExtension.getAnalytics();
		trace("jniInstance = " + (jniInstance != null));
	}

	public static function getInstance():GoogleAnalytics
	{
		if (instance == null)
			instance = new GoogleAnalytics();
		
		return instance;
	}

	public function newTracker(value:String):Tracker
	{
		var result = native.newTracker(jniInstance, value);
		var tracker = new Tracker(value, result);
		return tracker;
	}

	// @JNI public function getAppOptOut():Bool;
	// @JNI public function isDryRunEnabled():Bool;
	// @JNI public function dispatchLocalHits();
	// @JNI public function dispatchLocalHits();
	// @JNI public function getLogger():Logger;
}

@:build(ShortCuts.mirrors())
class NativeGoogleAnalytics
{
	public function new(){}

	@JNI public static function getInstance(context:Context):GoogleAnalytics;
	@JNI public function newTracker(instance:GoogleAnalytics, trackingId:String):Tracker;
}


// boolean getAppOptOut()
// boolean isDryRunEnabled()
// dispatchLocalHits()
// // Logger getLogger()
// static GoogleAnalytics getInstance(Context context)
// Tracker newTracker(int configResId)
// Tracker newTracker(String trackingId)
// void enableAutoActivityReports(Application application)
// void reportActivityStart(Activity activity)
// void reportActivityStop(Activity activity)
// void setAppOptOut(boolean optOut)
// void setDryRun(boolean dryRun)
// void setLocalDispatchPeriod(int dispatchPeriodInSeconds)
// void setLogger(Logger logger)
