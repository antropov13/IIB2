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
public class BerichtController { 
	@RequestMapping(value = { "/", "maengelBericht" })
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
		req.getSession().setAttribute("berichte", berichte); // set session attribute for my buildings 

		req.getSession().setAttribute("dienstleister", dienstleistern); // set session attribute for my buildings
		model.addAttribute("dienstleister", dienstleistern);
 
		req.getSession().setAttribute("dienstleistungen", dienstleistungen); // set session attribute for my buildings
		model.addAttribute("dienstleistungen", dienstleistungen);


		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		// return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";

		return "redirect:/dezernatmitarbeiter.jsp";
	}

	@RequestMapping(value = "/hinzufuegenBerichtForm", method = RequestMethod.POST)
	public String hinzufuegenBerichtForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		int mglID = Integer.parseInt(req.getParameter("mglID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String titel = req.getParameter("titel");
		String bes = req.getParameter("bes");
		String date = req.getParameter("date"); 

		String sql = "INSERT INTO lndokumentiert (ldo_dma_id, ldo_mgl_id, ldo_titel, ldo_bes)  VALUES (" + user.getId()
				+ "," + mglID + ", \"" + titel + "\", \"" + bes + "\");";
		dbm.update(sql);
		sql = "SELECT * from lndokumentiert;";
		List<LnDokumentiert> berichte = dbm.getBerichte(sql);
 
		model.addAttribute("berichte", berichte);
		view = "redirect:/dezernatmitarbeiter.jsp";
		return view;
	}

}
