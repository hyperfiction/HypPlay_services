package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.events.APlayServicesEvent;

/**
 * ...
 * @author shoe[box]
 */

class SigninEvent extends APlayServicesEvent{

	public static inline var SUCCESS	: String = "HypPS_SignIn_SUCCESS";
	public static inline var FAILED	: String = "HypPS_SignIn_FAILED";

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , s : Status ) {
			super( t , s );
		}

	// -------o public

	// -------o protected

	// -------o misc

}