package beansDB;

import java.util.List;

public class Dienstleister {
    private String firmaname;
    private String username;
    private String passwort;
    private String fachrolle = "Dienstleister";
    private List<Leistungsspektren> leistungsspektren;
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
    public List<Leistungsspektren> getLeistungsspektren() {
        return leistungsspektren;
    }
    
    public Leistungsspektren getLeistungsspektren(int id) {
    	for (Leistungsspektren l : leistungsspektren)
    	{
    		if (l.getId()==id) return l;
    	}
        return null;
    }

    /**
     * @param fachrolle the Leistungsspektren to set
     */
    public void setLeistungsspektren(List<Leistungsspektren> leistungsspektren) {
        this.leistungsspektren = leistungsspektren;
    }
}