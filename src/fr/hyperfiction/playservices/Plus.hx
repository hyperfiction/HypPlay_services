package fr.hyperfiction.playservices;

/**
 * ...
 * @author shoe[box]
 */
@:build( ShortCuts.mirrors( ) )
class Plus{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new() {

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
		static public function basicSharing(
										sText	: String ,
										sUrl		: String
									 	) : Void {

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
		static public function getAccount_name( ) : String {
			return "PlusAccount_name";
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
		static public function getFirst_name( ) : String{
			return "PlusFirst_name";
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
		static public function getFamily_name( ) : String{
			return "PlusFamily_name";
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
		static public function getDisplay_name( ) : String {
			return "PlusDisplay_name";
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
		static public function getNick_name( ) : String {
			return "PlusNick_name";
		}

	// -------o protected



	// -------o misc

}