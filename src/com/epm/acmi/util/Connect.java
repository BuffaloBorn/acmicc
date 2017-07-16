package com.epm.acmi.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Connect Utility used to connect and execute queries with SQL server
 * 
 * @author Jay Hombal
 */
public class Connect {
	private static final Logger log = Logger.getLogger(Connect.class);


	// Constructor
	public Connect() {

	}


	/**
	 * Returns Connection to ACMI database
	 * 
	 * @return Connection
	 */
	public static java.sql.Connection getConnection() {
		java.sql.Connection con = null;

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/acmi");
			con = ds.getConnection();
			initCtx.close();
			} catch (Exception e) {
			log.error(e);
			log.error("Error Trace in getConnection() : " + e.getMessage());
		}
		return con;
	}


	/**
	 * Returns Connection to teamflowDB database
	 * 
	 * @return Connection
	 */
	public static java.sql.Connection getConnectionToTeamFlow() {
		java.sql.Connection con = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/teamflowdb");
			con = ds.getConnection();
		} catch (Exception e) {
			log.error(e);
			log.error("Error Trace in getConnection() : " + e.getMessage());
		}
		return con;
	}


	/**
	 * Returns Connection to Stellent database
	 * 
	 * @return Connection
	 */
	public static java.sql.Connection getConnectionToStellent() {
		java.sql.Connection con = null;

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/stellent");
			con = ds.getConnection();
			
		} catch (Exception e) {
			log.error(e);
			log.error("Error Trace in getConnectionToStellent() : " + e.getMessage());
		}
		return con;
	}


	/**
	 * Display the driver properties, database details
	 */

	public void displayDbProperties() {
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		java.sql.Connection con = null;
		try {
			con = getConnection();
			if (con != null) {
				dm = con.getMetaData();
				rs = dm.getCatalogs();
				while (rs.next()) {
					log.debug("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;

			} else
				log.debug("Error: No active Connection");
		} catch (Exception e) {
			log.error(e);
		}
		dm = null;
	}


	/**
	 * Display TeamFlowDB properties
	 */
	public void displayDbPropertiesTeamFlow() {
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		java.sql.Connection con = null;
		try {
			con = getConnectionToTeamFlow();
			if (con != null) {
				dm = con.getMetaData();
				rs = dm.getCatalogs();
				while (rs.next()) {
					log.debug("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;

			} else
				log.debug("Error: No active Connection");
		} catch (Exception e) {
			log.error(e);
		}
		dm = null;
	}


	/**
	 * Close SQL DB Connection
	 * 
	 * @param con
	 */
	public static void closeConnection(java.sql.Connection con) {
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {
			log.error(e);
		}
	}

	
	public static java.sql.Connection getConnectionTest(String dbname,String uid,String password)
	{
		java.sql.Connection connection = null;
		  try {

			  String driver = "com.newatlanta.jturbo.driver.Driver";
			  String url = "jdbc:JTurbo://SRV5045:1433/acmi";
			  log.debug("Getting Connection for database " + dbname);
			  log.debug(" user = "+ uid);
			  log.debug(" password = "+ password);
			  log.debug(" url = "+ url);
			  log.debug(" driver = "+ driver);
             
              Class.forName(driver);
              long t = System.currentTimeMillis();
              connection = DriverManager.getConnection(url, "sa", "");
              long t1 = System.currentTimeMillis();
              log.debug("Time for connection " + dbname + " " + (t1- t) +"  milliseconds");
              log.debug(" Getting Connection for database " + dbname + "done ");
	     } catch (Exception ex) {
             ex.printStackTrace();
          }
          return connection;
	}

	/**
	 * Close PreparedStatement
	 * 
	 * @param psmt
	 */
	public static void closePSMT(PreparedStatement psmt) {
		try {
			if (psmt != null)
				psmt.close();
			psmt = null;
		} catch (Exception e) {
			log.error(e);
		}
	}


	/**
	 * Close Statement
	 * 
	 * @param stmt
	 */
	public static void closeSTMT(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
			stmt = null;
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public static void closeResultSet(ResultSet rst) {
		try {
			if (rst!= null)
				rst.close();
			rst = null;
		} catch (Exception e) {
			log.error(e);
		}
	}
}
