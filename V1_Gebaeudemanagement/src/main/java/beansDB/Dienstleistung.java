package beansDB;

public class Dienstleistung {
	private int id;
	private String name;
    private String beschreibung;
    private String haeufigkeit;
    private int preis;
    private int dln_id;
    private int dma_id;
	
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
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    /**
     * @return the nachname
     */
    public String getHaeufigkeit() {
        return haeufigkeit;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setHaeufigkeit(String haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
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
    public int getDmaId() {
        return dma_id;
    }

    /**
     * @param id the id to set
     */
    public void setDmaId(int id) {
        this.dma_id = id;
    }
 
	
}