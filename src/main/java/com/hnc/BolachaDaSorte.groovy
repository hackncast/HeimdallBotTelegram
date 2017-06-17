package com.hnc

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
			print(e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

}
