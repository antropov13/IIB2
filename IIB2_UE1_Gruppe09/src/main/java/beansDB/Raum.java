package beansDB;

public class Raum {
	private int id;
	private String nr;
	private String bezeichnung;
	private int stw_id;
	private String guid;
	
	public int getStw_id() {
		return stw_id;
	}
	public void setStw_id(int stw_id) {
		this.stw_id = stw_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
