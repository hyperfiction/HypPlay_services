package fr.hyperfiction.playservices;

import fr.hyperfiction.playservices.Multiplayers;
import haxe.Json;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class PlayServices{

	static public var onStatus : String->String->Int->Void;
	static public var onLeaderboard_metas : Array<LeaderboardMetas>->Void;

	static public inline var INIT					: String = "HypPS_INIT";
	static public inline var ON_ACHIEVEMENT_UPDATED	: String = "HypPS_ON_ACHIEVEMENT_UPDATED";
	static public inline var ON_INVITATION			: String = "HypPS_ON_INVITATION";
	static public inline var ON_LEADERBOARD_METAS	: String = "HypPS_ON_LEADERBOARD_METAS";
	static public inline var ON_SCORE_SUBMITTED		: String = "HypPS_ON_SCORE_SUBMITTED";
	static public inline var SIGIN_FAILED			: String = "HypPS_SIGIN_FAILED";
	static public inline var SIGIN_SUCCESS			: String = "HypPS_SIGIN_SUCCESS";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private function new() {

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public function initialize( ) : Void {
			trace("initalize");
			_setCallback( _onCallback );
			Multiplayers.initialize( );
			_initialize( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function clearAllNotifications( ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function clearNotifications( iType : Int ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function getCurrent_account_name( ) : String{

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		/*
		#if android
		@JNI
		#end
		static public function isAvailable( ) : Bool {
			return true;
		}
		*/

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function isSigned_in( ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function beginUserInitiated_sign_in( ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function hasSignin_error( ) : Bool {
			return true;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function getSigin_error( ) : String {
			return null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function openSettings( ) : Void {
			//getSettingsIntent
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function signOut( ) : Void {

		}

	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.playservices.PlayServices","initialize")
		#end
		static private function _initialize( ) : Void{

		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		#if cpp
		@CPP("HypPlayServices","HypPlayServices_set_event_callback")
		#end
		static private function _setCallback( cb : Dynamic ) : Void{

		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _onCallback( s : String , sArg : String , iStatus : Int ) : Void{
			trace("_oncallback ::: "+s+" - "+sArg+" - "+iStatus);

			switch( s ){

				case ON_LEADERBOARD_METAS:
					onLeaderboard_metas( Json.parse( sArg ) );

				default:
					if( onStatus != null )
						onStatus( s , sArg , iStatus );

			}


		}

	// -------o misc

}

typedef LeaderboardMetas={
	public var displayName	: String;
	public var iconUri		: String;
	public var id			: String;
	public var scoreOrder	: Int;
}