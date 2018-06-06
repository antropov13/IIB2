package webController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.LnDokumentiert;
import beansDB.Maengel;
import manage.DBManager;

@Controller
public class MaengelController {

	@RequestMapping(value = { "/", "maengel" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Maengel> maengel = new ArrayList<Maengel>();
		List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>();
		List<Dienstleister> dienstleistern = new ArrayList<Dienstleister>();
		List<Dienstleistung> dienstleistungen = new ArrayList<Dienstleistung>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from maengel;";
		//
		maengel = dbm.getMaengel(sql);

		sql = "SELECT * from lndokumentiert;";
		berichte = dbm.getBerichte(sql);

		sql = "SELECT * from dienstleister;";
		dienstleistern = dbm.getDienstleister(sql);

		sql = "SELECT * from dienstleistungen;";
		dienstleistungen = dbm.getDienstleistungen(sql);

		req.getSession().setAttribute("maengel", maengel); // set session attribute
		model.addAttribute("maengel", maengel);
		req.getSession().setAttribute("berichte", berichte); // set session attribute for reports
		req.getSession().setAttribute("dienstleister", dienstleistern); // set session attribute for my buildings
		model.addAttribute("dienstleister", dienstleistern);
 
		req.getSession().setAttribute("dienstleistungen", dienstleistungen); // set session attribute for my buildings
		model.addAttribute("dienstleistungen", dienstleistungen);


		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		// return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";

		return "redirect:/dezernatmitarbeiter.jsp";
	}

	@RequestMapping(value = "/hinzufuegenMaengel", method = RequestMethod.GET)
	public String hinzufuegenMaengel(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Maengel> maengel = new ArrayList<Maengel>();
		List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>();
		List<Dienstleister> dienstleistern = new ArrayList<Dienstleister>();
		List<Dienstleistung> dienstleistungen = new ArrayList<Dienstleistung>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from maengel;";
		//
		maengel = dbm.getMaengel(sql);

		sql = "SELECT * from lndokumentiert;";
		berichte = dbm.getBerichte(sql);

		sql = "SELECT * from dienstleister;";
		dienstleistern = dbm.getDienstleister(sql);

		sql = "SELECT * from dienstleistungen;";
		dienstleistungen = dbm.getDienstleistungen(sql);

		req.getSession().setAttribute("maengel", maengel); // set session attribute
		model.addAttribute("maengel", maengel);
		req.getSession().setAttribute("berichte", berichte); // set session attribute for my buildings
 
		req.getSession().setAttribute("dienstleister", dienstleistern); // set session attribute for my buildings
		model.addAttribute("dienstleister", dienstleistern); 
 
		req.getSession().setAttribute("dienstleistungen", dienstleistungen); // set session attribute for my buildings
		model.addAttribute("dienstleistungen", dienstleistungen);


		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		return "/hinzufuegenMaengel";
	}

	@RequestMapping(value = "/hinzufuegenMaengelForm", method = RequestMethod.POST)
	public String hinzufuegenMaengelForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		int dlrID = Integer.parseInt(req.getParameter("dienstleister"));
		int dlnID = Integer.parseInt(req.getParameter("dienstleistung")); 

		String sql = "INSERT INTO maengel (mgl_dln_id, mgl_dlr_id)  VALUES (" + dlnID
				+ ", " + dlrID + ");";
		dbm.update(sql); 

		sql = "SELECT * from maengel;";
		List<Maengel> i = dbm.getMaengel(sql); 
		Maengel m = i.get(i.size()-1);
		req.getSession().setAttribute("mgl", m);
		model.addAttribute("mgl", m); 
		//
		i = dbm.getMaengel(sql);

		sql = "SELECT * from lndokumentiert;";
		List<LnDokumentiert> berichte = dbm.getBerichte(sql);

		req.getSession().setAttribute("maengel", i); // set session attribute
		model.addAttribute("maengel", i);
		req.getSession().setAttribute("berichte", berichte); // set session attribute for my buildings
 
		req.getSession().setAttribute("user", user); // set session attribute
		model.addAttribute("user", user);
		
		view = "redirect:/hinzufuegenBericht.jsp";
		return view;
	}
}
