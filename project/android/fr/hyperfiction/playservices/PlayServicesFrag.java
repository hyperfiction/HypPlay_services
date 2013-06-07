package fr.hyperfiction.playservices;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.example.games.basegameutils.GameHelper;

import fr.hyperfiction.playservices.Multiplayers;
import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.playservices.PlayServices;

import java.util.ArrayList;

import org.haxe.nme.GameActivity;


/**
 * ...
 * @author shoe[box]
 */

public class PlayServicesFrag extends Fragment{

	static public native void onEvent( String jsEvName , String javaArg );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	private static GLSurfaceView _mSurface;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public PlayServicesFrag( ){
			super( );
			trace("constructor");
			_mSurface = (GLSurfaceView) GameActivity.getInstance().getCurrentFocus();
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
			PlayServices.dispatchEvent( PlayServices.INIT , "" , 0 );
		}

		public void onActivityResult (int requestCode, int res, Intent datas ){
			trace("onActivityResult ::: "+requestCode);
			/*
			switch( requestCode ){

				case Multiplayers.ID_INVITE_INTENT:
					Multiplayers.handleInvitation_results( resultCode , datas );
					break;

				case Multiplayers.ID_INVITATIONS_INBOX:
					Multiplayers.handleInvitation_select( resultCode , datas );
					break;

			}
			*/

			PlayHelper.getInstance( ).onActivityResult( requestCode , res , datas );
		}

		private void onEvent_wrapper( final String jsEvName , final String javaArg  ) {
			_mSurface.queueEvent(new Runnable() {
				@Override
				public void run() {
					onEvent( jsEvName, javaArg );
				}
			});
		}

	// -------o misc

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, "PlayServicesFrag:::"+s );
		}

		private static String TAG = "trace";//HypFacebook";

		/**
		*
		*
		* @public
		* @return	void
		*/
		public static PlayServicesFrag getInstance( ){

			if( __instance == null )
				__instance = new PlayServicesFrag( );

			return __instance;

		}

		private static PlayServicesFrag __instance = null;
}