package fr.hyperfiction.playservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

	public static String ROOM_CONNECTED		= "HypPS_ROOM_CONNECTED";
	public static String ROOM_CREATED			= "HypPS_ROOM_CREATED";
	public static String ROOM_JOINED			= "HypPS_ROOM_JOINED";
	public static String ROOM_LEFT			= "HypPS_ROOM_LEFT";
	public static String INVITE_CANCEL			= "HypPS_INVITE_CANCEL";
	public static String INVITE_USERS			= "HypPS_INVITE_USERS";

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
		static public void listenFor_invitations( boolean bListen ){

			if( bListen )
				PlayHelper.getInstance( ).getGamesClient( ).registerInvitationListener( getInstance( ) );
			else
				PlayHelper.getInstance( ).getGamesClient( ).unregisterInvitationListener( );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void quickGame( int iMin_opponents , int iMax_opponents , int exclusiveBitMask ) {
			Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria( iMin_opponents , iMax_opponents , exclusiveBitMask );

			Multiplayers listener = Multiplayers.getInstance( );

			RoomConfig.Builder 	r = _buildRoom( );
        						r.setAutoMatchCriteria(autoMatchCriteria);
        		_createRoom( );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void invite( int iMin_opponents , int iMax_opponents ){
			trace("invite");

			Intent intent = PlayHelper.getInstance( ).getGamesClient().getSelectPlayersIntent(
																				_iMinOpp = iMin_opponents ,
																				_iMaxOpp = iMax_opponents
																			);
			PlayServices.getInstance( ).frag.startActivityForResult(intent, ID_INVITE_INTENT );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onJoinedRoom(int statusCode, Room room){
			onEvent( ROOM_JOINED , _serializeRoom(room) , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onLeftRoom(int statusCode, String roomId){
			onEvent( ROOM_LEFT , roomId , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomConnected(int statusCode, Room room){
			onEvent( ROOM_CONNECTED , _serializeRoom(room) , statusCode);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomCreated(int statusCode, Room room){
			trace("onRoomCreated");
			trace( _serializeRoom( room ));
			onEvent( ROOM_CREATED , _serializeRoom(room) , statusCode);
		}


		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onConnectedToRoom( Room room ){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void  onDisconnectedFromRoom(Room room){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerDeclined(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerInvitedToRoom(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerJoined(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeerLeft(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersConnected(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onPeersDisconnected(Room room, List<String> participantIds){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomAutoMatching(Room room){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onRoomConnecting(Room room){

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onInvitationReceived( Invitation invit ){
			trace("onInvitationReceived ::: "+invit);
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
			JSONObject o = new JSONObject( );
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
			ArrayList<String> players = extras.getStringArrayList( GamesClient.EXTRA_PLAYERS );
			trace("players ::: "+players);
			onEvent( INVITE_USERS , players.toString( ) , 0 );

			/*
			Bundle autoMatchCriteria = null;
        		int minAutoMatchPlayers = data.getIntExtra(GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, 0 );
        		int maxAutoMatchPlayers = data.getIntExtra(GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, 0 );
			*/

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
			 PlayHelper.getInstance( ).getGamesClient( ).createRoom( _room.build( ) );
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