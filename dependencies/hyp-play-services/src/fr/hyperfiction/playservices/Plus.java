package fr.hyperfiction.playservices;

import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.PlusShare.Builder;
import com.google.android.gms.plus.PlusClient;

import fr.hyperfiction.googleplayservices.HypPlayServices;
import fr.hyperfiction.playservices.PlayHelper;


/**
 * ...
 * @author shoe[box]
 */

public class Plus{

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private Plus( ){

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void basicSharing( String sText , String sURL ){
			Intent i = new PlusShare.Builder( HypPlayServices.mainActivity )
		          .setType("text/plain")
		          .setText( sText )
		          .setContentUrl(Uri.parse( sURL ))
		          .getIntent();

		     HypPlayServices.mainActivity.startActivityForResult( i , 0 );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getFamily_name( ){
			return getPlusClient( ).getCurrentPerson( ).getName( ).getFamilyName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getFirst_name( ){
			return getPlusClient( ).getCurrentPerson( ).getName( ).getGivenName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getAccount_name( ){
			return getPlusClient( ).getAccountName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getDisplay_name( ){
			return getPlusClient( ).getCurrentPerson( ).getDisplayName( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getNick_name( ){
			return getPlusClient( ).getCurrentPerson( ).getNickname( );
		}

	// -------o protected

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private PlusClient getPlusClient( ){
			return PlayHelper.getInstance( ).getPlusClient( );
		}

	// -------o misc

}