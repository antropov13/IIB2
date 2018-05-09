package beansDB;

import java.util.List;

public class Leistungsspektren {
	private String name;
	private List<Dienstleistung> dienstleistungen;
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
    
    public List<Dienstleistung> getDienstleistungen() {
        return dienstleistungen;
    }

    public void setDienstleistungen(List<Dienstleistung> dienstleistungen) {
        this.dienstleistungen = dienstleistungen;
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