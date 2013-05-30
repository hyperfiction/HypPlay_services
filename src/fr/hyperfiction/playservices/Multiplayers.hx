package fr.hyperfiction.playservices;

import fr.hyperfiction.playservices.StatusCode;
import haxe.Json;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Multiplayers{

	static public var onDatas		: String->String->Void;
	static public var onEvent		: String->RoomDesc->Status->Void;
	static public var onInvitation	: String->InvitationDesc->Status->Void;
	static public var onStatus		: String->String->Status->Void;

	public static inline var ON_INVITATION	: String = "HypPS_ON_INVITATION";
	public static inline var ON_MESSAGE	: String = "HypPS_ON_MESSAGE";
	public static inline var INVITE_SENT	: String = "HypPS_INVITE_SENT";
	public static inline var INVITE_CANCEL	: String = "HypPS_INVITE_CANCEL";
	public static inline var ROOM_CONNECTED	: String = "HypPS_ROOM_CONNECTED";
	public static inline var ROOM_CREATED	: String = "HypPS_ROOM_CREATED";
	public static inline var ROOM_JOINED	: String = "HypPS_ROOM_JOINED";
	public static inline var ROOM_LEFT		: String = "HypPS_ROOM_LEFT";

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

		/**
		* Initialize the multiplayer client
		*
		* @public
		* @return	void
		*/
		static public function initialize( ) : Void {
			_setEvent_callback( _onMultiplayers_event );
			_setDatas_callback( _onMultiplayers_datas );
		}

		/**
		* Listen for ingame notifications
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function listenFor_invitations( ) : Void {
			trace("listenFor_invitations");
		}

		/**
		* Stop listening for ingames notifications
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function stopListen_for_invitations( ) : Void {

		}

		/**
		* Check for notifications
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function checkFor_invitations( ) : String {
			return null;
		}

		/**
		* Accept an invitation by it's ID
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function acceptInvitation( sInvitation_id : String ) : Void {

		}

		/**
		* Open the invitations INBOX
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function openInvitations_inbox( ) : Void{

		}

		/**
		* Launch a quick game
		*
		* @public
		* @param iMin : minimal opponents count ( Int )
		* @param iMin : maximal opponents count ( Int )
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function quickGame( iMin : Int , iMax : Int ) : Void {

		}

		/**
		* Invite player
		*
		* @public
		* @param iMin : minimal opponents count ( Int )
		* @param iMin : maximal opponents count ( Int )
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function invitePlayers( iMin : Int , iMax : Int ) : Void{
			trace("invitePlayers");
		}

		/**
		* Open the waiting room.
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function showWaiting_room( ) : Void {

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
		static public function sendString( sMessage : String ) : Void {

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
		static public function sendString_reliable( sMessage : String ) : Void {

		}

	// -------o protected

		/*
		*
		*
		* @private
		* @return	void
		*/
		#if cpp
		@CPP("HypPlayServices","HypPlayServices_set_event_callback_multiplayers")
		#end
		static private function _setEvent_callback( cb : Dynamic ) : Void{

		}

		/*
		*
		*
		* @private
		* @return	void
		*/
		#if cpp
		@CPP("HypPlayServices","HypPlayServices_set_datas_callback_multiplayers")
		#end
		static private function _setDatas_callback( cb : Dynamic ) : Void{

		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _onMultiplayers_event( sEvent : String , sArg : String , iCode : Int ) : Void{
			trace("_onMultiplayer_event ::: "+sEvent+" - "+iCode);
			trace("onEvent ::: "+onEvent);
			trace("onInvitation ::: "+onInvitation);
			var s : Status = StatusCode.translate( iCode );
			trace("s ::: "+s);
			switch( sEvent ){

				case ON_INVITATION:
					trace("ON_INVITATION ::: "+sArg);
					onInvitation( sEvent , Json.parse( sArg ) , s );

				case ROOM_CREATED:
					trace( "ROOM_CREATED ::: "+sArg);
					if( sArg == "" || sArg == null )
						onEvent( sEvent , null , s );
					else
						onEvent( sEvent , Json.parse( sArg ) , s );

				case ON_MESSAGE:
					onDatas( sEvent , sArg );

				default:
					trace("onEvent ::: "+sEvent+" - "+iCode);
					if( onStatus != null )
						onStatus( sEvent , sArg , s );

			}
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _onMultiplayers_datas( sDatas : String , sFrom : String ) : Void{
			trace("_onMultiplayers_datas ::: "+sDatas+" from :"+sFrom);
		}

	// -------o misc

}

typedef RoomDesc={
	public var createId			: String;
	public var creationtimestamp	: Int;
	public var description		: String;
	public var participants		: Array<String>;
	public var roomId			: String;
	public var status			: Int;
	public var variant			: Int;
}

typedef InvitationDesc={
	public var from			: InvitationFrom;
	public var sInvitation_id	: String;
	public var sTimestamp		: String;
}

typedef InvitationFrom={
	public var bConnected_to_room	: Bool;
	public var iStatus			: Int;
	public var sFrom_icon_uri	: String;
	public var sFrom_id			: String;
	public var sFrom_image_uri	: String;
	public var sFrom_name		: String;
}