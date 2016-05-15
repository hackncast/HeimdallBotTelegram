package com.hnc.db;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSQlite implements Closeable {

	private Connection connection;

	private ConexaoSQlite() throws ClassNotFoundException, SQLException {
		Class.forName( "org.sqlite.JDBC" );
		connection = DriverManager.getConnection( "jdbc:sqlite:hnc.db" );
	}

	public Connection getConnection() {
		return connection;
	}

	public static ConexaoSQlite getConexao() throws ClassNotFoundException, SQLException {
		return new ConexaoSQlite();
	}

	public void executeUpdate( String sql ) throws SQLException {
		Statement sta = connection.createStatement();
		sta.executeUpdate( sql );
	}

	public ResultSet executeQuery( String sql ) throws SQLException {
		Statement sta = connection.createStatement();
		ResultSet rs = sta.executeQuery( sql );
		return rs;
	}

	public PreparedStatement prepareStatement( String sql ) throws SQLException {
		PreparedStatement sta = connection.prepareStatement( sql );
		return sta;
	}

	public void executeUpdate( PreparedStatement sta ) throws SQLException {
		sta.executeUpdate();
	}

	public ResultSet executeQuery( PreparedStatement sta ) throws SQLException {
		try (ResultSet rs = sta.executeQuery()) {
			return rs;
		}
	}

	public void close() throws IOException {
		try {
			connection.close();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}

}
