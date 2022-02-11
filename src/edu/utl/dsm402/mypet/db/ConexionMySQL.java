package edu.utl.dsm402.mypet.db;



import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;


public class ConexionMySQL

{
Connection conn;
public Connection open() throws Exception

{

String user="root";

String password="";

String url="jdbc:mysql://localhost:3306/mypet";
Class.forName("com.mysql.jdbc.Driver"); 
conn=DriverManager.getConnection(url, user, password); 
return conn;

}

public void close()
{
try
{
conn.close();
}
catch (SQLException ex)
{
ex.printStackTrace();

}

}

}
