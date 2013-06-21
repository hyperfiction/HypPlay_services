package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.Multiplayers;
import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.events.PSEvent;

import nme.events.Event;


/**
 * ...
 * @author shoe[box]
 */
class InvitationEvent extends PSEvent{

	public var invit : InvitationDesc;

	public static inline var ACCEPTED	: String = "HypPS_INVITE_ACCEPTED";
	public static inline var CANCEL	: String = "HypPS_INVITE_CANCEL";
	public static inline var RECEIVED	: String = "HypPS_ON_INVITATION";
	public static inline var SENT		: String = "HypPS_INVITE_SENT";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , statusCode : Status , ?invit : InvitationDesc ) {
			super( t , statusCode );
			this.invit = invit;
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		public override function clone() : InvitationEvent {
			return new InvitationEvent( type , status , this.invit );
		}

	// -------o protected



	// -------o misc

}