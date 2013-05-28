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

import java.util.ArrayList;

import org.haxe.nme.GameActivity;


/**
 * ...
 * @author shoe[box]
 */

public class HypPlayServicesFrag extends Fragment implements GameHelper.GameHelperListener{

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
		public HypPlayServicesFrag( ){
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
			PlayHelper.getInstance( ).setup( this , GameHelper.CLIENT_GAMES );
			onEvent_wrapper( PlayServices.INIT , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInFailed( ){
			trace("onSignInFailed");
			onEvent_wrapper( PlayServices.SIGIN_FAILED , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInSucceeded( ){
			trace("onSignInSucceeded");
			onEvent_wrapper( PlayServices.SIGIN_SUCCESS , "" );
		}

		public void onActivityResult (int requestCode, int resultCode, Intent datas ){
			trace("onActivityResult ::: "+requestCode);
			switch( requestCode ){

				case Multiplayers.ID_INVITE_INTENT:
					Multiplayers.handleInvitation_results( resultCode , datas );
					break;

				case Multiplayers.ID_INVITATIONS_INBOX:
					Multiplayers.handleInvitation_select( resultCode , datas );
					break;

			}
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
			Log.w( TAG, "HypPlayServicesFrag:::"+s );
		}

		private static String TAG = "trace";//HypFacebook";
}