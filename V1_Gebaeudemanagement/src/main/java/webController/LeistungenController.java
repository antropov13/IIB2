package webController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import beansDB.Leistungsspektrum;
import manage.DBManager;

@Controller
public class LeistungenController {
	
	public int LeistungsspektrumID = 0;
	public int leistungID = 0;
	
	@RequestMapping(value = { "/", "dienstleister" })
	public void home() {
		System.out.println("1234");
		//return "redirect:/dienstleister.jsp";
	}

	@RequestMapping(value = "/aenderungLeistung", method = RequestMethod.GET)
	public String aenderungLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.LeistungsspektrumID = Integer.parseInt(req.getParameter("LeistungsspektrumID"));
		String view;
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		
		if (user == null || user.getFachrolle().equals("Dezernatmitarbeiter")) {
			model.addAttribute("error", "Bitte loggen Sie sich als Dienstleiter ein, um auf diese Seite zu kommen.");
			view = "error";
		} else {
			
			List<Leistungsspektrum> ls = (List<Leistungsspektrum>) user.getLeistungsspektren();
			Leistungsspektrum spektrum = new Leistungsspektrum();
			for (Leistungsspektrum l : ls) {
				if(l.getId()==LeistungsspektrumID) {
					spektrum = l;
					break;
				}
			}
			model.addAttribute("spektrum", spektrum);
			view = "aenderungLeistung";
		}
		return view;

	}
	
	@RequestMapping(value = "aenderungLeistungForm", method = { RequestMethod.POST })
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		String view;
	
		Leistungsspektrum ls = (Leistungsspektrum) user.getLeistungsspektren(LeistungsspektrumID);
		List<Dienstleistung> dlnList = ls.getDienstleistungen();
		
		DBManager dbm = new DBManager();
		
		for (Dienstleistung d : dlnList)
		{
			String preis = req.getParameter("Preis " + d.getId());
			System.out.println(preis);
			d.setPreis(Integer.parseInt(preis));
			
			String sql = "UPDATE lnlspdln SET lld_preis = " + preis + " where lld_id = " + d.getId() +" AND lld_lsp_id = " + LeistungsspektrumID + ";";
			dbm.update(sql);
			System.out.println(preis + " " + d.getId());
		}
		model.addAttribute("spektrum", ls);
		return "dienstleister";
		
	}
	
	@RequestMapping(value = "/loeschenLeistung", method = RequestMethod.GET)
	public String loeschenLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.leistungID = Integer.parseInt(req.getParameter("DnlID"));
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		Leistungsspektrum ls = (Leistungsspektrum) user.getLeistungsspektren(LeistungsspektrumID);
		Dienstleistung dln = (Dienstleistung) ls.getDienstleistungen(leistungID);
		ls.delDienstleistungen(dln);
		DBManager dbm = new DBManager();
		
		String sql = "";
		if(leistungID==-1) {
			sql = "DELETE FROM leistungsspektren WHERE ls_dlr_id = " + user.getId() + ";";
		}
		
		else {
			sql = "DELETE FROM lnlspdln WHERE lld_dln_id = " + leistungID + " AND lld_lsp_id = " + LeistungsspektrumID + ";";
		}
		dbm.update(sql);
		model.addAttribute("spektrum", ls);
		
		return "aenderungLeistung";
	}
	
	@RequestMapping(value = "/hinzufuegenLeistungsspektrum", method = RequestMethod.GET)
	public String hinzufuegenLeistungsspektrum(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		
		DBManager dbm = new DBManager();
		String sql = "INSERT INTO `leistungsspektren` (lsp_dlr_id) VALUES (" + user.getId() +");";
		int id = dbm.setSpektrum(sql);
		
		
		Leistungsspektrum ls = new Leistungsspektrum();
		ls.setId(id);
		user.setLeistungsspektrum(ls);
		//int countLS = user.getLeistungsspektren().size();
		ls.setName(String.valueOf(id));
		model.addAttribute("spektrum", ls);
		
		return "dienstleister";
	}
	
	@RequestMapping(value = "/hinzufuegenLeistung", method = RequestMethod.GET)
	public String hinzufuegenLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		
		DBManager dbm = new DBManager();
		String sql = "SELECT * FROM dienstleistungen";
		List<Dienstleistung> dlnList = dbm.getDienstleistungen(sql);
		
		model.addAttribute("dienstleistungen", dlnList);
		
		return "hinzufuegenLeistung";
	}
	
	@RequestMapping(value = "hinzufuegenLeistungForm", method = { RequestMethod.POST })
	public String hinzufuegenLeistungForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		String dln = req.getParameter("dienstleistungen");
		String[] subStr;
		subStr = dln.split("; ");
		this.leistungID = Integer.parseInt(subStr[3]);
		String name = subStr[0];
		String beschreibung = subStr[1];
		String haeufigkeit = subStr[2];
		int preis = Integer.parseInt(req.getParameter("preis"));
		
		//System.out.println(leistungID + " " + LeistungsspektrumID + " " +preis);
		//System.out.println("0");
		DBManager dbm = new DBManager();
		//String sql = "SET FOREIGN_KEY_CHECKS=0";
		//String sql = "SELECT lsp_id FROM `leistungsspektren` WHERE lsp_id=" +  LeistungsspektrumID + ";";
		//dbm.getSpektrum(sql);
		//System.out.println("1");
		String sql = "INSERT INTO `lnlspdln` (lld_dln_id, lld_lsp_id, lld_preis) VALUES (" + leistungID +", " + LeistungsspektrumID + ", " + preis + ");";
		//String sql = "INSERT INTO `lnlspdln` (lld_dln_id, lld_lsp_id, lld_preis) VALUES (3, 5, 8);";
		//System.out.println("2");
		//sql = "INSERT INTO `lnlspdln` (lld_dln_id, lld_lsp_id, lld_preis) VALUES (3, " + "(SELECT lsp_id FROM `leistungsspektren` WHERE lsp_id=" +  LeistungsspektrumID + ")" + ", 350)";
		dbm.update(sql);
		//System.out.println("2");
		//sql = "SET FOREIGN_KEY_CHECKS=1";
		//dbm.update(sql);
		//System.out.println("3");
		
		Dienstleistung dlnnew = new Dienstleistung();
		//dlnnew.setId(leistungID);
		dlnnew.setDlnId(leistungID);
		dlnnew.setName(name);
		dlnnew.setBescheibung(beschreibung);
		dlnnew.setHaeufigkeit(haeufigkeit);
		dlnnew.setPreis(preis);
		
		Leistungsspektrum ls = user.getLeistungsspektren(LeistungsspektrumID);
		ls.addDienstleistung(dlnnew);
		model.addAttribute("spektrum", ls);

		return "aenderungLeistung";
	}
	
	@RequestMapping(value = "/loeschenLeistungsspektrum", method = RequestMethod.GET)
	public String loeschenLeistungsspektrum(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		this.LeistungsspektrumID = Integer.parseInt(req.getParameter("LeistungsspektrumID"));
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		
		String sql = "";
		
		if (LeistungsspektrumID!=-1) {
		System.out.println("-1");
		Leistungsspektrum ls = (Leistungsspektrum) user.getLeistungsspektren(LeistungsspektrumID);
		//Dienstleistung dln = (Dienstleistung) ls.getDienstleistungen(leistungID);
		user.delLeistungsspektren(ls);
		List<Leistungsspektrum> lss = user.getLeistungsspektren();
		

		sql = "DELETE FROM lnlspdln WHERE lld_lsp_id = " + LeistungsspektrumID + ";";
		dbm.update(sql);
		sql = "DELETE FROM leistungsspektren WHERE lsp_dlr_id = " + user.getId() + " AND lsp_id = " + ls.getId() + ";";
		dbm.update(sql);

		model.addAttribute("spektrum", lss);
		}
		
		else {
			List<Leistungsspektrum> lsList = user.getLeistungsspektren();
			for (Leistungsspektrum l : lsList) {
				sql = "DELETE FROM lnlspdln WHERE lld_lsp_id = " + l.getId() + ";";
				dbm.update(sql);
				sql = "DELETE FROM leistungsspektren WHERE lsp_dlr_id = " + user.getId() + " AND lsp_id = " + l.getId() + ";";
				dbm.update(sql);
			}
			
			for (Iterator<Leistungsspektrum> iterator = lsList.iterator(); iterator.hasNext(); ) {
			    Leistungsspektrum value = iterator.next();
			        iterator.remove();
			}
			List<Leistungsspektrum> lss = user.getLeistungsspektren();
			model.addAttribute("spektrum", lss);
		}
		return "dienstleister";
	}
/*
 * 
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
