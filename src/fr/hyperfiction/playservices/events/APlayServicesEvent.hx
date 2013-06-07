package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.StatusCode;
import nme.events.Event;

/**
 * ...
 * @author shoe[box]
 */

class APlayServicesEvent extends Event{

	public var status : Status;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , status : Status ) {
			super( t );
			this.status = status;
		}

	// -------o public



	// -------o protected



	// -------o misc

}