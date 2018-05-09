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
public class LeistungenController {

	public int leistungID = 0;

	@RequestMapping(value = "/aenderungLeistung", method = RequestMethod.GET)
	public String aenderungLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.leistungID = Integer.parseInt(req.getParameter("LeistungID"));
		String view;
		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		
		if (user == null || user.getFachrolle().equals("Dezernatmitarbeiter")) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {
			List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();
			DBManager dbm = new DBManager();
			/*
			if (this.leistungID==-1) {
				Leistungsspektren ls = null ;
				ls = new Leistungsspektren();
				ls.setName("");
				ls.setBescheibung("");
				ls.setPreis(0);
				leistungen.add(ls);
				view = "aenderungLeistung";
			}
			
			
			else {
			String sql = "SELECT ls_id, dln_name, dln_beschreibung, ls_preis "
					+ "FROM leistungsspektren, dienstleistungen "
					+ "WHERE ls_dlr_id = " + user.getId() + " AND ls_dln_id = dln_id AND ls_id = " + leistungID + ";";
			leistungen = dbm.getLeistungen(sql);
			view = "aenderungLeistung";
			}
			model.addAttribute("leistungen", leistungen);
			*/
			view = "aenderungLeistung";
			for (Leistungsspektren l : leistungen) {
				System.out.println(l.getName());
			}
		}
		return view;

	}
	
	@RequestMapping(value = "aenderungLeistungForm", method = { RequestMethod.POST })
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		String view;
		String name = req.getParameter("Name");
		String beschreibung = req.getParameter("Beschreibung");
		String preis = req.getParameter("Preis");

		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		
		if (user == null || user.getFachrolle().equals("Dezernatmitarbeiter")) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {
			//List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();

			DBManager dbm = new DBManager();
		
			String sql = "INSERT INTO dienstleistungen (dln_name, dln_beschreibung) values('" + name +"', '" + beschreibung + "');";
			int id_new = dbm.setLeistungen(sql);
			
			if (this.leistungID==-1) {
				sql = "INSERT INTO leistungsspektren (ls_dln_id, ls_dlr_id, ls_preis) values('" + id_new +"', '" + user.getId() + "', '" + preis + "');";
			}
			else {
				sql = "UPDATE leistungsspektren SET ls_dln_id = " + id_new + ", ls_preis = " + preis + " where ls_id = " + this.leistungID + " ;";
			}
			
			dbm.update(sql);
			System.out.println(this.leistungID + " " + preis +  " " + id_new);
			
			List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();
			
			sql = "SELECT ls_id, dln_name, dln_beschreibung, ls_preis "
					+ "FROM leistungsspektren, dienstleistungen "
					+ "WHERE ls_dlr_id = " + user.getId() + " AND ls_dln_id = dln_id;";
			
			leistungen = dbm.getLeistungen(sql);
			req.getSession().setAttribute("leistungen", leistungen); // set session attribute
			model.addAttribute("leistungen", leistungen);
			
		}
		return "redirect:/" + user.getFachrolle().toLowerCase() + ".jsp";

	}
	
	@RequestMapping(value = "/loeschenLeistung", method = RequestMethod.GET)
	public String loeschenLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Fachrolle user = (Fachrolle) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		
		this.leistungID = Integer.parseInt(req.getParameter("LeistungID"));
		String sql = "";
		if(leistungID==-1) {
			sql = "DELETE FROM leistungsspektren WHERE ls_dlr_id = " + user.getId() + ";";
		}
		
		else {
			sql = "DELETE FROM leistungsspektren WHERE ls_id = " + leistungID + " AND ls_dlr_id = " + user.getId() + ";";
		}
		
		dbm.update(sql);
		
		List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();
		
		sql = "SELECT ls_id, dln_name, dln_beschreibung, ls_preis "
				+ "FROM leistungsspektren, dienstleistungen "
				+ "WHERE ls_dlr_id = " + user.getId() + " AND ls_dln_id = dln_id;";
		
		leistungen = dbm.getLeistungen(sql);
		req.getSession().setAttribute("leistungen", leistungen); // set session attribute
		model.addAttribute("leistungen", leistungen);
		
		return "redirect:/" + user.getFachrolle().toLowerCase() + ".jsp";
		
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
