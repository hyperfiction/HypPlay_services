package fr.hyperfiction.playservices;

import android.util.Log;
import fr.hyperfiction.playservices.PlayHelper;
import org.haxe.nme.GameActivity;

/**
 * ...
 * @author shoe[box]
 */

class Achievements{

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
			GameActivity.getInstance( ).startActivityForResult( PlayHelper.getInstance( ).getGamesClient( ).getAchievementsIntent( ), 5002 );
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