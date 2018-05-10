package beansDB;

import java.util.List;

public class Leistungsspektrum {
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
    
    public Dienstleistung getDienstleistungen(int id) {
    	for (Dienstleistung d : dienstleistungen)
    	{
    		if (d.getId()==id) return d;
    	}
        return null;
    }
    
    public void delDienstleistungen(Dienstleistung d) {
    	dienstleistungen.remove(d);
    }

    public void setDienstleistungen(List<Dienstleistung> dienstleistungen) {
        this.dienstleistungen = dienstleistungen;
    }
    
    public void addDienstleistung(Dienstleistung dienstleistung) {
        this.dienstleistungen.add(dienstleistung);
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