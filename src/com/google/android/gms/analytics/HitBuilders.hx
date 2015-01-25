package com.google.android.gms.analytics;

@:build(ShortCuts.mirrors())
class HitBuilders
{
	public var nativeInstance(default, null):Dynamic;
	public function new()		
	{

	}
}

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$AppViewBuilder")
class AppViewBuilder extends HitBuilders
{
	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:EventBuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():AppViewBuilder;
	
	public function new()
	{
		super();
		nativeInstance = create();
	}

	public function build():Dynamic
	{
		var result = nativebuild(nativeInstance);
		return result;
	}
}


@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$EventBuilder")
class EventBuilder extends HitBuilders
{
	public var category(default, null):String;
	public var action(default, null):String;
	public var label(default, null):String;

	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:EventBuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():EventBuilder;
	@JNI function setAction(instance:EventBuilder ,value:String):EventBuilder;
	@JNI function setCategory(instance:EventBuilder ,value:String):EventBuilder;
	@JNI function setLabel(instance:EventBuilder ,value:String):EventBuilder;

	public function new(?category:String, ?action:String, ?label:String)
	{
		super();
		nativeInstance = create();

		this.action = action;
		this.category = category;
		this.label = label; 

		setAction(nativeInstance, action);
		setCategory(nativeInstance, category);
		setLabel(nativeInstance, label);
	}

	public function build():Dynamic
	{
		var result = nativebuild(nativeInstance);
		return result;
	}
}

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$ExceptionBuilder")
class ExceptionBuilder extends HitBuilders
{
	public var fatal(default, null):Bool;
	public var description(default, null):String;

	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:EventBuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():ExceptionBuilder;
	@JNI function setDescription(instance:ExceptionBuilder ,value:String):ExceptionBuilder;
	@JNI function setFatal(instance:ExceptionBuilder ,value:Bool):ExceptionBuilder;

	public function new(description:String, ?fatal:Bool)
	{
		super();
		nativeInstance = create();
		this.description = description;
		this.fatal = fatal;

		setFatal(nativeInstance, fatal);
		setDescription(nativeInstance, description);
	}

	public function build():Dynamic
	{
		var result = nativebuild(nativeInstance);
		return result;
	}
}

