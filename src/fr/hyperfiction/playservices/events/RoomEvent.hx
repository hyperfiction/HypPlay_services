package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.Multiplayers;
import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.events.PSEvent;

import flash.events.Event;

/**
 * ...
 * @author shoe[box]
 */
class RoomEvent extends PSEvent{

	public var roomDesc : RoomDesc;

	public static inline var CONNECTED		: String = "HypPS_ROOM_CONNECTED";
	public static inline var CREATED		: String = "HypPS_ROOM_CREATED";
	public static inline var JOINED		: String = "HypPS_ROOM_JOINED";
	public static inline var LEFT			: String = "HypPS_ROOM_LEFT";
	public static inline var PEER_CONNECTED	: String = "HypPS_PEER_CONNECTED";
	public static inline var PEER_JOINED	: String = "HypPS_PEER_JOINED";
	public static inline var PEER_LEFT		: String = "HypPS_PEER_LEFT";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , statusCode : Status , ?roomDesc : RoomDesc ) {
			super( t , statusCode );
			this.roomDesc = roomDesc;
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		public override function clone() : RoomEvent {
			var 	ev = new RoomEvent( type , status );
				ev.roomDesc = roomDesc;
			return ev;
		}

	// -------o protected



	// -------o misc

}