package fr.hyperfiction.playservices;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig.Builder;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.gson.Gson;

import fr.hyperfiction.playservices.PlayHelper;
import fr.hyperfiction.playservices.PlayServicesFrag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.haxe.nme.GameActivity;
import org.haxe.nme.HaxeObject;
import org.json.JSONObject;

/**
 * ...
 * @author shoe[box]
 */

class Multiplayers implements RealTimeMessageReceivedListener,
						OnInvitationReceivedListener,
						RealTimeReliableMessageSentListener,
						RoomStatusUpdateListener,
						RoomUpdateListener{

	static public native int onEvent( String jsEvName , String javaArg , int statusCode );
	static public native int onDatas( String sDatas , String sFrom );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	final public static String GAME_START		= "HypPS_GAME_START";
	final public static String INVITE_ACCEPTED	= "HypPS_INVITE_ACCEPTED";
	final public static String INVITE_CANCEL	= "HypPS_INVITE_CANCEL";
	final public static String INVITE_SENT		= "HypPS_INVITE_SENT";
	final public static String INVITE_USERS		= "HypPS_INVITE_USERS";
	final public static String ON_INVITATION	= "HypPS_ON_INVITATION";
	final public static String ON_MESSAGE		= "HypPS_ON_INVITATION";
	final public static String PEER_CONNECTED	= "HypPS_PEER_CONNECTED";
	final public static String PEER_DECLINED	= "HypPS_PEER_DECLINED";
	final public static String PEER_DISCONNECTED	= "HypPS_PEER_DISCONNECTED";
	final public static String PEER_JOINED		= "HypPS_PEER_JOINED";
	final public static String PEER_LEFT		= "HypPS_PEER_LEFT";
	final public static String ROOM_CONNECTED	= "HypPS_ROOM_CONNECTED";
	final public static String ROOM_CREATED		= "HypPS_ROOM_CREATED";
	final public static String ROOM_JOINED		= "HypPS_ROOM_JOINED";
	final public static String ROOM_LEFT		= "HypPS_ROOM_LEFT";
	final public static String RTM_SEND		= "HypPS_RTM_SEND";

	public static final int ID_INVITE_INTENT	= 5004;
	public static final int ID_WAIT_INTENT		= 5005;
	public static final int ID_INVITATIONS_INBOX	= 5006;

	private static GLSurfaceView _mSurface;
	private static Room _currentRoom;
	private static int _iMinOpp;
	private static int _iMaxOpp;
	private static HaxeObject _hxListener;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private Multiplayers() {

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void initialize( HaxeObject hxListener ){
			_hxListener = hxListener;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String checkFor_invitations( ){
			trace("checkFor_invitations");
			return PlayHelper.getInstance( ).getInvitationId( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void listenFor_invitations( ){
			trace("listenFor_invitations");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						trace("run");
						getGamesClient( ).registerInvitationListener( Multiplayers.getInstance( ) );
					}
				}
			);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void stopListen_for_invitations( ){
			trace("stopListen_for_invitations");
			getGamesClient( ).unregisterInvitationListener( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void acceptInvitation( String sInvitation_id ){
			trace("acceptInvitation ::: "+sInvitation_id);
			if( sInvitation_id == null )
				return;

			RoomConfig.Builder 	b = makeBasicRoomConfigBuilder( );
							b.setInvitationIdToAccept( sInvitation_id );
			getGamesClient( ).joinRoom( b.build( ) );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void openInvitations_inbox( ){
			trace("openInvitations_inbox");
			_runIntent( getGamesClient( ).getInvitationInboxIntent( ) , ID_INVITATIONS_INBOX );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void handleInvitation_select( int resultCode , Intent datas ){
			trace("handleInvitation_select");

			//If the invitation popup have been canceled
				if( resultCode != Activity.RESULT_OK ){
					onEvent_wrapper( INVITE_CANCEL , "" , resultCode);
					return;
				}else
					onEvent_wrapper( INVITE_ACCEPTED , "" , resultCode);

			//
				Bundle extras = datas.getExtras( );
				Invitation invitation = extras.getParcelable( GamesClient.EXTRA_INVITATION );

			//
				RoomConfig roomConfig = makeBasicRoomConfigBuilder( )
			               .setInvitationIdToAccept(invitation.getInvitationId())
			               .build();
				getGamesClient().joinRoom( roomConfig );


		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void handleInvitation_results( int resultCode , Intent datas ){
			trace("handleInvitation_results ::: "+resultCode+" = "+datas);
			trace("resultCode ::: "+resultCode+" == "+Activity.RESULT_OK);

			//If the invitation popup have been canceled
				if( resultCode != Activity.RESULT_OK ){
					onEvent_wrapper( INVITE_CANCEL , "" , resultCode);
					return;
				}
				onEvent_wrapper( INVITE_SENT , "" , resultCode);

			//The invitess list
				final ArrayList<String> invitees = datas.getStringArrayListExtra(GamesClient.EXTRA_PLAYERS);
				trace("invitees ::: "+invitees.toString( ));

			//Get the automatch criteria
				Bundle autoMatchCriteria = null;
				int minAutoMatchPlayers = datas.getIntExtra( GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS , 0 );
				int maxAutoMatchPlayers = datas.getIntExtra( GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS , 0 );
				if (minAutoMatchPlayers > 0) {
					autoMatchCriteria =
					RoomConfig.createAutoMatchCriteria(
					minAutoMatchPlayers, maxAutoMatchPlayers, 0);
				} else {
					autoMatchCriteria = null;
				}

			//Create the room
				trace("handleInvitation_results::RoomConfig builder");
				 RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
			        				roomConfigBuilder.addPlayersToInvite(invitees);

				if (autoMatchCriteria != null)
					roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);

				RoomConfig roomConfig = roomConfigBuilder.build();

			//Create the room
				trace("handleInvitation_results::createRoom");
				getGamesClient( ).createRoom(roomConfig);

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void handleWaitingroom_results( int resultCode , Intent datas ){
			trace("resultCode ::: "+resultCode+" - "+datas);
			if (resultCode == Activity.RESULT_OK) {
				trace("handleWaitingroom_results:::RESULT_OK");
				onEvent_wrapper( GAME_START , "" , resultCode );
			}else if (resultCode == Activity.RESULT_CANCELED) {
				trace("handleWaitingroom_results::::RESULT_CANCELED");
				onEvent_wrapper( INVITE_CANCEL , "" , resultCode );
			}
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void quickGame( int iMin , int iMax ){

			//Automatch criteria
				Bundle bun = RoomConfig.createAutoMatchCriteria( iMin , iMax , 0 );

			//Room config
				RoomConfig.Builder 	b = makeBasicRoomConfigBuilder( );
								b.setAutoMatchCriteria( bun );

			//Create the room
				getGamesClient( ).createRoom( b.build( ) );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void invitePlayers( int iMin , int iMax ){
			trace("invitePlayers ::: min : "+iMin+" | max :" +iMax);
			Intent intent = getGamesClient().getSelectPlayersIntent( iMin , iMax );
			_runIntent( intent , ID_INVITE_INTENT );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void openWaiting_room( ){
			Intent i = getGamesClient().getRealTimeWaitingRoomIntent( _currentRoom , Integer.MAX_VALUE );
			_runIntent( i , ID_WAIT_INTENT );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void sendString( String sMessage ){
			trace("sendString :: "+sMessage+" - "+_currentRoom.getRoomId( ));
			trace( "room ::: "+_currentRoom.getRoomId( ) );
			byte[] ba = sMessage.getBytes();
			getGamesClient( ).sendUnreliableRealTimeMessageToAll(
															ba,
															_currentRoom.getRoomId( )
														);
			ba = null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void sendString_reliable( String sMessage ){
			trace("sendString_reliable :: "+sMessage);
			trace( "room ::: "+_currentRoom.getRoomId( ) );
			trace( "participants ::: "+_currentRoom.getParticipantIds( ) );
			byte[] ba = sMessage.getBytes();
			trace(" p ::: "+_currentRoom.getParticipants( ) );
			String me =  _currentRoom.getParticipantId( getGamesClient( ).getCurrentPlayerId( ) );
			for( Participant p : _currentRoom.getParticipants() )
				if( p.getParticipantId( ) != me )
				getGamesClient( ).sendReliableRealTimeMessage(
														getInstance( ),
														ba,
														_currentRoom.getRoomId( ),
														p.getParticipantId( )
													);
			ba = null;
		}


	// -------o callbacks

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onJoinedRoom( final int statusCode , final Room room ){
			if( statusCode != 0 )
				return;

			_currentRoom = room;
			onEvent_wrapper( ROOM_JOINED , _serializeRoom(room) , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	voidf
		*/
		public void onLeftRoom( final int statusCode , final String roomId ){
			_currentRoom = null;
			onEvent_wrapper( ROOM_LEFT , roomId , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomConnected( final int statusCode , final Room room ){
			_currentRoom = room;
			onEvent_wrapper( ROOM_CONNECTED , _serializeRoom(room) , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomCreated( final int statusCode , final Room room ){
			_currentRoom = room;
			onEvent_wrapper( ROOM_CREATED , room != null ? _serializeRoom(room) : "" , statusCode);
		}


		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onConnectedToRoom( Room room ){
			trace("onConnectedToRoom");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void  onDisconnectedFromRoom(Room room){
			trace("onDisconnectedFromRoom");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerDeclined(Room room, List<String> participantIds){
			trace("onPeerDeclined");
			onEvent_wrapper( PEER_DECLINED , participantIds.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerInvitedToRoom(Room room, List<String> participantIds){
			trace("onPeerInvitedToRoom");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerJoined(Room room, List<String> participantIds){
			trace("onPeerJoined");
			onEvent_wrapper( PEER_JOINED , participantIds.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerLeft(Room room, List<String> participantIds){
			trace("onPeerLeft");
			onEvent_wrapper( PEER_LEFT , participantIds.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersConnected(Room room, List<String> participantIds){
			trace("onPeersConnected");
			onEvent_wrapper( PEER_CONNECTED , participantIds.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersDisconnected(Room room, List<String> participantIds){
			trace("onPeersDisconnected");
			onEvent_wrapper( PEER_DISCONNECTED , participantIds.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomAutoMatching(Room room){
			trace("onRoomAutoMatching");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomConnecting(Room room){
			trace("onRoomConnecting");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onInvitationReceived( Invitation invit ){
			trace("onInvitationReceived ::: "+invit);
			JSONObject o = new JSONObject( );
			JSONObject from = new JSONObject( );
			try{

				from.put("bConnected_to_room"	, invit.getInviter( ).isConnectedToRoom( ) );
				from.put("iStatus"			, invit.getInviter( ).getStatus( ) );
				from.put("sFrom_icon_uri"	, invit.getInviter( ).getIconImageUri( ) );
				from.put("sFrom_id "		, invit.getInviter( ).getParticipantId( ) );
				from.put("sFrom_image_uri"	, invit.getInviter( ).getHiResImageUri( ) );
				from.put("sFrom_name"		, invit.getInviter( ).getDisplayName( ) );

				o.put("sTimestamp"		, invit.getCreationTimestamp( ));
				o.put("sInvitation_id"	, invit.getInvitationId( ));
				o.put("from"			, from);

			}catch( org.json.JSONException e ){
				trace( "error ::: "+e );
			}
			onEvent_wrapper( ON_INVITATION , o.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRealTimeMessageReceived(RealTimeMessage rtm){
			trace("onRealTimeMessageReceived ::: "+rtm);
			byte[] buff = rtm.getMessageData( );
			String msg = null;
			try{
				msg = new String( rtm.getMessageData( ) , "UTF-8");
			}catch( java.io.UnsupportedEncodingException e ){

			}
			onDatas_wrapper( msg , rtm.getSenderParticipantId() );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRealTimeMessageSent( int status , int tokenId , String participantId ){
			final JSONObject o = new JSONObject( );
			try{
				o.put("tokenId" , tokenId );
				o.put("participantId" , participantId );
			}catch( org.json.JSONException e ){
				trace( "error ::: "+e );
			}
			onEvent_wrapper( RTM_SEND , o.toString( ) , status );
		}

	// -------o protected

		static private void onEvent_wrapper( final String jsEvName , final String javaArg , final int status ) {
			trace("onEvent_wrapper ::: "+jsEvName+" - "+javaArg+" - "+status);
			if( _mSurface == null )
				_mSurface = (GLSurfaceView) GameActivity.getInstance().getCurrentFocus();

			_mSurface.queueEvent(new Runnable() {
				@Override
				public void run() {
					onEvent( jsEvName , javaArg , status );
				}
			});

		}

		static private void onDatas_wrapper( final String sDatas , final String sFrom ) {
			trace("onDatas_wrapper ::: "+sDatas+" - "+sFrom );
			/*
			//_hxListener.call2( "onDatas" , sDatas , sFrom );
			//onDatas( sDatas, sFrom );

			if( _mSurface == null )
				_mSurface = (GLSurfaceView) GameActivity.getInstance().getCurrentFocus();

			_mSurface.queueEvent(new Runnable() {
				@Override
				public void run() {
					onDatas( sDatas, sFrom );
				}
			});
			*/
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private String _serializeRoom( Room r ){
			final JSONObject o = new JSONObject( );
			try{
				o.put("createId" , r.getCreatorId() );
				o.put("creationtimestamp" , r.getCreationTimestamp( ) );
				o.put("description" , r.getDescription() );
				o.put("participants",r.getParticipantIds( ));
				o.put("roomId" , r.getRoomId( ) );
				o.put("status",r.getStatus( ));
				o.put("variant" , r.getVariant( ) );
			}catch( org.json.JSONException e ){
				trace( "error ::: "+e );
			}
			return o.toString( );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private void _runIntent( final Intent i , final int id ){
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayServicesFrag.getInstance( ).startActivityForResult( i , id );
					}
				});
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private RoomConfig.Builder makeBasicRoomConfigBuilder() {
			return RoomConfig.builder( Multiplayers.getInstance( ) )
				.setMessageReceivedListener( Multiplayers.getInstance( ) )
				.setRoomStatusUpdateListener( Multiplayers.getInstance( ) );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private GamesClient getGamesClient( ){
			return PlayHelper.getInstance( ).getGamesClient( );
		}


	// -------o misc

		/*
		*
		*
		* @private
		* @return	void
		*/
		public static void trace( String s ){
			Log.w( TAG, s );
		}
		private static String TAG = "trace";//HypFacebook";

		public static Multiplayers getInstance( ){
			if( __instance == null )
				__instance = new Multiplayers( );
			return __instance;
		}

		private static Multiplayers __instance = null;
}