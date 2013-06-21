package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.events.PSEvent;

import nme.events.Event;

/**
 * ...
 * @author shoe[box]
 */
class MultiplayersEvent extends PSEvent{

	public var sMessage : String;

	public static inline var GAME_START	: String = "HypPS_GAME_START";
	public static inline var ON_MESSAGE	: String = "HypPS_ON_MESSAGE";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , statusCode : Status , ?sMessage : String ) {
			super( t , statusCode );
			this.sMessage = sMessage;
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		override public function clone( ) : MultiplayersEvent {
			return new MultiplayersEvent( type , status , sMessage );
		}

	// -------o protected



	// -------o misc

}
