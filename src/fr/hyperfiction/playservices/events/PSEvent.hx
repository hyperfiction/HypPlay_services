package fr.hyperfiction.playservices.events;

import nme.events.Event;
import fr.hyperfiction.playservices.StatusCode;

/**
 * ...
 * @author shoe[box]
 */
class PSEvent extends Event{

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