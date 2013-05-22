package fr.hyperfiction.playservices;

import android.app.Activity;
import android.content.Intent;
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
			PlayHelper.getInstance( ).setup( this , GameHelper.CLIENT_GAMES );
			onEvent( PlayServices.INIT , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInFailed( ){
			trace("onSignInFailed");
			onEvent( PlayServices.SIGIN_FAILED , "" );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInSucceeded( ){
			trace("onSignInSucceeded");
			onEvent( PlayServices.SIGIN_SUCCESS , "" );
			_checkFor_invitation( );
		}

		public void onActivityResult (int requestCode, int resultCode, Intent datas ){
			trace("onActivityResult ::: "+requestCode);
			switch( requestCode ){

				case Multiplayers.ID_INVITE_INTENT:
					Multiplayers.handleResults( resultCode , datas );

			}
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private void _checkFor_invitation( ){
			if( PlayHelper.getInstance( ).getInvitationId( ) != null ){
				Multiplayers.onInvitation( PlayHelper.getInstance( ).getInvitationId( ) );
			}

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