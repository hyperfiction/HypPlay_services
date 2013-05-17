package fr.hyperfiction;

import fr.hyperfiction.HypPlayServices;
import com.google.android.gms.games.GamesClient;
import org.haxe.nme.GameActivity;
import android.util.Log;

/**
 * ...
 * @author shoe[box]
 */

class GameClient{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public GameClient( ){

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearAllNotifications( ){
			getGameClient( ).clearAllNotifications( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void clearNotifications( int type ){
			getGameClient( ).clearNotifications( type );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void connect( ){
			getGameClient( ).connect( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void disconnect( ){
			getGameClient( ).disconnect( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void showAchievements( ) {
			GameActivity.getInstance( ).startActivityForResult( getGameClient( ).getAchievementsIntent( ), 5002 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void showLeaderboard( String sLeaderboard_id ){
			GameActivity.getInstance( ).startActivityForResult( getGameClient( ).getLeaderboardIntent( sLeaderboard_id ), 5001 );
		}


		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void showAllLeaderboards( ){
			GameActivity.getInstance( ).startActivityForResult( getGameClient( ).getAllLeaderboardsIntent( ), 5003 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getAppId( ){
			return getGameClient( ).getAppId( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getCurrentAccountName( ) {
			return getGameClient( ).getCurrentAccountName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public GamesClient getGameClient( ) {
			return HypPlayServices.getHelper( ).getGamesClient( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void submitScore( String sLeaderboard_id , int iScore ) {
			trace("submitScore ::: "+sLeaderboard_id+" || score : "+iScore);
			getGameClient( ).submitScore( sLeaderboard_id , (long) iScore );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void unlockAchievement( String id ){
			getGameClient( ).unlockAchievement( id );
		}

	// -------o protected



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
		private static String TAG = "trace";//HypFacebook";
}