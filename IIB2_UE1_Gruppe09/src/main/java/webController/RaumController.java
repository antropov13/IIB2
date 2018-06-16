package webController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import beansDB.Dezernatmitarbeiter; 
import beansDB.Gebaeude;
import beansDB.Raum;
import beansDB.Stockwerk;
import manage.DBManager;

@Controller
public class RaumController {

	public int GebaeudeID = 0;
	public int StockwerkID = 0;
	public int RaumID = 0;
	private String aenderungGebaeude;

	@RequestMapping(value = "/aenderungRaum", method = RequestMethod.GET)
	public String aenderungRaum(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		this.RaumID = Integer.parseInt(req.getParameter("raumID"));
		//Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		//List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();
		Raum raum = new Raum();
		String sql = "SELECT * from raum where rau_id = " + RaumID +";";
		raum = dbm.getRaum_(sql);
		System.out.println(GebaeudeID + " " + StockwerkID + " " + raum.getBezeichnung());
		
		//List<Raum> raumList = new ArrayList<Raum>();
		//sql = "SELECT * FROM raum where rau_stw_id = " + StockwerkID + ";";
		//raumList = dbm.getRaumList(sql);
		
		//for(Raum r : raumList) {
		//	System.out.println(r.getBezeichnung());
		//}
		
		model.addAttribute("raum", raum);
		req.setAttribute("raum", raum);
		
		//req.setAttribute("stock", StockwerkID); 
		//req.setAttribute("geb", GebaeudeID);
		
		view = "/aenderungRaum";
		return view;

	}
	
	@RequestMapping(value = "/aenderungRaumForm", method = RequestMethod.POST)
	public String aenderungRaumForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.RaumID = Integer.parseInt(req.getParameter("raumID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String bezeichnung = req.getParameter("Bezeichnung");
		String nummer = req.getParameter("Nummer");
		DBManager dbm = new DBManager();
		String sql = "UPDATE raum SET rau_nummer = \" " + nummer + "\", rau_bezeichnung = \"" + bezeichnung + "\" WHERE rau_id = " + RaumID + ";";
		dbm.update(sql);
		
		return "redirect:/aenderungStockwerk?gebID="+GebaeudeID+"&stwID="+StockwerkID;

	}


	@RequestMapping(value = "/loeschenRaum", method = RequestMethod.GET)
	public String loeschenRaum(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		this.RaumID = Integer.parseInt(req.getParameter("raumID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM raum WHERE rau_id = " + RaumID + ";";
		dbm.update(sql);

		return "redirect:/aenderungStockwerk?stwID="+StockwerkID+"&gebID="+GebaeudeID;
	}
	

	@RequestMapping(value = "/hinzufuegenRaum", method = RequestMethod.POST)
	public String hinzufuegenRaum(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		String nummerRaum = req.getParameter("nummerRaum");
		String bezeichnungRaum = req.getParameter("bezeichnungRaum");
		
		String sql = "INSERT INTO raum (rau_nummer, rau_bezeichnung, rau_stw_id) VALUES (\"" + nummerRaum + "\", \"" + bezeichnungRaum  +"\", " + StockwerkID +")";
		dbm.update(sql);
 
		return "redirect:/aenderungStockwerk?stwID="+StockwerkID+"&gebID="+GebaeudeID;
		
	}

}
