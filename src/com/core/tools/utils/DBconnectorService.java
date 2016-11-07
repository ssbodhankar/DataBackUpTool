package com.core.tools.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBconnectorService
{
	private static Connection conn = null;

	/**
	 * This method is used to query the database to retrieve the information
	 * form the database
	 * 
	 * @param queryStatement
	 * @return
	 * @throws SQLException
	 */

	public static ResultSet queryDB(String queryStatement) throws SQLException
	{
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			Statement selectStatement = conn.createStatement();
			rs = selectStatement.executeQuery(queryStatement);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
//				conn.close();
			}
		}

		return rs;
	}

	/**
	 * This method is used to insert data in the database. The input is the
	 * query string.
	 * 
	 * @param insertQuery
	 * @return rowsAdded
	 * @throws SQLException 
	 */
	public static int insertData(String insertQuery) throws SQLException

	{
		int rowsAdded = 0;
		try
		{
			conn = getConnection();
			Statement insertStatement = getConnection().createStatement();
			rowsAdded = insertStatement.executeUpdate(insertQuery);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		return rowsAdded;

	}

	/**
	 * returns a Database connection Object.
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException
	{
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", "root");
		connectionProps.put("password", "Welcome123");
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://" + "localhost" + ":" + "3306" + "/", connectionProps);
		return conn;
	}

	// public static void main(String[] args)
	// {
	// Properties connectionProps = new Properties();
	// connectionProps.put("user", "root");
	// connectionProps.put("password", "Welcome123");
	// String selectQuery = "SELECT * FROM `trail`.`nameofstudents`;";
	// String insertQuery = "INSERT into `trail`.`nameofstudents`
	// (`id`,`LastName`, `FirstName`, `Age`) VALUES ('3','Chandak', 'Shruti',
	// '30')";
	// // if (this.dbms.equals("mysql"))
	// // {
	// ResultSet rs = queryDB(selectQuery);
	// try
	// {
	// ResultSetMetaData metaData = rs.getMetaData();
	// } catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	// System.out.println("DB Query Done");
	// int rows = insertData(insertQuery);
	// System.out.println(insertQuery + " is done");
	//
	// System.out.println("Connected to database");
	// }
}
