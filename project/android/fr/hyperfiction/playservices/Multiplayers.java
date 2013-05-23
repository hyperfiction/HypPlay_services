package fr.hyperfiction.playservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig.Builder;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;

import fr.hyperfiction.playservices.PlayHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.haxe.nme.GameActivity;
import org.json.JSONObject;

/**
 * ...
 * @author shoe[box]
 */

class Multiplayers implements RealTimeMessageReceivedListener,
						OnInvitationReceivedListener,
						RoomStatusUpdateListener,
						RoomUpdateListener{

	static public native void onEvent( String jsEvName , String javaArg , int statusCode );
	static{
		System.loadLibrary( "HypPlayServices" );
	}

	final public static String INVITE_CANCEL	= "HypPS_INVITE_CANCEL";
	final public static String INVITE_USERS		= "HypPS_INVITE_USERS";
	final public static String ON_INVITATION	= "HypPS_ON_INVITATION";
	final public static String ROOM_CONNECTED	= "HypPS_ROOM_CONNECTED";
	final public static String ROOM_CREATED		= "HypPS_ROOM_CREATED";
	final public static String ROOM_JOINED		= "HypPS_ROOM_JOINED";
	final public static String ROOM_LEFT		= "HypPS_ROOM_LEFT";

	public static final int ID_INVITE_INTENT	= 5004;
	public static final int ID_WAIT_INTENT		= 5005;
	public static final int ID_INVITATIONS_INBOX	= 5006;

	private static Room _currentRoom;
	private static int _iMinOpp;
	private static int _iMaxOpp;



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
			_runIntent( getGamesClient().getInvitationInboxIntent() , ID_INVITATIONS_INBOX );
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
					onEvent( INVITE_CANCEL , "" , resultCode);
					return;
				}

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
					onEvent( INVITE_CANCEL , "" , resultCode);
					return;
				}

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
		static public void showWaiting_room( ){
			Intent i = getGamesClient().getRealTimeWaitingRoomIntent( _currentRoom , Integer.MAX_VALUE );
			_runIntent( i , ID_WAIT_INTENT );
		}

	// -------o callbacks

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onJoinedRoom( final int statusCode , final Room room ){
			trace("onJoinedRoom ::: "+statusCode+" - "+room);

			if( statusCode != 0 )
				return;

			_currentRoom = room;
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						onEvent( ROOM_JOINED , _serializeRoom(room) , statusCode);
					}
				});
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onLeftRoom( final int statusCode , final String roomId ){
			trace("onLeftRoom ::: ");
			_currentRoom = null;
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						onEvent( ROOM_LEFT , roomId , statusCode);
					}
				});
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomConnected( final int statusCode , final Room room ){
			trace("onRoomConnected ::: ");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						onEvent( ROOM_CONNECTED , _serializeRoom(room) , statusCode);
					}
				});
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomCreated( final int statusCode , final Room room ){
			trace("onRoomCreated ::: "+room);
			_currentRoom = room;
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						onEvent( ROOM_CREATED , room != null ? _serializeRoom(room) : "" , statusCode);
					}
				});
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
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerLeft(Room room, List<String> participantIds){
			trace("onPeerLeft");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersConnected(Room room, List<String> participantIds){
			trace("onPeersConnected");
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersDisconnected(Room room, List<String> participantIds){
			trace("onPeersDisconnected");
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
			onEvent( ON_INVITATION , o.toString( ) , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRealTimeMessageReceived(RealTimeMessage message){
			trace("onRealTimeMessageReceived ::: "+message);
		}

	// -------o protected

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
						PlayServices.getInstance( ).frag.startActivityForResult( i , id );
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