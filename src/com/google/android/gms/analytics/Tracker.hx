package com.google.android.gms.analytics;

// import com.google.android.gms.analytics.TrackerAndroid;

@:build(Macro.build(TrackerAndroid))
class Tracker
{
	public var nativeTracker(default, null):TrackerAndroid;
	public var id(default, null):String;
	public var nativeInstance(default, null):Dynamic;

	public function new(id:String, nativeInstance:Dynamic)
	{
		this.id = id;
		this.nativeInstance = nativeInstance;
		this.nativeTracker = new TrackerAndroid();
	}

	public function toString():String
	{
		return '[Tracker ID : $id || withInstance : $nativeInstance]';
	}
}

