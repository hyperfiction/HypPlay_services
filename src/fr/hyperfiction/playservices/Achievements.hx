package fr.hyperfiction.playservices;

#if usesignals
import fr.hyperfiction.playservices.StatusCode;
import fr.hyperfiction.playservices.StatusCode.Status;
import org.shoebox.signals.Signal;
#end

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Achievements{

	#if usesignals
	static public var onList : SignalAch = new SignalAch( );
	#end

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
		static public function loadUnlocked_list( ) : Void {
						
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

		/**
		* Return the current achievement step progress ( if any )
		* 
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function getCurrentSteps( id : Int ) : Int {
			return 0;
		}

		/**
		* Return the current state of the achievement
		* 
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function getState( id : Int ) : Int {
			return 1;		
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
		static public function getId( sCode : String ) : Int {
			return -1;		
		}

	// -------o protected



	// -------o misc

}

#if usesignals
/**
 * ...
 * @author shoe[box]
 */
class SignalAch extends Signal<Status->Array<String>->Array<Int>->Void>{
}
#end