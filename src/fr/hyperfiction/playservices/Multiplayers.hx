package fr.hyperfiction.playservices;

import haxe.Json;
import nme.events.Event;
import nme.events.EventDispatcher;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Multiplayers extends EventDispatcher{

	public static inline var ON_INVITATION	: String = "HypPS_ON_INVITATION";
	public static inline var ROOM_CONNECTED	: String = "HypPS_ROOM_CONNECTED";
	public static inline var ROOM_CREATED	: String = "HypPS_ROOM_CREATED";
	public static inline var ROOM_JOINED	: String = "HypPS_ROOM_JOINED";
	public static inline var ROOM_LEFT		: String = "HypPS_ROOM_LEFT";

	static public var onEvent : String->RoomDesc->Int->Void;
	static public var onInvitation : String->InvitationDesc->Void;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private function new() {
			super( );
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public function initialize( ) : Void {
			trace("initialize");
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
		static public function listenFor_invitations( bOn : Bool = true ) : Void {

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
		static public function acceptInvitation( sInvite_ID : String ) : Void {

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
		static public function quickGame( iMin_opponents : Int , iMax_opponents : Int , exclusiveBitMask : Int = 0 ) : Void {

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
		static public function invite( iMin_opponents : Int , iMax_opponents : Int ) : Void {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function disconnect( ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function  declineRoom_invitation( sInvite_ID : String ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function  dismissRoom_invitation( sInvite_ID : String ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function getCurrent_account_name( ) : String {
			return null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function getCurrent_player( ) : String {
			return null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function getCurrent_player_id( ) : String {
			return null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public function leaveRoom( sRoom_ID : String ) : Void {

		}

	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _initialize( ) : Void{
			trace("_initialize");
			_setCallback( _onMultiplayer_event );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _onMultiplayer_event( sEvent : String , sArg : String , iCode : Int ) : Void{
			trace("_onMultiplayer_event ::: "+sEvent);
			//
				if( sEvent == ON_INVITATION ){
					trace( sArg );
					var json : InvitationDesc = Json.parse( sArg );
					trace( json );
					trace("onInvitation ::: "+onInvitation);
					onInvitation( json.sInvitation_id , json );

					return;
				}

			//
				return;
				var desc : RoomDesc = Json.parse( sArg );
				trace( desc );
				try{
					desc = Json.parse( sArg );

				}catch( e : nme.errors.Error ){
					trace( e );
				}


			//
				onEvent( sEvent , desc , iCode );
		}

	// -------o CPP

		/*
		*
		*
		* @private
		* @return	void
		*/
		#if cpp
		@CPP("HypPlayServices","HypPlayServices_set_event_callback_multiplayers")
		#end
		static private function _setCallback( cb : Dynamic ) : Void{

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