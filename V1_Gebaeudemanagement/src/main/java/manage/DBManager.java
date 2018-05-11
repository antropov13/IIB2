package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.*;

import beansDB.Auftrag;
import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
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
	    /*
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
	}*/
	
	public Dienstleister getUserDLR(String sql) throws ClassNotFoundException, SQLException {

		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		
		Dienstleister dlr = null ;
	    while (r.next()) {
	    	dlr = new Dienstleister();
	    	dlr.setId(r.getInt(1));
	    	dlr.setFirmaname(r.getString(2));
	    	dlr.setUsername(r.getString(3));
	    }
		con.close(); // Very important!
		return dlr;
	}
	
	public Dezernatmitarbeiter getUserDMA(String sql) throws ClassNotFoundException, SQLException {

		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		
		Dezernatmitarbeiter dma = null ;
	    while (r.next()) {
	    	dma = new Dezernatmitarbeiter();
	    	dma.setId(r.getInt(1));
	    	dma.setNachname(r.getString(2));
	    	dma.setVorname(r.getString(3));
	    	dma.setUsername(r.getString(4));
	    }
		con.close(); // Very important!
		return dma;
	}
	
	public List<Leistungsspektrum> getLeistungsspektren(String sql) throws ClassNotFoundException, SQLException {
		List<Leistungsspektrum> lsList = new ArrayList<Leistungsspektrum>();
		List<Dienstleistung> dlnList = new ArrayList<Dienstleistung>();
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		int nr = 0;
		int id_spektren_new = 0;
		Leistungsspektrum ls = null ;
		
		boolean val = r.next();
		if(val==false){
			return lsList;
		}
		else {
		    while (val) {
		    	nr++;
	    		ls = new Leistungsspektrum();
	    		//ls.setName("Leistungsspektrum " + nr);
	    		ls.setName(String.valueOf(r.getInt(1)));
	    		ls.setId(r.getInt(1));
	    		ls.setDienstleistungen(dlnList);
	    		lsList.add(ls);
		    	val=r.next();
		    	
		    }
			con.close(); // Very important!
		}
		
		return lsList;
	
	}
	
	public List<Leistungsspektrum> getLeistungen(String sql, Dienstleister dlr) throws ClassNotFoundException, SQLException {
		List<Leistungsspektrum> lsList = dlr.getLeistungsspektren();
		List<Dienstleistung> dlnList = new ArrayList<Dienstleistung>();
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		
		Leistungsspektrum ls = null ;
		Dienstleistung dln = null ;
		int id_spektren = 0;
		int id_spektren_new = 0;
		
			boolean val = r.next();
			if(val==false){
				return lsList;
			}
			else {
				while (val) {
			    	id_spektren_new = r.getInt(1);
			    	if (id_spektren_new!=id_spektren)
			    	{
			    		if (id_spektren!=0) {
			    			ls.setDienstleistungen(dlnList);
			    			dlnList = new ArrayList<Dienstleistung>();
			    		}
			    		ls = dlr.getLeistungsspektren(id_spektren_new);
			    	}
			    	dln = new Dienstleistung();
			    	dln.setDlnId(r.getInt(2));
			    	dln.setName(r.getString(3));
			    	dln.setBescheibung(r.getString(4));
			    	dln.setHaeufigkeit(r.getString(5));
			    	dln.setPreis(r.getInt(6));
			    	//dln.setId(r.getInt(7));
			    	dln.setDmaId(r.getInt(8));
			    	dlnList.add(dln);
			    	id_spektren = id_spektren_new;
			    	val=r.next();
			    	
			    }
			    ls.setDienstleistungen(dlnList);
				con.close();
		}
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
	
	public List<Dienstleistung> getDienstleistungen(String sql) throws ClassNotFoundException, SQLException {
		List<Dienstleistung> dlnList = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Dienstleistung dln = null ;
		boolean val = r.next();
		if(val==false){
			return dlnList;
		}
		else {
			dlnList = new ArrayList<Dienstleistung>();
		    while (val) {
		    	dln = new Dienstleistung();
		    	dln.setDlnId(r.getInt(1));
		    	dln.setName(r.getString(2));
		    	dln.setBescheibung(r.getString(3));
		    	dln.setHaeufigkeit(r.getString(4));
		    	dln.setDmaId(r.getInt(5));
		    	dlnList.add(dln);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return dlnList;
	}
	
	public int setSpektrum(String sql) throws ClassNotFoundException, SQLException {
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
	
	public int getSpektrum(String sql) throws ClassNotFoundException, SQLException {
		Integer id=-1;
		Connection con = getDBConnection(datenbankname);
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);	
		if (rs.next()){
		    id=rs.getInt(1);
		}
		System.out.println("id " + id);

		con.close(); // Very important!
		return id;
	}
	
	public List<Auftrag> getAuftraege(String sql) throws ClassNotFoundException, SQLException {
		List<Auftrag> auftraegeList = new ArrayList<Auftrag>();
		//List<Dienstleistung> dlnList = new ArrayList<Dienstleistung>();
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		Connection con2 = getDBConnection(datenbankname);
		Statement stmt2 = con2.createStatement();
		Connection con3 = getDBConnection(datenbankname);
		Statement stmt3 = con3.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		int nr = 0;
		int id_spektren_new = 0;
		Auftrag at = null ;
		String sql2 = "";
		String sql3 = "";
		String dma = "";
		boolean val = r.next();
		if(val==false){
			return auftraegeList;
		}
		else {
		    while (val) {
		    	nr++;
	    		at = new Auftrag();
	    		at.setId(r.getInt(1));
	    		at.setDma_idl(r.getInt(2));
	    		at.setDlr_id(r.getInt(3));
	    		at.setDate(r.getString(4));
	    		at.setStatus(r.getString(5));
	    		sql2 = "SELECT dma_name, dma_vorname from dezernatmitarbeiter where dma_id = " + r.getInt(2);
	    		ResultSet r2 = stmt2.executeQuery(sql2);
	    		while (r2.next())
	    		{
	    			dma = r2.getString(1) + " " + r2.getString(2);
	    		}
	    		
	    		sql3 = "SELECT * from gebaeude where geb_id = " + r.getInt(6);
	    		at.setGebaeude(getGebaeude(sql3));
	    		at.setAuftragsersteller(dma);
	    		auftraegeList.add(at);
		    	val=r.next();
		    	
		    }
			con.close(); // Very important!
		}
		
		return auftraegeList;
	
	}
	
	public Gebaeude getGebaeude(String sql) throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection(datenbankname);
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);
	    
	    Gebaeude gebaeude = new Gebaeude();
		while (rs.next())
		{
			gebaeude.setId(rs.getInt(1));
			gebaeude.setStrasse(rs.getString(2));
			gebaeude.setHausnummer(rs.getString(3));
			gebaeude.setOrt(rs.getString(4));
			gebaeude.setPlz(rs.getInt(5));
			gebaeude.setDma_id(rs.getInt(6));
		}
	    return gebaeude;
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