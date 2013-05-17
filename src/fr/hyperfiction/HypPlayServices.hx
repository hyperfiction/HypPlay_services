package fr.hyperfiction;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class HypPlayServices{

	static public inline var INIT			: String = "HypPS_INIT";
	static public inline var SIGIN_SUCCESS	: String = "HypPS_SIGIN_SUCCESS";
	static public inline var SIGIN_FAILED	: String = "HypPS_SIGIN_FAILED";

	static public var onStatus : String->String->Void;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new() {
			trace("constructor");

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public function init(  ) : Void {
			_setCallback( _oncallback );
			_init( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.HypPlayServices","beginUserInitiatedSignIn")
		#end
		static public function beginUserInitiatedSignIn(  ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.GameClient","submitScore")
		#end
		static public function submitScore( sLeaderboard_id : String , iScore : Int ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.GameClient","showLeaderboard")
		#end
		static public function showLeaderboard( sLeaderboard_id : String ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.GameClient","showAllLeaderboards")
		#end
		static public function showAllLeaderboards( ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.GameClient","unlockAchievement")
		#end
		static public function unlockAchievement( s : String ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.GameClient","showAchievements")
		#end
		static public function showAchievements( ) : Void {

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
		static public function enableDebug_log( b : Bool , sTag : String ) : Void { }

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function getScopes( ) : String {
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
		static public function setSigningInMessage( s : String ) : Void {

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
		static public function setSigningOutMessage( s : String ) : Void {

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
		static public function setUnknownErrorMessage( s : String ) : Void {

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
		static public function isSignedIn( ) : Bool {
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
		static public function hasSignInError( ) : Bool {
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
		static public function getSignInError( ) : String {
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
		static public function signOut( ) : Void {

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
		static public function getInvitationId( ) : String {
			return null;
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		#if android
		@JNI("fr.hyperfiction.HypPlayServices","init")
		#end
		static private function _init( ) : Void{
		}

	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _oncallback( sName : String , sValue : String ) : Void{
			onStatus( sName , sValue );
		}

	// -------o cpp

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



	// -------o misc

}