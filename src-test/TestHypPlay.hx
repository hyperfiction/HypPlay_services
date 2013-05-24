package;

//import fr.hyperfiction.HypPlayServices;

import fr.hyperfiction.playservices.PlayServices;
import fr.hyperfiction.playservices.Achievements;
import fr.hyperfiction.playservices.Leaderboards;
import fr.hyperfiction.playservices.Multiplayers;
import fr.hyperfiction.playservices.StatusCode;

import nme.Lib;
import nme.display.Sprite;
import nme.text.TextField;
import nme.events.MouseEvent;

/**
 * ...
 * @author shoe[box]
 */

class TestHypPlay extends Sprite{

	private var _sRoom : String;

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
			Lib.current.stage.scaleMode = nme.display.StageScaleMode.NO_SCALE;
			Lib.current.stage.align     = nme.display.StageAlign.TOP_LEFT;
			haxe.Timer.delay( _init , 200 );
		}

	// -------o public



	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _init( ) : Void{
			Multiplayers.onEvent = _onMultiplayer_event;
			Multiplayers.onInvitation = _onInvitation;
			PlayServices.onStatus = _onStatus;
			PlayServices.onLeaderboard_metas = _onLb_metas;
			PlayServices.initialize( );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _onStatus( s : String , sArg : String , iCode : Int ) : Void{
			trace("_onStatus ::: "+s+" || arg : "+sArg+" || code : "+iCode);

			switch( s ){

				case PlayServices.INIT:
					trace("isSignedIn : "+PlayServices.isSigned_in( ));
					PlayServices.beginUserInitiated_sign_in( );

				case PlayServices.SIGIN_SUCCESS:
					trace("SIGIN_SUCCESS");
					Multiplayers.listenFor_invitations( );
					_buttons( );

				case PlayServices.ON_SCORE_SUBMITTED:
					trace("ON_SCORE_SUBMITTED ::: "+sArg);

				case PlayServices.ON_INVITATION:
					trace("onInvitation ::: "+sArg);
					Multiplayers.acceptInvitation( sArg );

			}
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _buttons( ) : Void{
			//var a : Array<String> = [ "INVITE","QUICK_GAME", "ACHIEVEMENT" , "ACHIEVEMENT_INC" , "UNLOCK_ACHIEVEMENT" , "LEADERBOARD" , "ALL_LEADERBOARDS" , "SUBMIT_SCORE"];
			var a : Array<String> = [ "INVITE","INVITATION_INBOX","QUICK_GAME","WAITING_ROOM","SEND_UNRELIABLE","SEND_RELIABLE"];

			var inc = 0;
			var spContainer = new Sprite( );
			for( s in a ){

				var btn = new TestButton( s );
					btn.y = inc;
					btn.name = s;
				spContainer.addChild( btn );
				inc+=Std.int( btn.height + 20 );

			}
			spContainer.x = ( nme.Lib.current.stage.stageWidth - spContainer.width ) / 2;
			spContainer.y = ( nme.Lib.current.stage.stageHeight - spContainer.height ) / 2;
			addChild( spContainer );

			spContainer.addEventListener( MouseEvent.MOUSE_UP , function( e : MouseEvent ){

				switch( e.target.name ){

					case "INVITE":
						trace("invite");
						Multiplayers.invitePlayers( 1 , 1 );

					case "INVITATION_INBOX":
						Multiplayers.openInvitations_inbox( );

					case "QUICK_GAME":
						Multiplayers.quickGame( 1 , 1 );

					case "WAITING_ROOM":
						Multiplayers.showWaiting_room( );

					case "SEND_UNRELIABLE":
						trace("SEND_UNRELIABLE");
						Multiplayers.sendString( "Test depuis haxe é'(-è_çà)'" );

					case "SEND_RELIABLE":
						trace("SEND_RELIABLE");
						Multiplayers.sendString_reliable( "Test depuis haxe é'(-è_çà)'" );

					/*


					case "ACHIEVEMENT":
						Achievements.open( );

					case "ACHIEVEMENT_INC":
						Achievements.increment( "CgkI_42Q27cXEAIQBA" , 2 , false );

					case "ACHIEVEMENT_INC_SYNC":
						Achievements.increment( "CgkI_42Q27cXEAIQBA" , 2 , true );

					case "REVEAL_ACHIEVEMENT":
						Achievements.revealAchievement( "CgkI_42Q27cXEAIQBQ" );

					case "UNLOCK_ACHIEVEMENT":
						Achievements.unlockAchievement("CgkI_42Q27cXEAIQBQ",true);

					case "LEADERBOARD":
						Leaderboards.openBy_id( "CgkI_42Q27cXEAIQAQ" );

					case "ALL_LEADERBOARDS":
						Leaderboards.openAll( );

					case "LEADERBOARD_METAS":
						Leaderboards.loadMeta_datas( "CgkI_42Q27cXEAIQAQ" );

					case "SUBMIT_SCORE":
						Leaderboards.submitScore( "CgkI_42Q27cXEAIQAQ" , 250 );

					case "SUBMIT_SCORE_SYNC":
						Leaderboards.submitScore( "CgkI_42Q27cXEAIQAQ" , 250 , true );

					case "LEAVE_ROOM":
						trace("leaveRoom ::: "+_sRoom);
						Multiplayers.leaveRoom( _sRoom );
					*/
				}

			});
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _onMultiplayer_event( s : String , room : RoomDesc , status : Status ) : Void{
			trace("_onMultiplayer_event ::: "+s+" - "+room+" - "+status);
			trace( s+" === "+Multiplayers.ROOM_CREATED+" === "+(s==Multiplayers.ROOM_CREATED));
			switch( s ){

				case Multiplayers.ROOM_CREATED:
				trace("ROOM_CREATED ( "+status+" ) ::: "+room+" - "+status);
					_sRoom = room.createId;
					Multiplayers.showWaiting_room( );

				case Multiplayers.ROOM_JOINED:
					_sRoom = room.roomId;


			}

		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _onInvitation( sInvitId : String , desc : InvitationDesc , status : Status ) : Void{
			trace("_onInvitation");

			if( status != OK )
				return;

			trace( desc );
			trace( status );
			//Multiplayers.acceptInvitation( desc.sInvitation_id );
			Multiplayers.openInvitations_inbox( );

		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private function _onLb_metas( a : Array<LeaderboardMetas> ) : Void{
			trace("_onLb_metas ::: "+a);
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
			graphics.drawRect( 0 , 0 , Lib.current.stage.stageWidth , Lib.current.stage.stageHeight * 0.08 );
			graphics.endFill( );

			var 	tf = new TextField( );
				tf.defaultTextFormat = new nme.text.TextFormat( null , 25 );
				tf.width = width;
				tf.height = height;
				//tf.border = true;
				tf.text = s;
			addChild( tf );

			mouseEnabled = true;
			mouseChildren = false;
		}

	// -------o public



	// -------o protected



	// -------o misc

}