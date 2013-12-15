package fr.hyperfiction.playservices.events;

import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.events.APlayServicesEvent;

/**
 * ...
 * @author shoe[box]
 */

class AchievementsEvent extends APlayServicesEvent{

	public var aCodes  : Array<String>;
	public var aStatus : Array<Int>;

	static public inline var ON_LIST : String = "HypPS_ON_ACHIEVEMENT_LIST";
	
	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( t : String , aCodes : Array<String>, aStatus : Array<Int> ) {
			super( t , StatusCode.translate( 0 ) );
			this.aCodes  = aCodes;
			this.aStatus = aStatus;
		}

	// -------o public

	// -------o protected

	// -------o misc

}