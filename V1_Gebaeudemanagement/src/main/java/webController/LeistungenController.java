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

import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
import manage.DBManager;

@Controller
public class LeistungenController {
	
	public int LeistungsspektrumID = 0;
	public int leistungID = 0;
	
	@RequestMapping(value = { "/", "leistungen" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Dienstleister dlr = (Dienstleister) req.getSession().getAttribute("user");
		
		List<Leistungsspektrum> spektrum = new ArrayList<Leistungsspektrum>();
		
		DBManager dbm = new DBManager();	
		
					
		String sql = "SELECT lsp_id from leistungsspektren where lsp_dlr_id = " + dlr.getId() + ";";
		spektrum = dbm.getLeistungsspektren(sql);
		dlr.setLeistungsspektren(spektrum);
		
		sql = "SELECT lsp_id, dln_id, dln_name, dln_beschreibung, dln_haefigkeit, lld_preis, lld_id, dln_dma_id "
				+ "from leistungsspektren, dienstleistungen, lnlspdln where lsp_dlr_id = " + dlr.getId() + " "
				+ "AND lsp_id = lld_lsp_id AND lld_dln_id = dln_id";

		spektrum = dbm.getLeistungen(sql, dlr);
		dlr.setLeistungsspektren(spektrum);
		
		req.getSession().setAttribute("leistungen", spektrum); // set session attribute
		model.addAttribute("leistungen", spektrum);
		
		req.getSession().setAttribute("user", dlr); // set session attribute
		model.addAttribute("user", dlr);
		//return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";
		return "dienstleister";
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
			String preis = req.getParameter("Preis " + d.getDlnId());
			System.out.println(preis);
			d.setPreis(Integer.parseInt(preis));
			
			String sql = "UPDATE lnlspdln SET lld_preis = " + preis + " where lld_id = " + d.getDlnId() +" AND lld_lsp_id = " + LeistungsspektrumID + ";";
			dbm.update(sql);
			System.out.println(preis + " " + d.getDlnId());
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
		
		List<Dienstleistung> dlnList = new ArrayList<Dienstleistung>();
		
		Leistungsspektrum ls = new Leistungsspektrum();
		ls.setId(id);
		ls.setDienstleistungen(dlnList);
		ls.setName(String.valueOf(id));
		
		user.setLeistungsspektrum(ls);
		//int countLS = user.getLeistungsspektren().size();
		
		model.addAttribute("spektrum", ls);
		
		return "dienstleister";
	}
	
	@RequestMapping(value = "/hinzufuegenLeistung", method = RequestMethod.GET)
	public String hinzufuegenLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String sql ="SELECT * FROM dienstleistungen where dln_id NOT IN (select lld_dln_id from lnlspdln where lld_lsp_id = " + LeistungsspektrumID + ")";  // "SELECT * FROM dienstleistungen";
		List<Dienstleistung> dlnList = dbm.getDienstleistungen(sql);
		if (dlnList==null)
		{
			Leistungsspektrum ls = user.getLeistungsspektren(LeistungsspektrumID);
			model.addAttribute("spektrum", ls);
			req.setAttribute("submitDone","done");
			return "aenderungLeistung";
		}
		else {
		model.addAttribute("dienstleistungen", dlnList);
		return "hinzufuegenLeistung";
		}
		
	}
	
	@RequestMapping(value = "hinzufuegenLeistungForm", method = { RequestMethod.POST })
	public String hinzufuegenLeistungForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		System.out.println("0");
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		String dln = req.getParameter("dienstleistungen");
		String[] subStr;
		subStr = dln.split("; ");
		System.out.println("1");
		this.leistungID = Integer.parseInt(subStr[3]);
		String name = subStr[0];
		String beschreibung = subStr[1];
		String haeufigkeit = subStr[2];
		int preis = Integer.parseInt(req.getParameter("preis"));
		DBManager dbm = new DBManager();
		System.out.println("2");
		String sql = "INSERT INTO `lnlspdln` (lld_dln_id, lld_lsp_id, lld_preis) VALUES (" + leistungID +", " + LeistungsspektrumID + ", " + preis + ");";
		dbm.update(sql);
		System.out.println("3");
		
		Dienstleistung dlnnew = new Dienstleistung();
		//dlnnew.setId(leistungID);
		dlnnew.setDlnId(leistungID);
		dlnnew.setName(name);
		dlnnew.setBeschreibung(beschreibung);
		dlnnew.setHaeufigkeit(haeufigkeit);
		dlnnew.setPreis(preis);
		System.out.println("4 " + LeistungsspektrumID);
		Leistungsspektrum ls = user.getLeistungsspektren(LeistungsspektrumID);
		System.out.println("6" + ls.getName());
		
		ls.addDienstleistung(dlnnew);
		System.out.println("7");
		model.addAttribute("spektrum", ls);
		System.out.println("5");
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
