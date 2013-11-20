package ;
import flash.display.Sprite;
import flash.Lib;
import haxe.Timer;

import fr.hyperfiction.playservices.PlayServices;

/**
 * ...
 * @author shoe[box]
 */

class Main extends Sprite{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new() {
			super( );
			graphics.beginFill( 0xFF );
			graphics.drawRect( 0 , 0 , 100 , 100 );
			graphics.endFill( );
			Timer.delay( _run , 200 );
			

		}

	// -------o public



	// -------o protected
		
		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _run( ) : Void {
			trace("_run");
			Lib.current.stage.addEventListener( flash.events.MouseEvent.MOUSE_DOWN , _onMouse_down , false );
		}
	
		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _onMouse_down( _ ) : Void {
			trace("_onMouse_down");
			//PlayServices.signOut( );
			PlayServices.initialize( );
			PlayServices.beginUserInitiated_sign_in( );
		}

	// -------o misc

}