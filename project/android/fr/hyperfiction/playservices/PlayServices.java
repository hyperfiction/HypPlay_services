package fr.hyperfiction.playservices;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.playservices.HypPlayServicesFrag;

import org.haxe.nme.GameActivity;

/**
 * ...
 * @author shoe[box]
 */

public class PlayServices{

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

	public HypPlayServicesFrag frag;

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
			PlayServices.getInstance( ).frag.getActivity( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayServices.getInstance( ).frag.startActivityForResult( i , ID_SETTINGS );
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
			trace("initialize");
			getInstance( )._init( );
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
				PlayServices.getInstance( ).frag.getActivity( ).runOnUiThread(
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

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
					@Override
					public void run() {
						trace("run");
						frag = new HypPlayServicesFrag( );
						FragmentActivity fa = (FragmentActivity) GameActivity.getInstance( );

						FragmentTransaction ft = GameActivity.getInstance( ).getSupportFragmentManager( ).beginTransaction( );
										ft.add( android.R.id.content , frag );
										ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
										ft.addToBackStack( null );
										ft.commit( );
					}
				}
			);
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
			Log.w( TAG, s );
		}
		private static String TAG = "trace";//HypFacebook";
}