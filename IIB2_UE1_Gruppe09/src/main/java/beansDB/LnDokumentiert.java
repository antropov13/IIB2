package beansDB;
 
import java.text.SimpleDateFormat;
import java.util.Date;

public class LnDokumentiert {
	private int id;
	private int dma_id;
	private int mgl_id;
	private Date date;
	private String beschreibung;
	private String titel;
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public Date getDate() {
		return date;
	}
	public String getFormatDate() {
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
		return f.format(this.date);
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMgl_id() {
		return mgl_id;
	}
	public void setMgl_id(int mgl_id) {
		this.mgl_id = mgl_id;
	}
	public int getDma_id() {
		return dma_id;
	}
	public void setDma_id(int dma_id) {
		this.dma_id = dma_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
