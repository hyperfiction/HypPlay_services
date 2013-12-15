package fr.hyperfiction.playservices;

import android.app.Dialog;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.example.games.basegameutils.GameHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.googleplayservices.HypPlayServices;

//import org.haxe.nme.HaxeObject;

/**
 * ...
 * @author shoe[box]
 */

public class PlayServices implements GameHelper.GameHelperListener{

	static public native void onEvent( String jsEvName , String javaArg , int statusCode );
	static{
		System.loadLibrary( "HypPlayServices" );
	}
	
	final public static String INIT                   = "HypPS_INIT";
	final public static String ON_ACHIEVEMENT_LIST    = "HypPS_ON_ACHIEVEMENT_LIST";
	final public static String ON_ACHIEVEMENT_UPDATED = "HypPS_ON_ACHIEVEMENT_UPDATED";
	final public static String ON_INVITATION          = "HypPS_ON_INVITATION";
	final public static String ON_LEADERBOARD_METAS   = "HypPS_ON_LEADERBOARD_METAS";
	final public static String ON_SCORE_SUBMITTED     = "HypPS_ON_SCORE_SUBMITTED";
	final public static String SIGIN_FAILED           = "HypPS_SIGIN_FAILED";
	final public static String SIGIN_SUCCESS          = "HypPS_SIGIN_SUCCESS";

	final public static int ID_SETTINGS	= 5007;
	final public static int ID_ERROR_POPUP	= 6000;

	private static GLSurfaceView _mSurface;
	private boolean _bInit = false;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private PlayServices() {
			trace("constructor");
		}

	// -------o public


		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void openSettings( ){
			trace("openSettings");
			final Intent i = HypPlayServices.playHelper.getGamesClient( ).getSettingsIntent( );
			HypPlayServices.mainActivity.runOnUiThread(
				new Runnable( ) {
					public void run() {
						HypPlayServices.mainActivity.startActivityForResult( i , ID_SETTINGS );
					}
				});
		}


		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void initialize( ){
			trace("initialize ::: ");
			getInstance( )._init( );
			onEvent( PlayServices.INIT , "" , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void connect( ){
			trace("connect");
			//HypPlayServices.playHelper.getGamesClient( ).connect( );

			HypPlayServices.mainActivity.runOnUiThread(
				new Runnable( ) {
					public void run() {
						HypPlayServices.playHelper.onStart( HypPlayServices.mainActivity );
					}
				});
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearAllNotifications( ){
			HypPlayServices.playHelper.getGamesClient( ).clearAllNotifications( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearNotifications( int type ){
			HypPlayServices.playHelper.getGamesClient( ).clearNotifications( type );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getCurrent_account_name( ) {
			return HypPlayServices.playHelper.getGamesClient( ).getCurrentAccountName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getDisplay_name( ){
			return HypPlayServices.playHelper.getGamesClient( ).getCurrentPlayer( ).getDisplayName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getUser_id( ){
			return HypPlayServices.playHelper.getGamesClient( ).getCurrentPlayerId( );
		}

		/**
		* 
		* 
		* @public
		* @return	void
		*/
		static public String getSHA1CertFingerprint( ){
			return HypPlayServices.playHelper.getSHA1CertFingerprint( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public int isAvailable( ){

			final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable( HypPlayServices.mainContext );
			if(resultCode != ConnectionResult.SUCCESS){
				HypPlayServices.mainActivity.runOnUiThread(
				new Runnable( ) {
					public void run() {
						Dialog 	dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, HypPlayServices.mainActivity, ID_ERROR_POPUP );
								dialog.setCancelable(false);
								dialog.show();
					}
				});

			}


			return resultCode;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void beginUserInitiated_sign_in( ){
			trace("beginUserInitiatedSignIn");

			HypPlayServices.mainActivity.runOnUiThread(
					new Runnable( ) {
						public void run() {
							trace("run");
							HypPlayServices.playHelper.beginUserInitiatedSignIn( );
						}
					}
				);

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isSigned_in( ){
			trace("isSignedIn");
			return HypPlayServices.playHelper.isSignedIn( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean hasSignin_error( ){
			return HypPlayServices.playHelper.hasSignInError( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getSignin_error( ){
			return HypPlayServices.playHelper.getSignInError( ).toString( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void signOut( ){
			trace("signOut");
			HypPlayServices.mainActivity.runOnUiThread(
					new Runnable( ) {
						public void run() {
							HypPlayServices.playHelper.signOut( );
						}
					}
				);

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInFailed( ){
			trace("onSignInFailed");
			dispatchEvent( PlayServices.SIGIN_FAILED , "" , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onSignInSucceeded( ){
			trace("onSignInSucceeded");
			dispatchEvent( PlayServices.SIGIN_SUCCESS , "" , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void dispatchEvent( final String jsEvName , final String javaArg , final int statusCode  ) {

			if( _mSurface == null )
				_mSurface = (GLSurfaceView) HypPlayServices.mainActivity.getCurrentFocus();
				_mSurface.queueEvent(new Runnable() {
					@Override
					public void run() {
						onEvent( jsEvName, javaArg , statusCode );
					}
				});
		}

	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		private void _init( ){
			trace("_init");

			if( _bInit )
				return;

			_bInit = true;
			trace("activity ::: "+HypPlayServices.mainActivity);
			HypPlayServices.playHelper.setup( this , GameHelper.CLIENT_GAMES | GameHelper.CLIENT_PLUS );

		}

	// -------o misc

		public static PlayServices getInstance( ){
			trace("getInstance");
			if( __instance == null )
				__instance = new PlayServices( );

			return __instance;

		}
		private static PlayServices __instance;

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, "PlayServices ::: "+s );
		}
		private static String TAG = "HypPS";
}