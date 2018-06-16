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
import manage.DBManager;

@Controller
public class StockwerkController {

	public int GebaeudeID = 0;
	public int StockwerkID = 0;
	private String aenderungGebaeude;

	@RequestMapping(value = { "/", "stockwerk" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();

		String sql = "SELECT * from gebaeude;";
		//
		gebAll = dbm.getGeb(sql);
 

		req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
		model.addAttribute("gebaeude", gebAll); 
		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		System.out.println("GebL " + gebAll.size());

		return "dezernatmitarbeiter";
	}

	@RequestMapping(value = "/aenderungStockwerk", method = RequestMethod.POST)
	public String aenderungStockwerk(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();

		String sql = "SELECT * from gebaeude;";
		//
		gebAll = dbm.getGeb(sql);
 
		Gebaeude gebToEdit = null;
		for(Gebaeude g: gebAll) {  
			if(g.getId() == GebaeudeID) {
				gebToEdit= g;
				break;
			}
		}
		model.addAttribute("gebaeude", gebAll); 
		model.addAttribute("gebToEdit", gebToEdit);
		view = "/aenderungGebaeude";
		return view;

	}

	@RequestMapping(value = "/aenderungStockwerkForm", method = RequestMethod.POST)
	public String aenderungStockwerkForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Gebaeude> gebaeude = (List<Gebaeude>) req.getSession().getAttribute("gebaeude"); 
		DBManager dbm = new DBManager();
		String str = req.getParameter("strasse");
		String nr = req.getParameter("nr");
		String ort = req.getParameter("ort");
		int plz = Integer.parseInt(req.getParameter("plz"));

		for (Gebaeude g : gebaeude) {
			if (g.getId() == GebaeudeID) {
				g.setStrasse(str);
				g.setHausnummer(nr);
				g.setOrt(ort);
				g.setPlz(plz);
			}
		}

		String sql = "UPDATE gebaeude SET geb_strasse = \" " + str + "\" , geb_hausnr = \"" + nr + "\" , geb_ort = \""
				+ ort + "\" , geb_plz = " + plz + " where geb_id = " + GebaeudeID + ";";
		dbm.update(sql);

		sql = "SELECT * from gebaeude;";
		gebaeude = dbm.getGeb(sql); 

		return "redirect:/dezernatmitarbeiter.jsp";

	}

	@RequestMapping(value = "/loeschenStockwerk", method = RequestMethod.GET)
	public String loeschenStockwerk(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		this.StockwerkID = Integer.parseInt(req.getParameter("stwID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 
		//List<Gebaeude> gebaeude = new ArrayList<Gebaeude>();

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM stockwerk WHERE stw_id = " + StockwerkID + ";";
		dbm.update(sql);

		//sql = "SELECT * from gebaeude;";
		//gebaeude = dbm.getGeb(sql);

		//model.addAttribute("gebaeude", gebaeude); 
		//return "dezernatmitarbeiter";
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

	@RequestMapping(value = "/hinzufuegenStockwerkForm", method = RequestMethod.POST)
	public String hinzufuegenStockwerkForms(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String str = req.getParameter("strasse");
		String nr = req.getParameter("nr");
		String ort = req.getParameter("ort");
		int plz = Integer.parseInt(req.getParameter("plz"));

		String sql = "INSERT INTO gebaeude (geb_strasse, geb_hausnr, geb_ort, geb_plz, geb_dma_id)  VALUES (\"" + str
				+ "\", \"" + nr + "\", \"" + ort + "\", " + plz + ", " + user.getId() + ");";
		dbm.update(sql);
		Gebaeude geb = new Gebaeude();
		geb.setStrasse(str);
		geb.setHausnummer(nr);
		geb.setOrt(ort);
		geb.setPlz(plz);
		geb.setDma_id(user.getId());

		sql = "SELECT * from gebaeude;";
		List<Gebaeude> gebaeude = dbm.getGeb(sql);

		System.out.println("GebL " + gebaeude.size());
 
		model.addAttribute("gebaeude", gebaeude);
		req.setAttribute("gebaeude", gebaeude);
		 
		return "dezernatmitarbeiter";
	}
}
