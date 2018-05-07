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

import beansDB.Fachrolle;
import beansDB.Gebaeude;
import beansDB.Leistungsspektren;
import manage.DBManager;

@Controller
public class LeistungsController {

	@RequestMapping(value = "/leistungsAusgeben", method = RequestMethod.GET)
	public String leistungsausgaben(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		String view;
		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {

			
			List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();

			DBManager dbm = new DBManager();
			
			//String sql = "SELECT * "
			//		+ "FROM leistungsspektren, dienstleistungen  "
			//		+ "WHERE ls_dlr_id = " + user.getId() + ""
			//		+ "AND ls_dln_id = dln_id;";
			
			String sql = "SELECT ls_id, dln_name, dln_beschreibung, ls_preis "
					+ "FROM leistungsspektren, dienstleistungen "
					+ "WHERE ls_dlr_id = " + user.getId() + " AND ls_dln_id = dln_id;";
			if(user.getFachrolle() == "Gurachter")
			{
				// sql = ...
				// hier SQL selber schreiben!
				
			}
			leistungen = dbm.getLeistungen(sql);
			model.addAttribute("leistungen", leistungen);
			view = "leistungsAusgeben";
		}
		return view;

	}
/*
	@RequestMapping(value = "/gebaeudeEingeben", method = RequestMethod.GET)
	public String gebaeudeeingeben(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		model.addAttribute("user", user);
		return "gebaeudeEingeben";
	}

	@RequestMapping(value = "/insertGeb", method = RequestMethod.POST)
	public String eingeben(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view;
		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "Bitte loggen Sie sich als Gutachter ein, um auf diese Seite zu kommen.");
			view = "error";

		} else {

			Gebaeude geb = new Gebaeude();
			geb.setGuid(req.getParameter("guid"));
			geb.setStrasse(req.getParameter("strasse"));
			geb.setHausnummer(req.getParameter("hausnummer"));
			geb.setPlz(Integer.parseInt(req.getParameter("plz")));
			geb.setOrt(req.getParameter("ort"));

			geb.setBlt_id(user.getId());
			DBManager.eingebenGeb(geb);
			model.addAttribute("feedback", "erfolgreich eingegeben!");
			view = user.getFachrolle().toLowerCase();
			view = "redirect:/" + view + ".jsp";
		}
		return view;
	}
*/
}
