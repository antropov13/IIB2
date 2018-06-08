package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.*;

import beansDB.Auftrag;
import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
import beansDB.LnAuftragDln;
import beansDB.Lnlspdln;
import beansDB.LnDokumentiert;
import beansDB.Maengel;
import manage.start;
import beansDB.Gebaeude;

// CRUD = Create, Retrieve, Update, Delete
public class DBManager {

	
	private static String datenbankname = "IIB2_UE1_Gruppe09";

	private static java.sql.Connection getDBConnection(String dbName) throws ClassNotFoundException, SQLException {
	    	
	    Connection conn = null;
	    String p = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //das ist notwendig wegen Fehler "time zone"
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + p, "root", "Caramelo2");    		
		    		
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
	
	public List<Dienstleister> getUserDLRList(String sql) throws ClassNotFoundException, SQLException {
		List<Dienstleister> dlrList = new ArrayList<Dienstleister>();
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		
		Dienstleister dlr = null ;
		boolean val = r.next();
		if(val==false){
			return dlrList;
		}
		else {
		    while (val) {
	    	dlr = new Dienstleister();
	    	dlr.setId(r.getInt(1));
	    	dlr.setFirmaname(r.getString(2));
	    	dlr.setUsername(r.getString(3));
	    	dlrList.add(dlr);
	    	val=r.next();
		    }
		}
		con.close(); // Very important!
		return dlrList;
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
		Leistungsspektrum ls = null ;
		
		boolean val = r.next();
		if(val==false){
			return lsList;
		}
		else {
		    while (val) {
	    		ls = new Leistungsspektrum();
	    		ls.setName(String.valueOf(r.getInt(1)));
	    		ls.setId(r.getInt(1));
	    		ls.setDlrId(r.getInt(2));
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
			    	dln.setBeschreibung(r.getString(4));
			    	dln.setHaeufigkeit(r.getString(5));
			    	dln.setPreis(r.getInt(6));
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
		    	dln.setBeschreibung(r.getString(3));
		    	dln.setHaeufigkeit(r.getString(4));
		    	dln.setDmaId(r.getInt(5));
		    	dlnList.add(dln);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return dlnList;
	}
	
	public Dienstleistung getDienstleistung(String sql) throws ClassNotFoundException, SQLException {
		Dienstleistung dln = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		boolean val = r.next();
		if(val==false){
			return dln;
		}
		else {
		    while (val) {
		    	dln = new Dienstleistung();
		    	dln.setDlnId(r.getInt(1));
		    	dln.setName(r.getString(2));
		    	dln.setBeschreibung(r.getString(3));
		    	dln.setHaeufigkeit(r.getString(4));
		    	dln.setDmaId(r.getInt(5));
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return dln;
	}
	
	public List<Lnlspdln> getLnlspdln(String sql) throws ClassNotFoundException, SQLException {
		List<Lnlspdln> lnlspdlnList = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Lnlspdln ln = null ;
		boolean val = r.next();
		if(val==false){
			return lnlspdlnList;
		}
		else {
			lnlspdlnList = new ArrayList<Lnlspdln>();
		    while (val) {
		    	ln = new Lnlspdln();
		    	ln.setId(r.getInt(1));
		    	ln.setLld_dln_id(r.getInt(2));
		    	ln.setLld_lsp_id(r.getInt(3));
		    	ln.setLld_preis(r.getInt(4));
		    	lnlspdlnList.add(ln);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return lnlspdlnList;
	}
	
	public int setAuftrag(String sql) throws ClassNotFoundException, SQLException {
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
	public int setMaengel(String sql) throws ClassNotFoundException, SQLException {
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
		Connection conDMA = getDBConnection(datenbankname);
		Statement stmtDMA = conDMA.createStatement();
		Connection conDLR = getDBConnection(datenbankname);
		Statement stmtDLR = conDLR.createStatement();
		Connection conGEB = getDBConnection(datenbankname);
		Statement stmtGEB = conGEB.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		int nr = 0;
		int id_spektren_new = 0;
		Auftrag at = null ;
		String sqlDMA = "", sqlDLR  = "", sqlGeb = "";
		String dma  = "", dlr = "";
		
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
	    		at.setDate(r.getDate(6));
	    		at.setStatus(r.getString(7));
	    		sqlDMA = "SELECT dma_name, dma_vorname from dezernatmitarbeiter where dma_id = " + r.getInt(2);
	    		ResultSet rDMA = stmtDMA.executeQuery(sqlDMA);
	    		while (rDMA.next())
	    		{
	    			dma = rDMA.getString(1) + " " + rDMA.getString(2);
	    		}
	    		at.setAuftragsersteller(dma);
	    		
	    		sqlDLR = "SELECT dlr_firmaname from dienstleister where dlr_id = " + r.getInt(3);
	    		ResultSet rDLR = stmtDLR.executeQuery(sqlDLR);
	    		while (rDLR.next())
	    		{
	    			dlr = rDLR.getString(1);
	    		}
	    		at.setDienstleister(dlr);
	    		
	    		sqlGeb = "SELECT * from gebaeude where geb_id = " + r.getInt(8);
	    		at.setGebaeude(getGebaeude(sqlGeb));
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
	
	public List<Gebaeude> getGebaeudeList(String sql) throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection(datenbankname);
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);
	    List<Gebaeude> gebaeudeList = new ArrayList<Gebaeude>();
	    Gebaeude gebaeude = null;
		boolean val = rs.next();
		if(val==false){
			return null;
		}
		else {
		    while (val) {
		    gebaeude = new Gebaeude();
		    gebaeude.setId(rs.getInt(1));
			gebaeude.setStrasse(rs.getString(2));
			gebaeude.setHausnummer(rs.getString(3));
			gebaeude.setOrt(rs.getString(4));
			gebaeude.setPlz(rs.getInt(5));
			gebaeude.setDma_id(rs.getInt(6));
			gebaeudeList.add(gebaeude);
			val=rs.next();
		    }
		}
	    return gebaeudeList;
	}
	/*
	public List<LnAuftragDln> getlnauftragdln(String sql) throws ClassNotFoundException, SQLException {
		List<LnAuftragDln> lnauftragdln = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		LnAuftragDln dln = null ;
		boolean val = r.next();
		if(val==false){
			return lnauftragdln;
		}
		else {
			lnauftragdln = new ArrayList<LnAuftragDln>();
		    while (val) {
		    	dln = new LnAuftragDln();
		    	dln.setId(r.getInt(1));
		    	dln.setDienstleistung_id(r.getInt(2));
		    	dln.setAuftrag_id(auftrag_id);(r.getString(3));
		    	dln.setHaeufigkeit(r.getString(4));
		    	dln.setDmaId(r.getInt(5));
		    	lnauftragdln.add(dln);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return lnauftragdln;
	}*/
	
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


	public List<Gebaeude> getGeb(String sql) throws ClassNotFoundException, SQLException {
		List<Gebaeude> gebList = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Gebaeude geb = null ;
		boolean val = r.next();
		if(val==false){
			return gebList;
		}
		else {
			gebList = new ArrayList<Gebaeude>();
		    while (val) {
		    	geb = new Gebaeude();
		    	geb.setId(r.getInt(1));
		    	geb.setStrasse(r.getString(2));
		    	geb.setHausnummer(r.getString(3));
		    	geb.setOrt(r.getString(4));
		    	geb.setPlz(r.getInt(5));
		    	geb.setDma_id(r.getInt(6));
		    	gebList.add(geb);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return gebList;
	}

	public List<LnDokumentiert> getBerichte(String sql) throws ClassNotFoundException, SQLException {
		List<LnDokumentiert> berichte = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		LnDokumentiert b = null ;
		boolean val = r.next();
		if(val==false){
			return berichte;
		}
		else {
			berichte = new ArrayList<LnDokumentiert>();
		    while (val) {
		    	b = new LnDokumentiert();
		    	b.setId(r.getInt(1));
		    	b.setDma_id(r.getInt(2));
		    	b.setMgl_id(r.getInt(3));
		    	b.setDate(r.getDate(4));
		    	b.setTitel(r.getString(5));
		    	b.setBeschreibung(r.getString(6)); 
		    	berichte.add(b);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return berichte;
	}

	public List<Maengel> getMaengel(String sql) throws ClassNotFoundException, SQLException{
		List<Maengel> maengel = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
 		Maengel m = null ; 
		boolean val = r.next();
		if(val==false){
			return maengel;
		}
		else {
			maengel = new ArrayList<Maengel>();
		    while (val) {
		    	m = new Maengel();
		    	m.setId(r.getInt(1));
		    	m.setAuftrag(r.getInt(2));
		    	maengel.add(m);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return maengel;
	}

	public List<Dienstleister> getDienstleister(String sql) throws ClassNotFoundException, SQLException{
		List<Dienstleister> dlr = null;
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		Dienstleister d = null ;
		boolean val = r.next();
		if(val==false){
			return dlr;
		}
		else {
			dlr = new ArrayList<Dienstleister>();
		    while (val) {
		    	d = new Dienstleister();
		    	d.setId(r.getInt(1));
		    	d.setFirmaname(r.getString(2));
		    	dlr.add(d);
		    	val=r.next();
		    }
			con.close(); // Very important!
		}
		return dlr;
	}

	public int getLast(String sql) throws SQLException, ClassNotFoundException {
		Connection con = getDBConnection(datenbankname);
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(sql);
		boolean val = r.next();
		int ret = -1;
		if(val== true) {
			ret = r.getInt(1);
			
		}
		con.close();
		return ret;
	}

	public int getMaengelID(String sql) throws ClassNotFoundException, SQLException {
		List<LnDokumentiert> mL = getBerichte(sql);
		int id = -1;
		if(mL.size() == 1)
			id = mL.get(0).getMgl_id();

		return id;
	}

}