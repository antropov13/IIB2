package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.*;


import beansDB.Fachrolle;
import beansDB.Gebaeude;
import beansDB.Leistungsspektren;
import manage.start;

// CRUD = Create, Retrieve, Update, Delete
public class DBManager {

	
	private static String datenbankname = "gebaeudemanagement";

	private static java.sql.Connection getDBConnection(String dbName) throws ClassNotFoundException, SQLException {
	    	
	    Connection conn = null;
	    String p = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //das ist notwendig wegen Fehler "time zone"
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + p, "root", "0000");    		
		    		
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		    	
		return conn;
	}
	    
	public static List<Fachrolle> getUser(String tabelle) throws ClassNotFoundException, SQLException {
	    	
		String sql = "SELECT * FROM " +tabelle +" ;";
    	ResultSet r = query(sql);	        
       
        List<Fachrolle> frList = new ArrayList<Fachrolle>();
        if (tabelle.equals("Dezernatmitarbeiter")) {
	        while (r.next()) {
	        	Fachrolle fr = new Fachrolle();
	            fr.setId(r.getInt(1));
	            fr.setNachname(r.getString(2));
	            fr.setVorname(r.getString(3));
	            fr.setUsername(r.getString(4));
	          
	            fr.setFachrolle(tabelle);
	            frList.add(fr);
	        }
        }
        else {
	        while (r.next()) {
	        	Fachrolle fr = new Fachrolle();
	            fr.setId(r.getInt(1));
	            fr.setFirmaname(r.getString(2));
	            fr.setUsername(r.getString(3));
	          
	            fr.setFachrolle(tabelle);
	            frList.add(fr);
	        }
        }
        return frList;    
	}
	
	public Fachrolle getUser(String sql, String tabelle) throws ClassNotFoundException, SQLException {

		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Fachrolle fr = null ;
		if (tabelle.equals("Dezernatmitarbeiter")) {
	        while (r.next()) {
	        	fr = new Fachrolle();
	            fr.setId(r.getInt(1));
	            fr.setNachname(r.getString(2));
	            fr.setVorname(r.getString(3));
	            fr.setUsername(r.getString(4));
	            fr.setFachrolle(tabelle);
	        }
        }
        else {
	        while (r.next()) {
	        	fr = new Fachrolle();
	            fr.setId(r.getInt(1));
	            fr.setFirmaname(r.getString(2));
	            fr.setUsername(r.getString(3));
	            fr.setFachrolle(tabelle);
	        }
        }
		con.close(); // Very important!
		return fr;
	}
	
	public List<Leistungsspektren> getLeistungen(String sql) throws ClassNotFoundException, SQLException {
		List<Leistungsspektren> lsList = new ArrayList<Leistungsspektren>();
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Leistungsspektren ls = null ;
	    while (r.next()) {
	    	ls = new Leistungsspektren();
	        ls.setId(r.getInt(1));
	        ls.setName(r.getString(2));
	        ls.setBescheibung(r.getString(3));
	        ls.setPreis(r.getInt(4));
	        lsList.add(ls);
	        
	    }
		con.close(); // Very important!
		return lsList;
	}
	
	public int setLeistungen(String sql) throws ClassNotFoundException, SQLException {
		Integer id=-1;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		
		stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()){
		    id=rs.getInt(1);
		}
		System.out.println("id " + id);

		con.close(); // Very important!
		return id;
	}
	
	public void update(String sql) throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		con.close(); // Very important!
	}
	    
	public static ResultSet query(String sql) throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection(datenbankname);
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);	       
	    return rs;
	}
}