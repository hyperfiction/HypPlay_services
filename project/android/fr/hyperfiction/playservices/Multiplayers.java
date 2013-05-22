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

	public static final int ID_INVITE_INTENT = 5004;

	private static RoomConfig.Builder _room;
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
		static public void leaveRoom( String sRoom_ID ){
			PlayHelper.getInstance( ).getGamesClient( ).leaveRoom( Multiplayers.getInstance( ) , sRoom_ID );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void acceptInvitation( String sInvite_ID ){
			trace("acceptInvitation ::: "+sInvite_ID);
			_buildRoom( );
			_room.setInvitationIdToAccept( sInvite_ID );
			_joinRoom( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void onInvitation( final String sInvite_ID ){
			trace("onInvitation ::: "+sInvite_ID);
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayServices.onEvent( PlayServices.ON_INVITATION , sInvite_ID , 0 );
					}
				});

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void handleResults( int resultCode , Intent datas ){
			if( resultCode != Activity.RESULT_OK ){
				_cancelInvite( );
			}else{
				_inviteUsers( datas.getExtras( ) );
			}
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void listenFor_invitations( final boolean bListen ){
			trace("listenFor_invitations ::: "+bListen);
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						if( bListen )
							PlayHelper.getInstance( ).getGamesClient( ).registerInvitationListener( getInstance( ) );
						else
							PlayHelper.getInstance( ).getGamesClient( ).unregisterInvitationListener( );
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
		static public void quickGame( int iMin_opponents , int iMax_opponents , int exclusiveBitMask ) {
			try{
				Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria( iMin_opponents , iMax_opponents , exclusiveBitMask );
				Multiplayers listener = Multiplayers.getInstance( );

				RoomConfig.Builder 	r = _buildRoom( );
	        						r.setAutoMatchCriteria(autoMatchCriteria);
	        		_createRoom( );
        		}catch( Exception e) {
				trace( "Exception in quickGame" );
				e.printStackTrace();
			}

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void invite( int iMin_opponents , int iMax_opponents ){
			trace("invite");

			final Intent intent = PlayHelper.getInstance( ).getGamesClient().getSelectPlayersIntent(
																				_iMinOpp = iMin_opponents ,
																				_iMaxOpp = iMax_opponents
																			);
			GameActivity.getInstance( ).runOnUiThread(
					new Runnable( ) {
						public void run() {
							try{
								PlayServices.getInstance( ).frag.startActivityForResult(intent, ID_INVITE_INTENT );
							}catch( Exception e) {
								trace( "Exception in invite" );
								e.printStackTrace();
							}
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
		public void onJoinedRoom( final int statusCode , final Room room ){
			trace("onJoinedRoom ::: ");
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
		private static void _inviteUsers( Bundle extras ){
			trace("_inviteUsers ::: "+extras);
			final ArrayList<String> players = extras.getStringArrayList( GamesClient.EXTRA_PLAYERS );
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						onEvent( INVITE_USERS , players.toString( ) , 0 );
					}
				}
			);

        		_buildRoom( );
        		_room.addPlayersToInvite( players );
        		_createRoom( );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private static void _cancelInvite( ){
			onEvent( INVITE_CANCEL , "" , 0 );
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private RoomConfig.Builder _buildRoom( ){
			Multiplayers listener = Multiplayers.getInstance( );
			_room = RoomConfig.builder( listener );
			_room.setMessageReceivedListener( listener );
			_room.setRoomStatusUpdateListener( listener );
        		return _room;
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private static void _createRoom( ){
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayHelper.getInstance( ).getGamesClient( ).createRoom( _room.build( ) );
					}
				}
			);
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		private static void _joinRoom( ){
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						PlayHelper.getInstance( ).getGamesClient( ).joinRoom( _room.build( ) );
					}
				}
			);
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