package webController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import beansDB.Auftrag;
import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
import beansDB.LnDokumentiert;
import beansDB.Maengel;
import manage.DBManager;

@Controller
public class LoginController {
	@RequestMapping(value = { "/", "login" })
	public String home() {
		return "index";
	}
	

	@RequestMapping(value = "verify", method = { RequestMethod.POST })
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		System.out.println("1");
		
		Dienstleister dlr = new Dienstleister();
		Dezernatmitarbeiter dma = new Dezernatmitarbeiter();

		System.out.println("2");
		if (req.getParameter("fachrolle")==null) {
			dlr.setUsername(req.getParameter("username"));
			dlr.setPass(req.getParameter("password"));
			
			if (dlr.getUsername() == "" || dlr.getPass() == "") {
				model.addAttribute("warning", "Geben Sie bitte alle Daten ein");
				return "index";
			}
			System.out.println("3");
			dlr = loginDLR(dlr);
			
			if (dlr==null) {
				model.addAttribute("warning", "Passwort oder Nutzerkonto ist falsch!");
				return "index";
			}
			
			else {

				System.out.println("4");
				List<Leistungsspektrum> spektrum = new ArrayList<Leistungsspektrum>();
				
				DBManager dbm = new DBManager();	
				
							
				String sql = "SELECT * from leistungsspektren where lsp_dlr_id = " + dlr.getId() + ";";
				spektrum = dbm.getLeistungsspektren(sql);
				dlr.setLeistungsspektren(spektrum);
				
				sql = "SELECT lsp_id, dln_id, dln_name, dln_beschreibung, dln_haefigkeit, lld_preis, lld_id, dln_dma_id "
						+ "from leistungsspektren, dienstleistungen, lnlspdln where lsp_dlr_id = " + dlr.getId() + " "
						+ "AND lsp_id = lld_lsp_id AND lld_dln_id = dln_id";

				spektrum = dbm.getLeistungen(sql, dlr);
				dlr.setLeistungsspektren(spektrum);
				System.out.println("5");
				req.getSession().setAttribute("leistungen", spektrum); // set session attribute
				model.addAttribute("leistungen", spektrum);
				
				List<Auftrag> auftragList = new ArrayList<Auftrag>();
				sql = "SELECT * from auftraege where aft_dlr_id = " + dlr.getId() + ";";
				auftragList = dbm.getAuftraege(sql);
				dlr.setAuftraegeList(auftragList);
				
				int auftrag_warte = 0;
				String warte_auftrag = ""; 
				
				for (Auftrag at : dlr.getAuftraegeList())
				{
					if (at.getStatus().equals("Warte auf eine Antwort"))
					{
					auftrag_warte ++;
					};
				}
				System.out.println("6");
				if (auftrag_warte!=0) warte_auftrag = String.valueOf(auftrag_warte);
				
				req.getSession().setAttribute("auftraege", auftragList); // set session attribute
				model.addAttribute("auftraege", auftragList);
				
				req.getSession().setAttribute("warte_auftrag", warte_auftrag); // set session attribute
				model.addAttribute("warte_auftrag", warte_auftrag);
				
				req.getSession().setAttribute("user", dlr); // set session attribute
				model.addAttribute("user", dlr);
				System.out.println("7");
				return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";
				
			}
		}
		else {
			dma.setUsername(req.getParameter("username"));
			dma.setPass(req.getParameter("password"));
			if (dma.getUsername() == "" || dma.getPass() == "") {
				model.addAttribute("warning", "Geben Sie bitte alle Daten ein");
				return "index";
			}
			
			dma = loginDMA(dma);
			
			if (dma==null) {
				model.addAttribute("warning", "Passwort oder Nutzerkonto ist falsch!");
				return "index";
			}
			
			else {
				
				DBManager dbm = new DBManager();
				List<Auftrag> auftragList = new ArrayList<Auftrag>();
				String sql = "SELECT * from auftraege where aft_dma_id = " + dma.getId() + ";";
				auftragList = dbm.getAuftraege(sql);
				dma.setAuftraegeList(auftragList);
				
				req.getSession().setAttribute("auftraege", auftragList); // set session attribute
				model.addAttribute("auftraege", auftragList);

				List<Gebaeude> gebAll = new ArrayList<Gebaeude>();
				List<Gebaeude> gebForID = new ArrayList<Gebaeude>();
				dbm = new DBManager();	

				sql = "SELECT * from gebaeude;";
				
				gebAll = dbm.getGeb(sql);
				 sql = "SELECT * from gebaeude WHERE geb_dma_id = " + dma.getId() + ";";
				 gebForID = dbm.getGeb(sql);

				req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
				model.addAttribute("gebaeude", gebAll);
				req.getSession().setAttribute("mGebaeude", gebForID); // set session attribute for my buildings
				model.addAttribute("mGebaeude", gebForID);
				
				sql = "SELECT * from dienstleistungen;";
			    List<Dienstleistung> leistungen = new ArrayList<Dienstleistung>();
				leistungen = dbm.getDienstleistungen(sql);
				
				sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + dma.getId() + ";";
			    List<Dienstleistung> mLeistungen = new ArrayList<Dienstleistung>();
				mLeistungen = dbm.getDienstleistungen(sql);
				
				req.getSession().setAttribute("dienstleistungen", leistungen); // set session attribute
				model.addAttribute("dienstleistungen", leistungen);
				

				req.getSession().setAttribute("mDienstleistungen", mLeistungen); // set session attribute
				model.addAttribute("mDienstleistungen", mLeistungen); 
				

				sql = "SELECT * from lndokumentiert;";
			    List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>();
				berichte = dbm.getBerichte(sql);

				sql = "SELECT * from maengel;";
			    List<Maengel> maengel = new ArrayList<Maengel>();
				maengel = dbm.getMaengel(sql);

				sql = "SELECT * from dienstleister;";
				List<Dienstleister> dienstleistern = dbm.getDienstleister(sql);
 

				req.getSession().setAttribute("berichte", berichte); // set session attribute
				model.addAttribute("berichte", berichte);
				
				req.getSession().setAttribute("dienstleister", dienstleistern); // set session attribute
				model.addAttribute("dienstleister", dienstleistern); 
				

				req.getSession().setAttribute("maengel", maengel); // set session attribute
				model.addAttribute("maengel", maengel); 
				
				req.getSession().setAttribute("user", dma); // set session attribute
				model.addAttribute("user", dma);
				return "redirect:/" + dma.getFachrolle().toLowerCase() + ".jsp";
			}
		}
	}
	
	public Dienstleister loginDLR(Dienstleister dlr) throws ClassNotFoundException, SQLException {
		DBManager dbm = new DBManager();	
		String sql = "SELECT * FROM " + dlr.getFachrolle() + " WHERE dlr_username = '" + dlr.getUsername()
				+ "' AND dlr_passwort = '" + dlr.getPass() + "';";
		return dbm.getUserDLR(sql);
	}
	
	public Dezernatmitarbeiter loginDMA(Dezernatmitarbeiter dma) throws ClassNotFoundException, SQLException {
		DBManager dbm = new DBManager();	
		String sql = "SELECT * FROM " + dma.getFachrolle() + " WHERE dma_username = '" + dma.getUsername()
				+ "' AND dma_passwort = '" + dma.getPass() + "';";
		return dbm.getUserDMA(sql);
	}
	
	

	@RequestMapping(value = "logout", method=RequestMethod.GET)
	public String logout(HttpSession session, SessionStatus status,
			HttpServletResponse res) {
		status.setComplete();
		session.setAttribute("user", null);
		// session.invalidate();
		return "index";
	}

}
