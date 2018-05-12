package beansDB;

import java.util.List;

public class Dezernatmitarbeiter {
	private String vorname;
    private String nachname;
    private String username;
    private String passwort;
    private String fachrolle = "Dezernatmitarbeiter";
    private List<Auftrag> auftraegeList;
    private int id;
    
    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

	/**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
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
    
    public List<Auftrag> getAuftraegeList() {
		return auftraegeList;
	}

	public void setAuftraegeList(List<Auftrag> auftraegeList) {
		this.auftraegeList = auftraegeList;
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