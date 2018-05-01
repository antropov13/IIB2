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
        System.out.println("1 Vorhandene Bauleiter ansehen");
        System.out.println("2 Daten zu existierenden Bauleiter Ã¤ndern");
        System.out.println("3 Neuen Bauleiter eingeben");
        System.out.println("4 Vorhandene Gutachter ansehen");
        System.out.println("5 Daten zu existierenden Gutachter Ã¤ndern");
        System.out.println("6 Neuen Gutachter eingeben");
        System.out.println("7 Programm verlassen");
        
        List<Fachrolle> dienstleister = DBManager.getUser("Dienstleister"); 
        datenAusgabe(dienstleister);
        
        /*
        try{
            eingabe = Integer.parseInt(sc.nextLine());
        }catch(Exception e)
        {
            System.out.println("Ungueltige Eingabe. Bitte erneut eingeben.");
            menu();
        }*/
        
        
        /*
        switch(eingabe){
            default:
                System.out.println("Ungültige Eingabe. Bitte erneut eingeben.");
                menu();
                break;
                
            case 1:
                List<Fachrolle> dienstleister = DBManager.getUser("gebaeudemanagement.dienstleister"); 
                datenAusgabe(dienstleister);
                break;
                
            case 4:
            	List<Fachrolle> dezernatmitarbeiter = DBManager.getUser("gebaeudemanagement.dezernatmitarbeiter"); 
            	datenAusgabe(dezernatmitarbeiter);
                break;
                             
            case 7:
                break;
        }   */
    }
	
    public static void datenAusgabe(List<Fachrolle> fachrolle) throws ClassNotFoundException, SQLException {
		String result;
	    for (Fachrolle f : fachrolle) {
	        	result = MessageFormat.format("ID = {0}, Name = {1}, Vorname = {2}, Username = {3}, Passwort = {4}", 
	        			f.getId(), f.getNachname(), f.getVorname(), f.getUsername(), f.getPasswort());
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