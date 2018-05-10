package beansDB;

public class Dienstleistung {
	private String name;
    private String beschreibung;
    private int preis;
    private int dln_id;
    private int id;
	
	/**
     * @return the vorname
     */
    public String getName() {
        return name;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nachname
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setBescheibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }
    
    /**
     * @return the id
     */
    public int getDlnId() {
        return dln_id;
    }

    /**
     * @param id the id to set
     */
    public void setDlnId(int id) {
        this.dln_id = id;
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
	
}