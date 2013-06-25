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

import fr.hyperfiction.playservices.PlayServicesFrag;
import fr.hyperfiction.playservices.PlayHelper;

import org.haxe.nme.GameActivity;
import org.haxe.nme.HaxeObject;

/**
 * ...
 * @author shoe[box]
 */

public class PlayServices implements GameHelper.GameHelperListener{

	static public native void onEvent( String jsEvName , String javaArg , int statusCode );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	final public static String INIT				= "HypPS_INIT";
	final public static String ON_ACHIEVEMENT_UPDATED	= "HypPS_ON_ACHIEVEMENT_UPDATED";
	final public static String ON_INVITATION		= "HypPS_ON_INVITATION";
	final public static String ON_LEADERBOARD_METAS	= "HypPS_ON_LEADERBOARD_METAS";
	final public static String ON_SCORE_SUBMITTED	= "HypPS_ON_SCORE_SUBMITTED";
	final public static String SIGIN_FAILED			= "HypPS_SIGIN_FAILED";
	final public static String SIGIN_SUCCESS		= "HypPS_SIGIN_SUCCESS";

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
			final Intent i = PlayHelper.getInstance( ).getGamesClient( ).getSettingsIntent( );
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						GameActivity.getInstance( ).startActivityForResult( i , ID_SETTINGS );
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
			//PlayHelper.getInstance( ).getGamesClient( ).connect( );

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayHelper.getInstance( ).onStart( GameActivity.getInstance( ) );
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
			PlayHelper.getInstance( ).getGamesClient( ).clearAllNotifications( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearNotifications( int type ){
			PlayHelper.getInstance( ).getGamesClient( ).clearNotifications( type );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getCurrent_account_name( ) {
			return PlayHelper.getInstance( ).getGamesClient( ).getCurrentAccountName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getDisplay_name( ){
			return PlayHelper.getInstance( ).getGamesClient( ).getCurrentPlayer( ).getDisplayName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getUser_id( ){
			return PlayHelper.getInstance( ).getGamesClient( ).getCurrentPlayerId( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public int isAvailable( ){

			final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable( GameActivity.getContext( ) );
			if(resultCode != ConnectionResult.SUCCESS){
				GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						Dialog 	dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, GameActivity.getInstance( ), ID_ERROR_POPUP );
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

			GameActivity.getInstance( ).runOnUiThread(
					new Runnable( ) {
						public void run() {
							trace("run");
							PlayHelper.getInstance( ).beginUserInitiatedSignIn( );
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
			return PlayHelper.getInstance( ).isSignedIn( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean hasSignin_error( ){
			return PlayHelper.getInstance( ).hasSignInError( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getSignin_error( ){
			return PlayHelper.getInstance( ).getSignInError( ).toString( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void signOut( ){
			trace("signOut");
			GameActivity.getInstance( ).runOnUiThread(
					new Runnable( ) {
						public void run() {
							PlayHelper.getInstance( ).signOut( );
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
				_mSurface = (GLSurfaceView) GameActivity.getInstance().getCurrentFocus();
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

			/*
				GameActivity.getInstance( ).runOnUiThread(
					new Runnable(){
						@Override
						public void run() {
							trace("run");
							FragmentActivity fa = (FragmentActivity) GameActivity.getInstance( );

							FragmentTransaction ft = GameActivity.getInstance( ).getSupportFragmentManager( ).beginTransaction( );
											ft.add( android.R.id.content , PlayServicesFrag.getInstance( ) );
											ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
											ft.addToBackStack( null );
											ft.commit( );
						}
					}
				);
			*/
			PlayHelper.getInstance( ).setup( this , GameHelper.CLIENT_GAMES | GameHelper.CLIENT_PLUS );

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