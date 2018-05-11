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

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
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
		
		Dienstleister dlr = new Dienstleister();
		Dezernatmitarbeiter dma = new Dezernatmitarbeiter();

		if (req.getParameter("fachrolle")==null) {
			dlr.setUsername(req.getParameter("username"));
			dlr.setPass(req.getParameter("password"));
			
			if (dlr.getUsername() == "" || dlr.getPass() == "") {
				model.addAttribute("warning", "Geben Sie bitte alle Daten ein");
				return "index";
			}
			
			dlr = loginDLR(dlr);
			
			if (dlr==null) {
				model.addAttribute("warning", "Passwort oder Nutzerkonto ist falsch!");
				return "index";
			}
			
			else {

				
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

				List<Gebaeude> gebAll = new ArrayList<Gebaeude>();
				List<Gebaeude> gebForID = new ArrayList<Gebaeude>();
				DBManager dbm = new DBManager();	
				

				String sql = "SELECT * from gebaeude;";
				//String sql2 = "SELECT geb_id from gebaeude WHERE geb_dma_id = " + dma.getId() + ";";
				gebAll = dbm.getGeb(sql);
				
				for(Gebaeude g: gebAll) {
					if(g.getDma_id() == dma.getId())
						gebForID.add(g);
				}

				req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
				model.addAttribute("gebaeude", gebAll);
				req.getSession().setAttribute("mGebaeude", gebForID); // set session attribute for my buildings
				model.addAttribute("mGebaeude", gebForID);
			
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
