package fr.hyperfiction.playservices;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Achievements{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private function new() {

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function open( ) : Void {

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
		static public function increment( sId : String , numSteps : Int , ?bSync : Bool ) : Void {

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
		static public function revealAchievement( sId : String , ?bSync : Bool ) : Void {

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
		static public function unlockAchievement( sId : String , ?bSync : Bool ) : Void {

		}


	// -------o protected



	// -------o misc

}