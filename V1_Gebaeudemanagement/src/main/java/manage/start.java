package manage;


import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import beansDB.Fachrolle;

public class start {
	private static Scanner sc;
	private static java.sql.Connection con;
	private static String datenbankname = "gebaeudemanagement";
	
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
    	 sc = new Scanner (System.in) ;
    	 menu();
    }
    
	
	private static void menu() throws SQLException, ClassNotFoundException
    {
        int eingabe=0;
        System.out.println("Willkommen im Benutzer-Interface. Was moechten Sie machen?");
        System.out.println("1 Vorhandene Dienstleister ansehen");
        System.out.println("2 Vorhandene Dezernatmitarbeiter ansehen");
        
        
        try{
            eingabe = Integer.parseInt(sc.nextLine());
        }catch(Exception e)
        {
            System.out.println("Ungueltige Eingabe. Bitte erneut eingeben.");
            menu();
        }
        
        switch(eingabe){
            default:
                System.out.println("Ungültige Eingabe. Bitte erneut eingeben.");
                menu();
                break;
                
            case 1:
                List<Fachrolle> dienstleister = DBManager.getUser("Dienstleister"); 
                datenAusgabe(dienstleister);
                break;
                
            case 2:
            	List<Fachrolle> dezernatmitarbeiter = DBManager.getUser("Dezernatmitarbeiter"); 
            	datenAusgabe(dezernatmitarbeiter);
                break;
                             
            case 7:
                break;
        }   
    }
	
    public static void datenAusgabe(List<Fachrolle> fachrolle) throws ClassNotFoundException, SQLException {
		String result;
	    for (Fachrolle f : fachrolle) {
	    		if (f.getFachrolle().equals("Dezernatmitarbeiter"))
	        	result = MessageFormat.format("ID = {0}, Name = {1}, Vorname = {2}, Username = {3}, Passwort = {4}", 
	        			f.getId(), f.getNachname(), f.getVorname(), f.getUsername(), f.getPass());
	    		else result = MessageFormat.format("ID = {0}, Firmaname = {1}, Username = {2}, Passwort = {3}", 
	        			f.getId(), f.getFirmaname(), f.getUsername(), f.getPass());
	        	System.out.println(result);
			
		}
		weitermachen();
		
	}
    
  	private static void weitermachen() throws SQLException, ClassNotFoundException
    {
        System.out.println("Machten Sie weitere Aktionen unternehmen? (y/n)");
        String weiter = sc.nextLine();
        if(weiter.equals("y")||weiter.equals("Y"))
            menu();
        else if(!weiter.equals("n")&&!weiter.equals("N"))
        {
            System.out.println("Ungueltige Eingabe. Bitte erneut eingeben.");
            weitermachen();
        }
            
    }
}