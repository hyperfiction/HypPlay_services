package fr.hyperfiction.playservices;

import fr.hyperfiction.playservices.events.SigninEvent;
import fr.hyperfiction.playservices.Multiplayers;
import haxe.Json;

import nme.Lib;
import nme.events.Event;
import nme.events.EventDispatcher;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class PlayServices{

	static private inline var INIT				: String = "HypPS_INIT";
	static private inline var ON_ACHIEVEMENT_UPDATED	: String = "HypPS_ON_ACHIEVEMENT_UPDATED";
	static private inline var ON_INVITATION			: String = "HypPS_ON_INVITATION";
	static private inline var ON_LEADERBOARD_METAS	: String = "HypPS_ON_LEADERBOARD_METAS";
	static private inline var ON_SCORE_SUBMITTED		: String = "HypPS_ON_SCORE_SUBMITTED";
	static private inline var SIGIN_FAILED			: String = "HypPS_SIGIN_FAILED";
	static private inline var SIGIN_SUCCESS			: String = "HypPS_SIGIN_SUCCESS";

	static private var _listener : HaxeObject;

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

			_listener = new HaxeObject( );
			_listener.onEvent = _onEvent;
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
		static public function connect( ) : Void {

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
			return "";
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
		static public function getDisplay_name( ) : String {
			return "";
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
		static public function getUser_id( ) : String {
			return "G+USERID";
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
		static public function isAvailable( ) : Int {
			return -1;
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

				case INIT:
					Lib.current.stage.dispatchEvent( new Event( Event.INIT ));

				case SIGIN_SUCCESS:
					Lib.current.stage.dispatchEvent( new SigninEvent( SigninEvent.SUCCESS , OK ));

				case SIGIN_FAILED:
					Lib.current.stage.dispatchEvent( new SigninEvent( SigninEvent.FAILED , OK ));

				default:
					//if( onStatus != null )
					//	onStatus( s , sArg , iStatus );

			}


		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _onEvent( sType : String , sArg : String , iStatus : Int ) : Void{

			switch( sType ){

				case INIT:
					Lib.current.stage.dispatchEvent( new Event( Event.INIT ));

				case SIGIN_SUCCESS:
					Lib.current.stage.dispatchEvent( new SigninEvent( SigninEvent.SUCCESS , OK ));

				case SIGIN_FAILED:
					Lib.current.stage.dispatchEvent( new SigninEvent( SigninEvent.FAILED , OK ));


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

class HaxeObject{

	public var onEvent : String->String->Int->Void;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new() {

		}

	// -------o public



	// -------o protected



	// -------o misc

}