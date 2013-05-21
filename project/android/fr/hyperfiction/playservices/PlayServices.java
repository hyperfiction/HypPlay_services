package fr.hyperfiction.playservices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.playservices.HypPlayServicesFrag;

import org.haxe.nme.GameActivity;

/**
 * ...
 * @author shoe[box]
 */

class PlayServices{

	static public native void onEvent( String jsEvName , String javaArg , int statusCode );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	public static String INIT				= "HypPS_INIT";
	public static String SIGIN_SUCCESS			= "HypPS_SIGIN_SUCCESS";
	public static String SIGIN_FAILED			= "HypPS_SIGIN_FAILED";
	public static String ON_LEADERBOARD_METAS	= "HypPS_ON_LEADERBOARD_METAS";
	public static String ON_SCORE_SUBMITTED		= "HypPS_ON_SCORE_SUBMITTED";
	public static String ON_ACHIEVEMENT_UPDATED	= "HypPS_ON_ACHIEVEMENT_UPDATED";

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
		static public void clearAllNotifications( ){
			PlayHelper.getInstance( ).getGameClient( ).clearAllNotifications( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearNotifications( int type ){
			PlayHelper.getInstance( ).getGameClient( ).clearNotifications( type );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getCurrent_account_name( ) {
			return PlayHelper.getInstance( ).getGameClient( ).getCurrentAccountName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isAvailable( ){
			return false; //TODO
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
			PlayHelper.getInstance( ).signOut( );
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