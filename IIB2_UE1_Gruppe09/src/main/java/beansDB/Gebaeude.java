package beansDB;

import java.util.List;

public class Gebaeude {
	
	private int id;	
	private String strasse;
	private String hausnummer;
	private String guid;
	private String ort;
	private int plz;
	private int dma_id; 
	private List<Stockwerk> stwList;
	
	
	public List<Stockwerk> getStwList() {
		return stwList;
	}

	public void setStwList(List<Stockwerk> stwList) {
		this.stwList = stwList;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int blt_id;

	
	public int getId() {
		return id;
	}
	
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public int getDma_id() {
		return dma_id;
	}
	public void setDma_id(int dma_id) {
		this.dma_id = dma_id;
	}
}