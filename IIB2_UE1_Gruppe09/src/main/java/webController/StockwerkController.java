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
import beansDB.Stockwerk;
import manage.DBManager;

@Controller
public class StockwerkController {

	public int GebaeudeID = 0;
	public int StockwerkID = 0;
	private String aenderungGebaeude;

	@RequestMapping(value = "/aenderungStockwerk", method = RequestMethod.GET)
	public String aenderungStockwerk(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		//Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		//List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();
		Stockwerk stw = new Stockwerk();
		String sql = "SELECT * from stockwerk where stw_id = " + StockwerkID +";";
		stw = dbm.getStockwerk_(sql);
		System.out.println(GebaeudeID + " " + StockwerkID);
		model.addAttribute("stockwerk", stw); 
		model.addAttribute("gebId", GebaeudeID);
		view = "/aenderungStockwerk";
		return view;

	}
	
	@RequestMapping(value = "/aenderungStockwerkForm", method = RequestMethod.POST)
	public String aenderungStockwerkForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		//this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String bezeichnung = req.getParameter("Bezeichnung");
		System.out.println(GebaeudeID + " " + StockwerkID + " " + bezeichnung);
		//List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();
		String sql = "UPDATE stockwerk SET stw_bezeichnung = \"" + bezeichnung + "\" WHERE stw_id = " + StockwerkID + ";";
		dbm.update(sql);
		
		//model.addAttribute("stockwerk", stw); 
		//model.addAttribute("gebToEdit", gebToEdit);

		return "redirect:/aenderungGebaeude?gebID="+GebaeudeID;

	}


	@RequestMapping(value = "/loeschenStockwerk", method = RequestMethod.GET)
	public String loeschenStockwerk(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM stockwerk WHERE stw_id = " + StockwerkID + ";";
		dbm.update(sql);

		return "redirect:/aenderungGebaeude?gebID="+GebaeudeID;
	}
	

	@RequestMapping(value = "/hinzufuegenStockwerk", method = RequestMethod.POST)
	public String hinzufuegenStockwerk(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		String newStockwerk = req.getParameter("bezeichnungStw");
		
		String sql = "INSERT INTO stockwerk (stw_bezeichnung, stw_geb_id) VALUES (\"" + newStockwerk + "\", " + GebaeudeID +")";
		dbm.update(sql);
 
		return "redirect:/aenderungGebaeude?gebID="+GebaeudeID;
		
	}

}
