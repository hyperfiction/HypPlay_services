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

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$ItemBuilder")
class ItemBuilder extends HitBuilders
{
	public var category(default, default):Null<String>;
	public var name(default, null):String;
	public var price(default, default):Null<Float>;
	public var quantity(default, null):Float;
	public var sku(default, null):String;
	public var transactionId(default, null):String;

	@JNI @JNI_CONSTRUCTOR static public function create():ItemBuilder;
	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:ItemBuilder):nativejava.util.Map<String, String>;

	@JNI function setCategory(instance:ItemBuilder ,value:String):ItemBuilder;
	@JNI function setName(instance:ItemBuilder ,value:String):ItemBuilder;
	@JNI function setPrice(instance:ItemBuilder ,value:Double):ItemBuilder;
	@JNI function setQuantity(instance:ItemBuilder ,value:Long):ItemBuilder;
	@JNI function setSku(instance:ItemBuilder ,value:String):ItemBuilder;
	@JNI function setTransactionId(instance:ItemBuilder ,value:String):ItemBuilder;

	public function new(transactionId:String, name:String, sku:String, quantity:Float)
	{
		super();

		this.transactionId = transactionId;
		this.name = name;
		this.sku = sku;
		this.quantity = quantity;

		nativeInstance = create();
	}

	public function build():Dynamic
	{
		setName(nativeInstance, name);
		setQuantity(nativeInstance, quantity);
		setSku(nativeInstance, sku);
		setTransactionId(nativeInstance, transactionId);

		if (price != null) setPrice(nativeInstance, price);
		if (category != null) setCategory(nativeInstance, category);

		var result = nativebuild(nativeInstance);
		return result;
	}
}

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$ScreenViewbuilder")
class ScreenViewbuilder extends HitBuilders
{
	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:ScreenViewbuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():ScreenViewbuilder;
	
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
@JNI_DEFAULT_CLASS_NAME("HitBuilders$SocialBuilder")
class SocialBuilder extends HitBuilders
{
	public var action(default, default):Null<String>;
	public var network(default, default):Null<String>;
	public var target(default, default):Null<String>;

	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:SocialBuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():SocialBuilder;
	@JNI function setAction(instance:SocialBuilder ,value:String):SocialBuilder;
	@JNI function setNetwork(instance:SocialBuilder ,value:String):SocialBuilder;
	@JNI function setTarget(instance:SocialBuilder ,value:String):SocialBuilder;

	public function new(?network:String, ?action:String, ?target:String)
	{
		super();
		nativeInstance = create();

		this.action = action;
		this.network = network;
		this.target = target; 
	}

	public function build():Dynamic
	{
		if (action !=null) setAction(nativeInstance, action);
		if (network !=null) setNetwork(nativeInstance, network);
		if (target !=null) setTarget(nativeInstance, target);

		var result = nativebuild(nativeInstance);
		return result;
	}
}

@:build(ShortCuts.mirrors())
@JNI_DEFAULT_PACKAGE("com.google.android.gms.analytics")
@JNI_DEFAULT_CLASS_NAME("HitBuilders$TimingBuilder")
class TimingBuilder extends HitBuilders
{
	public var category(default, default):Null<String>;
	public var label(default, default):Null<String>;
	public var value(default, default):Null<Float>;
	public var variable(default, default):Null<String>;
	
	@JNI("com.google.android.gms.analytics", "build") 
	function nativebuild(instance:TimingBuilder):nativejava.util.Map<String, String>;
	@JNI @JNI_CONSTRUCTOR static public function create():TimingBuilder;
	@JNI function setCategory(instance:TimingBuilder ,value:String):TimingBuilder;
	@JNI function setLabel(instance:TimingBuilder ,value:String):TimingBuilder;
	@JNI function setValue(instance:TimingBuilder ,value:Long):TimingBuilder;
	@JNI function setVariable(instance:TimingBuilder ,value:String):TimingBuilder;

	public function new()
	{
		super();
		nativeInstance = create();
	}

	public function build():Dynamic
	{
		if (category !=null) setCategory(nativeInstance, category);
		if (label !=null) setLabel(nativeInstance, label);
		if (value !=null) setValue(nativeInstance, value);
		if (variable !=null) setVariable(nativeInstance, variable);

		var result = nativebuild(nativeInstance);
		return result;
	}
}
