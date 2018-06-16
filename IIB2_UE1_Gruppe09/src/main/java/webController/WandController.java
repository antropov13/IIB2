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
import beansDB.Wand;
import manage.DBManager;

@Controller
public class WandController {

	public int GebaeudeID = 0;
	public int StockwerkID = 0;
	public int RaumID = 0;
	public int WandID = 0;
	private String aenderungGebaeude;


	@RequestMapping(value = "/loeschenWand", method = RequestMethod.GET)
	public String loeschenWand(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		this.RaumID = Integer.parseInt(req.getParameter("raumID"));
		this.WandID = Integer.parseInt(req.getParameter("wandID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM wand WHERE wan_id = " + WandID + ";";
		dbm.update(sql);

		return "redirect:/aenderungRaum?stwID="+StockwerkID+"&gebID="+GebaeudeID+"&raumID="+RaumID;
	}
	

	@RequestMapping(value = "/hinzufuegenWand", method = RequestMethod.POST)
	public String hinzufuegenWand(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		this.RaumID = Integer.parseInt(req.getParameter("raumID"));
		String Guid = req.getParameter("guidWand");
		System.out.println("hinWand " + RaumID);
		String sql = "INSERT INTO wand (wan_guid, wan_rau_id) VALUES (\"" + Guid +"\", " + RaumID +")";
		dbm.update(sql);
 
		return "redirect:/aenderungRaum?stwID="+StockwerkID+"&gebID="+GebaeudeID+"&raumID="+RaumID;
		
	}

}
