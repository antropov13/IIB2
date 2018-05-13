package beansDB;

import java.util.List;

public class Dienstleister {
    private String firmaname;
    private String username;
    private String passwort;
    private String fachrolle = "Dienstleister";
    private List<Leistungsspektrum> leistungsspektrumList;
    private List<Auftrag> auftraegeList;
    private int id;
    
    /**
     * @return the Firmaname
     */
    public String getFirmaname() {
        return firmaname;
    }

    /**
     * @param vorname the Firmaname to set
     */
    public void setFirmaname(String firmaname) {
        this.firmaname = firmaname;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the passwort
     */
    public String getPass() {
        return passwort;
    }

    /**
     * @param passwort the passwort to set
     */
    public void setPass(String passwort) {
        this.passwort = passwort;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fachrolle
     */
    public String getFachrolle() {
        return fachrolle;
    }
    
    /**
     * @return the Leistungsspektren
     */
    public List<Leistungsspektrum> getLeistungsspektren() {
        return leistungsspektrumList;
    }
    
    public Leistungsspektrum getLeistungsspektren(int id) {
    	for (Leistungsspektrum l : leistungsspektrumList)
    	{
    		if (l.getId()==id) return l;
    	}
        return null;
    }
    
    public void delLeistungsspektren(Leistungsspektrum ls) {
    	leistungsspektrumList.remove(ls);
    }

    /**
     * @param fachrolle the Leistungsspektren to set
     */
    public void setLeistungsspektren(List<Leistungsspektrum> leistungsspektren) {
        this.leistungsspektrumList = leistungsspektren;
    }
    
    public void setLeistungsspektrum(Leistungsspektrum leistungsspektren) {
        this.leistungsspektrumList.add(leistungsspektren);
    }
    
    /**
     * @param fachrolle the Leistungsspektren to set
     */
    public void setAuftraegeList(List<Auftrag> auftraege) {
        this.auftraegeList = auftraege;
    }
    
    public List<Auftrag> getAuftraegeList() {
        return auftraegeList;
    }
    
    public Auftrag getAuftrag(int id) {
        for (Auftrag a : auftraegeList)
        {
        	if (a.getId()==id) return a;
        }
        return null;
    }
    
    public void delAuftrag(int id) {
        for (Auftrag a : auftraegeList)
        {
        	if (a.getId()==id) 
        	{
        		auftraegeList.remove(a);
        		break;
        	}
        }
    }
    
}