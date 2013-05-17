package;

import fr.hyperfiction.HypPlayServices;
import nme.display.Sprite;
import nme.text.TextField;
import nme.events.MouseEvent;

/**
 * ...
 * @author shoe[box]
 */

class TestHypPlay extends Sprite{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new() {
			trace("constructor");
			super( );
			_buttons( );
			HypPlayServices.onStatus = function( s : String , sArg : String ){
				trace("onStatus ::"+s+" - "+sArg);

				switch( s ){

					case HypPlayServices.INIT:


					case HypPlayServices.SIGIN_SUCCESS:
						trace("SIGIN_SUCCESS");
						trace("isSignedIn ::: "+HypPlayServices.isSignedIn( ) );
						trace( HypPlayServices.getScopes( ));
						//trace( HypPlayServices.signOut( ));
						//trace("isSignedIn ::: "+HypPlayServices.isSignedIn( ) );
						//HypPlayServices.submitScore("CgkI_42Q27cXEAIQAQ",150);
						//HypPlayServices.showLeaderboard("CgkI_42Q27cXEAIQAQ");
						//HypPlayServices.unlockAchievement("CgkI_42Q27cXEAIQAw");
						//HypPlayServices.showAchievements( );

				}

			}
			HypPlayServices.init( );


		}

	// -------o public



	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _buttons( ) : Void{
			var a : Array<String> = [ "SIGN" , "ACHIEVMENTS" , "LEADERBOARD" , "ALL_LEADERBOARDS" ];

			var inc = 0;
			var spContainer = new Sprite( );
			for( s in a ){

				var btn = new TestButton( s );
					btn.y = inc * 60;
					btn.name = s;
				spContainer.addChild( btn );
				inc++;

			}
			spContainer.x = ( nme.Lib.current.stage.stageWidth - spContainer.width ) / 2;
			spContainer.y = ( nme.Lib.current.stage.stageHeight - spContainer.height ) / 2;
			addChild( spContainer );

			spContainer.addEventListener( MouseEvent.MOUSE_UP , function( e : MouseEvent ){

				switch( e.target.name ){
					case "SIGN":
						trace("HypPlayServices.isSignedIn( ) ::: "+HypPlayServices.isSignedIn( ));
						HypPlayServices.beginUserInitiatedSignIn( );
						trace("HypPlayServices.isSignedIn( ) ::: "+HypPlayServices.isSignedIn( ));

					case "ACHIEVMENTS":
						HypPlayServices.showAchievements( );

					case "LEADERBOARD":
						HypPlayServices.showLeaderboard("CgkI_42Q27cXEAIQAQ");

					case "ALL_LEADERBOARDS":
						HypPlayServices.showAllLeaderboards( );

				}

			});
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _sign( ) : Void{
			trace("init");
			trace("isSignedIn ::: "+HypPlayServices.isSignedIn( ) );
			HypPlayServices.enableDebug_log( true  , "debug" );
			HypPlayServices.beginUserInitiatedSignIn( );

		}

	// -------o misc

}

/**
 * ...
 * @author shoe[box]
 */

class TestButton extends Sprite{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( s : String ) {
			super( );
			graphics.beginFill( 0xEEEEEE );
			graphics.drawRect( 0 , 0 , 200 , 40 );
			graphics.endFill( );

			var tf = new TextField( );
				tf.width = 200;
				tf.height = 40;
				tf.text = s;
			addChild( tf );

			mouseEnabled = true;
			mouseChildren = false;
		}

	// -------o public



	// -------o protected



	// -------o misc

}