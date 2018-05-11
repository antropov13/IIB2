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
		System.out.println("12345");
		
		if (dienstleistungList == null)
		{
			dienstleistungList = new ArrayList<Dienstleistung>();
		}

		auftrag = user.getAuftrag(AuftragID);
		auftrag.setDienstleistungList(dienstleistungList);

		view = "auftrag";
		for (Dienstleistung d : dienstleistungList) {
		System.out.println(d.getName());
		}
		return view;

	}
		
}
