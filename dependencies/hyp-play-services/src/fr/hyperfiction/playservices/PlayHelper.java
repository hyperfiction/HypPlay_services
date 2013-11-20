package fr.hyperfiction.playservices;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;

import com.google.example.games.basegameutils.GameHelper;

import fr.hyperfiction.playservices.PlayServices;
import fr.hyperfiction.playservices.Multiplayers;
import fr.hyperfiction.googleplayservices.HypPlayServices;

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
			super( HypPlayServices.mainActivity );
			trace("constructor ::: "+HypPlayServices.mainActivity);
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
			onEvent_wrapper( PlayServices.ON_ACHIEVEMENT_UPDATED , achievementId+"" , statusCode );
		}

		//Activity Results -----------------------------------------------------------------------------------------

		/**
		* Handle activity result. Call this method from your Activity's
		* onActivityResult callback. If the activity result pertains to the sign-in
		* process, processes it appropriately.
		*/
		@Override
		public void onActivityResult(int requestCode, int responseCode, Intent intent) {
			super.onActivityResult( requestCode , responseCode , intent );
			trace("onActivityResult ::: "+requestCode+" - "+responseCode+" - "+intent);

			switch( requestCode ){

				case Multiplayers.ID_INVITE_INTENT:
					Multiplayers.handleInvitation_results( responseCode , intent );
					break;

				case Multiplayers.ID_INVITATIONS_INBOX:
					Multiplayers.handleInvitation_select( responseCode , intent );
					break;

				case Multiplayers.ID_WAIT_INTENT:
					Multiplayers.handleWaitingroom_results( responseCode , intent );

			}


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
			onEvent_wrapper( PlayServices.ON_LEADERBOARD_METAS , sRes , statusCode );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onScoreSubmitted(int statusCode, SubmitScoreResult result){
			onEvent_wrapper( PlayServices.ON_SCORE_SUBMITTED , result.toString( ) , statusCode );
		}


	// -------o protected

		private void onEvent_wrapper( final String jsEvName , final String javaArg , final int statusCode ) {
			HypPlayServices.callbackHandler.post(new Runnable() {
				@Override
				public void run() {
					PlayServices.onEvent( jsEvName, javaArg, statusCode );
				}
			});
		}

	// -------o misc

		public static PlayHelper getInstance( ){

			if( __instance == null )
				__instance = new PlayHelper( );

			return __instance;

		}
		private static PlayHelper __instance;

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, "PlayHelper ::: "+s );
		}
		private static String TAG = "HypPS";
}