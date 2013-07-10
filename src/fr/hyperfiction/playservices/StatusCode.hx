package fr.hyperfiction.playservices;

/**
 * ...
 * @author shoe[box]
 */

class StatusCode{

	private static inline var STATUS_NETWORK_ERROR_OPERATION_FAILED	: Int = 0x00000006;
	private static inline var STATUS_OK						: Int = 0x00000000;
	private static inline var STATUS_PARTICIPANT_NOT_CONNECTED		: Int = 0x00001b5b;
	private static inline var STATUS_REAL_TIME_CONNECTION_FAILED	: Int = 0x00001b58;
	private static inline var STATUS_REAL_TIME_INACTIVE_ROOM		: Int = 0x00001b5d;
	private static inline var STATUS_REAL_TIME_MESSAGE_FAILED		: Int = 0xffffffff;
	private static inline var STATUS_REAL_TIME_MESSAGE_SEND_FAILED	: Int = 0x00001b59;
	private static inline var STATUS_REAL_TIME_ROOM_NOT_JOINED		: Int = 0x00001b5c;

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
		static public function translate( i : Int ) : Status {

			return switch( i ){

				case STATUS_OK:
					OK;

				case STATUS_PARTICIPANT_NOT_CONNECTED:
					PARTICIPANT_NOT_CONNECTED;

				case STATUS_REAL_TIME_CONNECTION_FAILED:
					REAL_TIME_CONNECTION_FAILED;

				case STATUS_REAL_TIME_INACTIVE_ROOM:
					REAL_TIME_INACTIVE_ROOM;

				case STATUS_REAL_TIME_MESSAGE_FAILED:
					REAL_TIME_MESSAGE_FAILED;

				case STATUS_REAL_TIME_MESSAGE_SEND_FAILED:
					REAL_TIME_MESSAGE_SEND_FAILED;

				case STATUS_REAL_TIME_ROOM_NOT_JOINED:
					REAL_TIME_ROOM_NOT_JOINED;

				case STATUS_NETWORK_ERROR_OPERATION_FAILED:
					NETWORK_ERROR_OPERATION_FAILED;

				case _:


			};

		}

	// -------o protected



	// -------o misc

}

enum Status{
	OK;
	PARTICIPANT_NOT_CONNECTED;
	REAL_TIME_CONNECTION_FAILED;
	REAL_TIME_INACTIVE_ROOM;
	REAL_TIME_MESSAGE_FAILED;
	REAL_TIME_MESSAGE_SEND_FAILED;
	REAL_TIME_ROOM_NOT_JOINED;
	NETWORK_ERROR_OPERATION_FAILED;
}