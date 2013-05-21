package fr.hyperfiction.playservices;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Leaderboards{

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
		* Open the specified leaderboard
		*
		* @leaderbards
		* @param sID : Leadboard ID ( String )
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function openBy_id( ?sId : String ) : Void {

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
		static public function openAll( ) : Void {

		}

		/**
		* Load the metadatas of the leaderboard
		*
		* @param sId : Leaderboard id ( String )
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function loadMeta_datas( sId : String ) : Void {

		}

		/**
		* Submit a new score
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function submitScore( sId : String , iScore : Int , ?bSync : Bool ) : Void {

		}

	// -------o protected

	// -------o misc

}