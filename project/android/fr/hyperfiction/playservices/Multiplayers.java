package fr.hyperfiction.playservices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

	private static final int ID_INVITE_INTENT = 5004;

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

			RoomConfig.Builder 	r = RoomConfig.builder( listener );
        						r.setMessageReceivedListener( listener );
        						r.setRoomStatusUpdateListener( listener );
        						r.setAutoMatchCriteria(autoMatchCriteria);

        		PlayHelper.getInstance( ).getGamesClient( ).createRoom( r.build( ) );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void invite( int iMin_opponents , int iMax_opponents ){
			trace("invite");

			Intent intent = PlayHelper.getInstance( ).getGamesClient().getSelectPlayersIntent( iMin_opponents , iMax_opponents );
			GameActivity.getInstance( ).startActivityForResult(intent, ID_INVITE_INTENT );

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