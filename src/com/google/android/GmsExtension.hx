package com.google.android;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;

@:build(ShortCuts.mirrors())
class GmsExtension
{
	public function new(){}
	@JNI @JNI_CONSTRUCTOR static public function create():GmsExtension;
	@JNI public function testCallInstance(instance:GmsExtension):String;
	@JNI public static function getContext():Context;
	@JNI public static function getAnalytics():GoogleAnalytics;
}
