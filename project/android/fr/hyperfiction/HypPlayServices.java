package fr.hyperfiction;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.appstate.AppStateClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.example.games.basegameutils.GameHelper;

import fr.hyperfiction.HypPlayServicesFrag;

import org.haxe.nme.GameActivity;

/**
 * ...
 * @author shoe[box]
 */
class HypPlayServices{

	static public native void onEvent( String jsEvName , String javaArg );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	public static String INIT		= "HypPS_INIT";
	public static String SIGIN_SUCCESS	= "HypPS_SIGIN_SUCCESS";
	public static String SIGIN_FAILED	= "HypPS_SIGIN_FAILED";

	private static String TAG = "trace";//HypFacebook";

	protected static GameHelper mHelper;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public HypPlayServices( ){
			trace("constructor");
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void init( ){
			getInstance( )._init( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public GameHelper getHelper( ){
			return mHelper;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void beginUserInitiatedSignIn( ){
			trace("beginUserInitiatedSignIn");
			GameActivity.getInstance( ).runOnUiThread(
					new Runnable( ) {
						public void run() {
							getHelper( ).beginUserInitiatedSignIn( );
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
		static public String getInvitationId( ){
			return getHelper( ).getInvitationId( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void enableDebug_log( boolean b , String tag ){
			trace("enableDebug_log ::: "+b+" - "+tag);
			getHelper( ).enableDebugLog( b , tag );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getScopes( ){
			return getHelper( ).getScopes( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void setSigningInMessage( String s ) {
			getHelper( ).setSigningInMessage( s );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void setSigningOutMessage( String s ) {
			getHelper( ).setSigningOutMessage( s );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void setUnknownErrorMessage( String s ) {
			getHelper( ).setUnknownErrorMessage( s );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isSignedIn( ) {
			return getHelper( ).isSignedIn( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean hasSignInError( ) {
			return getHelper( ).hasSignInError( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void signOut( ){
			getHelper( ).signOut( );
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
			mHelper = new GameHelper( GameActivity.getInstance( ) );
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
					@Override
					public void run() {

						trace("run");
						HypPlayServicesFrag newFragment = new HypPlayServicesFrag( );
						FragmentActivity fa = (FragmentActivity) GameActivity.getInstance( );

						FragmentTransaction ft = GameActivity.getInstance( ).getSupportFragmentManager( ).beginTransaction( );
										ft.add( android.R.id.content , newFragment );
										ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
										ft.addToBackStack( null );
										ft.commit( );

					}
				}
			);
		}

	// -------o misc

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, s );
		}

		public static HypPlayServices getInstance( ){
			trace("getInstance");
			if( __instance == null )
				__instance = new HypPlayServices( );

			return __instance;

		}
		private static HypPlayServices __instance;
}