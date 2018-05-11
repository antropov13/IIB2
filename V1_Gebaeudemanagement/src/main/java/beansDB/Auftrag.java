package beansDB;

public class Auftrag {
	private int id;
	private String date;
	private String status;
	private int dlr_id;
	private int dma_idl;
	private String auftragsersteller;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDlr_id() {
		return dlr_id;
	}
	public void setDlr_id(int dlr_id) {
		this.dlr_id = dlr_id;
	}
	public int getDma_idl() {
		return dma_idl;
	}
	public void setDma_idl(int dma_idl) {
		this.dma_idl = dma_idl;
	}
	
	public String getAuftragsersteller() {
		return auftragsersteller;
	}
	public void setAuftragsersteller(String auftragsersteller) {
		this.auftragsersteller = auftragsersteller;
	}
	
}
