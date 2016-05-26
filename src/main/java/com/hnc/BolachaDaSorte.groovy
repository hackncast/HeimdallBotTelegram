package com.hnc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BolachaDaSorte {

	public static String abrirPacote() {

		try {
			StringBuilder sb = new StringBuilder();
			Process process = Runtime.getRuntime().exec( "fortune" );

			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader( is );
			BufferedReader br = new BufferedReader( isr );
			String line;

			while( ( line = br.readLine() ) != null ) {
				sb.append( line );
				sb.append( "\n" );
			}

			return sb.toString();
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return "";
	}

}
