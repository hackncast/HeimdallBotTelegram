package com.hnc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BolachaDaSorte {

	public static String abrirPacote() {

		try {
			def sb =  StringBuilder.newInstance()
			Process process = Runtime.runtime.exec( "fortune" )

			process.getInputStream().eachLine { line ->
				sb << line
				sb << "\n"
			}

			return sb.toString()
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return "";
	}

}
