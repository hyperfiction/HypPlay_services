package com.google.android.gms.analytics;

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("Tracker")
class TrackerAndroid
{
	public function new(){}
	@JNI public function enableAdvertisingIdCollection(instance:Tracker, enabled:Bool);
	@JNI public function enableAutoActivityTracking(instance:Tracker, enabled:Bool);
	@JNI public function enableExceptionReporting(instance:Tracker, enabled:Bool);
	@JNI public function get(instance:Tracker, key:String):String;
	@JNI public function send(instance:Tracker, params:nativejava.util.Map<String, String>);
	@JNI public function set(instance:Tracker, key:String, value:String);
	@JNI public function setAnonymizeIp(instance:Tracker, anonymize:Bool);
	@JNI public function setAppId(instance:Tracker, appId:String);
	@JNI public function setAppInstallerId(instance:Tracker, appInstallerId:String);
	@JNI public function setAppName(instance:Tracker, appName:String);
	@JNI public function setAppVersion(instance:Tracker, appVersion:String);
	@JNI public function setClientId(instance:Tracker, clientId:String);
	@JNI public function setEncoding(instance:Tracker, encoding:String);
	@JNI public function setHostname(instance:Tracker, hostname:String);
	@JNI public function setLanguage(instance:Tracker, language:String);
	@JNI public function setLocation(instance:Tracker, location:String);
	@JNI public function setPage(instance:Tracker, page:String);
	@JNI public function setReferrer(instance:Tracker, referrer:String);
	// @JNI public function setSampleRate(instance:Tracker, sampleRate:Double);
	@JNI public function setScreenColors(instance:Tracker, screenColors:String);
	@JNI public function setScreenName(instance:Tracker, screenName:String);
	@JNI public function setScreenResolution(instance:Tracker, width:Int, height:Int);
	// @JNI public function setSessionTimeout(instance:Tracker, sessionTimeout:Long);
	@JNI public function setTitle(instance:Tracker, title:String);
	@JNI public function setUseSecure(instance:Tracker, useSecure:Bool);
	@JNI public function setViewportSize(instance:Tracker, viewportSize:String);
}
