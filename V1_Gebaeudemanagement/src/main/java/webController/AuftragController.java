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

import beansDB.Auftrag;
import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
import manage.DBManager;

@Controller
public class AuftragController {
	
	public int AuftragID = 0;
	
	@RequestMapping(value = { "/", "auftraege" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		System.out.println("1234");
		return "/dezernatmitarbeiter";
	}

	
	@RequestMapping(value = "/oeffnenAuftrag", method = RequestMethod.GET)
	public String aenderungAuftraege(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.AuftragID = Integer.parseInt(req.getParameter("AuftragID"));
		String view;
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		List<Dienstleistung> dienstleistungList = new ArrayList<Dienstleistung>();
		Auftrag auftrag = new Auftrag();
	
		String sql = "SELECT * from dienstleistungen "
				+ "where dln_id in "
				+ "(SELECT lad_dln_id "
					+ "FROM lnaftdln "
					+ "where lad_aft_id = " + AuftragID + ");";
		
		DBManager dbm = new DBManager();	
		dienstleistungList = dbm.getDienstleistungen(sql);
		
		if (dienstleistungList == null)
		{
			dienstleistungList = new ArrayList<Dienstleistung>();
		}

		auftrag = user.getAuftrag(AuftragID);
		auftrag.setDienstleistungList(dienstleistungList);
		
		model.addAttribute("auftrag", auftrag);

		view = "auftrag";
		return view;

	}
	
	@RequestMapping(value = "/aenderungStatus", method = RequestMethod.GET)
	public String aenderungStatus(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view;
		Dienstleister user = (Dienstleister) req.getSession().getAttribute("user");
		int status_int = Integer.parseInt(req.getParameter("status"));
		String status = "";
		String warte_auftrag = "";
		switch(status_int) {
		
		case 1: 
			status="Erledigt";
			break;
		case 2:
			status="Ausfuehrung";
			break;
		case 3: 
			status="Abgelehnt";
			break;
		case 4:
			String sql = "UPDATE auftraege SET aft_dlr_id = NULL WHERE aft_id = " + AuftragID;
			DBManager dbm = new DBManager();
			dbm.update(sql);
			user.delAuftrag(AuftragID);
			warte_auftrag = warteAuftrag(user);
			model.addAttribute("warte_auftrag", warte_auftrag);
			return "dienstleister";
		
		}
		
		warte_auftrag = warteAuftrag(user);
		
		String sql = "UPDATE auftraege SET aft_status = '" + status + "' where aft_id = " + AuftragID;
		DBManager dbm = new DBManager();
		dbm.update(sql);
		
		user.getAuftrag(AuftragID).setStatus(status);

		Auftrag auftrag = new Auftrag();
		auftrag = user.getAuftrag(AuftragID);
		
		model.addAttribute("auftrag", auftrag);
		model.addAttribute("warte_auftrag", warte_auftrag);

		view = "auftrag";
		return view;
	}
	
	public String warteAuftrag(Dienstleister dlr)
	{
		int auftrag_warte = 0;
		String warte_auftrag = ""; 
		
		for (Auftrag at : dlr.getAuftraege())
		{
			if (at.getStatus().equals("Warte auf eine Antwort"))
			{
			auftrag_warte ++;
			};
		}
		
		if (auftrag_warte!=0) warte_auftrag = String.valueOf(auftrag_warte);
		return warte_auftrag;
	}
		
}
