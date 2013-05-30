package fr.hyperfiction.playservices;

import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.PlusShare.Builder;

import fr.hyperfiction.playservices.PlayHelper;

import org.haxe.nme.GameActivity;

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
			Intent i = new PlusShare.Builder( GameActivity.getInstance( ) )
		          .setType("text/plain")
		          .setText( sText )
		          .setContentUrl(Uri.parse( sURL ))
		          .getIntent();

		     GameActivity.getInstance( ).startActivityForResult( i , 0 );
		}

	// -------o protected



	// -------o misc

}