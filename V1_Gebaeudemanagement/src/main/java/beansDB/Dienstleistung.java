package beansDB;

public class Dienstleistung {
	private String name;
    private String beschreibung;
    private String haeufigkeit;
    private int preis;
    private int dln_id;
    private int dma_id;
    private int ls_id;
	
	public int getDln_id() {
		return dln_id;
	}

	public void setDln_id(int dln_id) {
		this.dln_id = dln_id;
	}

	public int getDma_id() {
		return dma_id;
	}

	public void setDma_id(int dma_id) {
		this.dma_id = dma_id;
	}

	public int getLs_id() {
		return ls_id;
	}

	public void setLs_id(int ls_id) {
		this.ls_id = ls_id;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

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