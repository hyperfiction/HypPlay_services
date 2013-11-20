package fr.hyperfiction.googleplayservices;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import fr.hyperfiction.playservices.PlayServices;
import fr.hyperfiction.playservices.PlayHelper;

import android.util.Log;


public class HypPlayServices extends org.haxe.extension.Extension{

	public static PlayHelper playHelper = PlayHelper.getInstance( );
	//public static AssetManager assetManager;
	//public static Handler callbackHandler;
	//public static Activity mainActivity;
	//public static Context mainContext;

	/**
	* constructor
	*
	* @param
	* @return	void
	*/
	public HypPlayServices() {
		trace("constructor");
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional data
	 * from it.
	 */
	public boolean onActivityResult (int requestCode, int resultCode, Intent data) {
		trace("onActivityResult");
		playHelper.onActivityResult( requestCode , resultCode , data );
		return true;
	}


	/**
	 * Called when the activity is starting.
	 */
	public void onCreate (Bundle savedInstanceState) {
		trace("onCreate");
		//PlayServices.initialize( );
	}


	/*
	*
	*
	* @private
	* @return	void
	*/
	public static void trace( String s ){
		Log.w( TAG, "PlayServices ::: "+s );
	}
	private static String TAG = "trace";

}