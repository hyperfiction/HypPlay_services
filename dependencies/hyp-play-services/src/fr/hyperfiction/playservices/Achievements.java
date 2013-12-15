package fr.hyperfiction.playservices;

import android.util.Log;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
import fr.hyperfiction.googleplayservices.HypPlayServices;
import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.playservices.PlayServices;

/**
 * ...
 * @author shoe[box]
 */

class Achievements{

	private static Achievement[] achList;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private Achievements() {

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void open( ){
			HypPlayServices.mainActivity.startActivityForResult( PlayHelper.getInstance( ).getGamesClient( ).getAchievementsIntent( ), 5002 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static void increment( String sId , int iNumSteps , boolean bSync ){
			trace("increment ::: "+sId+" - "+iNumSteps+" - "+bSync);
			if( !bSync )
				PlayHelper.getInstance( ).getGamesClient( ).incrementAchievement( sId , iNumSteps );
			else
				PlayHelper.getInstance( ).getGamesClient( ).incrementAchievementImmediate( PlayHelper.getInstance( ) , sId , iNumSteps );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static void revealAchievement( String sId , boolean bSync ){
			if( !bSync )
				PlayHelper.getInstance( ).getGamesClient( ).revealAchievement ( sId );
			else
				PlayHelper.getInstance( ).getGamesClient( ).revealAchievementImmediate( PlayHelper.getInstance( ) , sId );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void unlockAchievement( String sId , boolean bSync ){
			if( !bSync )
				PlayHelper.getInstance( ).getGamesClient( ).unlockAchievement( sId );
			else
				PlayHelper.getInstance( ).getGamesClient( ).unlockAchievementImmediate( PlayHelper.getInstance( ) , sId );
		}

		/**
		* 
		* 
		* @public
		* @return	void
		*/
		static public void loadUnlocked_list( ){
			trace("loadUnlocked_list");
			PlayHelper.getInstance( ).getGamesClient().loadAchievements( listener , true );
		}

		/**
		* 
		* 
		* @public
		* @return	void
		*/
		static public int getCurrentSteps( int id ){
			return achList[ id ].getCurrentSteps( );
		}

		/**
		* 
		* 
		* @public
		* @return	void
		*/
		static public int getState( int id ){
			return achList[ id ].getState( );		
		}

		/**
		* 
		* 
		* @public
		* @return	void
		*/
		static public int getId( String sCode ){
			int l = achList.length;
			for( int i = 0 ; i < l ; i++ ){		
				trace("ach ::: "+achList[ i ].getAchievementId( ));
				if( achList[ i ].getAchievementId( ).equals( sCode ) )
					return i;
			}

			return -1;
		}

	// -------o protected

		private static OnAchievementsLoadedListener listener = new OnAchievementsLoadedListener( ){
			@Override
			public void onAchievementsLoaded(int statusCode, AchievementBuffer buffer) {
				trace("onAchievementsLoaded");

				//PlayServices

				if (statusCode == GamesClient.STATUS_OK) {

					int count = buffer.getCount( );
					String sRes = "";

					achList = new Achievement[count];

					for( int i = 0 ; i < count ; i++ ){
						
						if( sRes != "" )
							sRes += "|"; 
							sRes += buffer.get( i ).getAchievementId( )+"="+buffer.get( i ).getState( );

						achList[ i ] = buffer.get( i );
						
					}
					trace( sRes );
					PlayServices.dispatchEvent( PlayServices.ON_ACHIEVEMENT_LIST , sRes , 0 );
				}



				//buffer.close();
			}
		};

	// -------o misc



		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, "Achievements ::: "+s );
		}
		private static String TAG = "HypPS";
}