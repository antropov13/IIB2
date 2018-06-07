package beansDB;

public class Maengel {
	private int id;
	private int aft_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAuftrag(int id) {
		this.aft_id = id;
		
	}
	public int getAuftrag() {
		return this.aft_id;
	}
}
