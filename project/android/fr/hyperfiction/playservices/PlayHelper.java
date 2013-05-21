package fr.hyperfiction.playservices;

import android.util.Log;

import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;

import com.google.example.games.basegameutils.GameHelper;

import fr.hyperfiction.playservices.PlayServices;

import org.json.JSONObject;

/**
 * ...
 * @author shoe[box]
 */

public class PlayHelper extends GameHelper implements 	OnAchievementUpdatedListener ,
											OnLeaderboardMetadataLoadedListener ,
											OnScoreSubmittedListener{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private PlayHelper() {
			super( org.haxe.nme.GameActivity.getInstance( ) );
		}

	// -------o public

		//Achievements -----------------------------------------------------------------------------------------

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onAchievementUpdated( int statusCode, String achievementId ){
			trace("onAchievementUpdated ::: "+statusCode);
			PlayServices.onEvent( PlayServices.ON_ACHIEVEMENT_UPDATED , achievementId+"" , statusCode );
		}

		//Leaderboards -----------------------------------------------------------------------------------------

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onLeaderboardMetadataLoaded(int statusCode, LeaderboardBuffer buffer){
			int l = buffer.getCount( );
			int i = 0;

			String sRes = "[";
			Leaderboard lb;
			JSONObject obj;
			while( i < l ){
				lb = buffer.get( i );
				try{
					obj = new JSONObject( );
					obj.put("displayName",lb.getDisplayName( ));
					obj.put("iconUri",lb.getIconImageUri( ));
					obj.put("id",lb.getLeaderboardId( ));
					obj.put("scoreOrder",lb.getScoreOrder()+"");
					sRes += ( i > 0 ? "," : "" ) + obj.toString( );
				}catch( org.json.JSONException e ){
					trace( "error ::: "+e );
				}
				i++;
			}

			sRes += "]";
			PlayServices.onEvent( PlayServices.ON_LEADERBOARD_METAS , sRes , statusCode );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onScoreSubmitted(int statusCode, SubmitScoreResult result){
			PlayServices.onEvent( PlayServices.ON_SCORE_SUBMITTED , result.toString( ) , statusCode );
		}


	// -------o protected


	// -------o misc

		public static PlayHelper getInstance( ){
			trace("getInstance");
			if( __instance == null )
				__instance = new PlayHelper( );

			return __instance;

		}
		private static PlayHelper __instance;

		public static void trace( String s ){
			Log.w( TAG, s );
		}
		private static String TAG = "trace";//HypFacebook";
}