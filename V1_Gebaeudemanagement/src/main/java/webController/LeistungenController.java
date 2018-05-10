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

import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektren;
import manage.DBManager;

@Controller
public class LeistungenController {
	
	public int LeistungsspekterID = 0;
	public int leistungID = 0;
	
	@RequestMapping(value = { "/", "dienstleister" })
	public void home() {
		System.out.println("1234");
		//return "redirect:/dienstleister.jsp";
	}

	@RequestMapping(value = "/aenderungLeistung", method = RequestMethod.GET)
	public String aenderungLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.LeistungsspekterID = Integer.parseInt(req.getParameter("LeistungsspekterID"));
		String view;
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		
		if (user == null || user.getFachrolle().equals("Dezernatmitarbeiter")) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {
			
			List<Leistungsspektren> ls = (List<Leistungsspektren>) user.getLeistungsspektren();
			Leistungsspektren spektren = new Leistungsspektren();
			for (Leistungsspektren l : ls) {
				if(l.getId()==LeistungsspekterID) {
					spektren = l;
					break;
				}
			}
			model.addAttribute("spektren", spektren);
			view = "aenderungLeistung";
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

		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		
		if (user == null || user.getFachrolle().equals("Dezernatmitarbeiter")) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {
			//List<Leistungsspektren> leistungen = new ArrayList<Leistungsspektren>();

			DBManager dbm = new DBManager();
		
			String sql = "INSERT INTO dienstleistungen (dln_name, dln_beschreibung) values('" + name +"', '" + beschreibung + "');";
			int id_new = dbm.setLeistungen(sql);
			
			if (this.LeistungsspekterID==-1) {
				sql = "INSERT INTO leistungsspektren (ls_dln_id, ls_dlr_id, ls_preis) values('" + id_new +"', '" + user.getId() + "', '" + preis + "');";
			}
			else {
				sql = "UPDATE leistungsspektren SET ls_dln_id = " + id_new + ", ls_preis = " + preis + " where ls_id = " + this.leistungID + " ;";
			}
			
			dbm.update(sql);
			System.out.println(this.LeistungsspekterID + " " + preis +  " " + id_new);
			
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
		
		this.leistungID = Integer.parseInt(req.getParameter("DnlID"));
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		Leistungsspektren ls = (Leistungsspektren) user.getLeistungsspektren(LeistungsspekterID);
		Dienstleistung dln = (Dienstleistung) ls.getDienstleistungen(leistungID);
		ls.delDienstleistungen(dln);
		DBManager dbm = new DBManager();
		
		String sql = "";
		if(leistungID==-1) {
			sql = "DELETE FROM leistungsspektren WHERE ls_dlr_id = " + user.getId() + ";";
		}
		
		else {
			sql = "DELETE FROM lnlspdln WHERE lld_dln_id = " + leistungID + " AND lld_lsp_id = " + LeistungsspekterID + ";";
		}
		
		dbm.update(sql);
		return "aenderungLeistung";
		
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
