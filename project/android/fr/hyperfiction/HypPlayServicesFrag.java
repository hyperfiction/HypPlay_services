package fr.hyperfiction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import fr.hyperfiction.HypPlayServices;

import org.haxe.nme.GameActivity;

import com.google.example.games.basegameutils.GameHelper;

/**
 * ...
 * @author shoe[box]
 */

class HypPlayServicesFrag extends Fragment implements GameHelper.GameHelperListener{

	static public native void onEvent( String jsEvName , String javaArg );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public HypPlayServicesFrag( ){
			super( );
			trace("constructor");
		}

	// -------o public



	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		@Override
		public void onCreate( Bundle b ){
			trace("onCreate");
			super.onCreate( b );
			HypPlayServices.getHelper( ).setup( this , GameHelper.CLIENT_GAMES );
			onEvent( HypPlayServices.INIT , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInFailed( ){
			trace("onSignInFailed");
			onEvent( HypPlayServices.SIGIN_FAILED , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInSucceeded( ){
			trace("onSignInSucceeded");
			onEvent( HypPlayServices.SIGIN_SUCCESS , "" );
		}

		public void onActivityResult (int requestCode, int resultCode, Intent data){
			trace("onActivityResult ::: "+requestCode);
		}

	// -------o misc

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, "HypPlayServicesFrag:::"+s );
		}

		private static String TAG = "trace";//HypFacebook";
}