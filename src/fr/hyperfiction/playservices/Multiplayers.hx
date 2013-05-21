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

	static public var onEvent : String->RoomDesc->Int->Void;

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
		static public function listenFor_invitations( bListen : Bool = true ) : Void {

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
		public function  declineRoom_invitation( sInvitation_id : String ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function  dismissRoom_invitation( sInvitation_id : String ) : Bool {
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

			//
				var desc : RoomDesc = Json.parse( sArg );
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

/**
 * ...
 * @author shoe[box]
 */

class MultiplayersEvent extends Event{

	public var room : RoomDesc;

	public static inline var ROOM_CONNECTED	: String= "HypPS_ROOM_CONNECTED";
	public static inline var ROOM_CREATED	: String= "HypPS_ROOM_CREATED";
	public static inline var ROOM_JOINED	: String= "HypPS_ROOM_JOINED";
	public static inline var ROOM_LEFT		: String= "HypPS_ROOM_LEFT";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( type : String ) {
			super( type );
		}

	// -------o public



	// -------o protected



	// -------o misc

}

typedef RoomDesc={
	public var createid			: String;
	public var createtimestamp	: Int;
	public var description		: String;
	public var participants		: Array<String>;
	public var roomId			: String;
	public var status			: Int;
	public var variant			: Int;
}