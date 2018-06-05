package webController;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import beansDB.LnAuftragDln;
import beansDB.Lnlspdln;
import manage.DBManager;

@Controller
public class AuftragController {
	
	public int AuftragID = 0;
	public List<Dienstleister> dienstleisterList;
	public Auftrag auftrag;
	
	@RequestMapping(value = { "/", "auftraege" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		System.out.println("1234");
		return "/dezernatmitarbeiter";
	}
	
	@RequestMapping(value = "neuerAuftrag", method = { RequestMethod.POST })
	public String neuerAuftrag(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException, ParseException {
		System.out.println("1234");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		Auftrag auftrag = new Auftrag();
		auftrag.setDma_idl(user.getId());
		System.out.println("User: " + user.getId());
		auftrag.setAuftragsersteller(user.getNachname() + " " + user.getVorname());
		Gebaeude gebaeude = new Gebaeude();
		System.out.println("User: " + auftrag.getAuftragsersteller());
		System.out.println("Geb: " + req.getParameter("gebaeude"));
		gebaeude.setId(Integer.parseInt(req.getParameter("gebaeude")));
		System.out.println("Geb: " + gebaeude.getId());
		String sql = "SELECT * FROM gebaeude where geb_id = " + gebaeude.getId();
		DBManager dbm = new DBManager();
		gebaeude = dbm.getGebaeude(sql);
		auftrag.setGebaeude(gebaeude);
		auftrag.setDate(format.parse(req.getParameter("datum")));
		System.out.println("Datum: " + auftrag.getDateTag());
		Dienstleister dlr = new Dienstleister();
		dlr.setId(Integer.parseInt(req.getParameter("dlr")));
		sql = "SELECT * FROM dienstleister where dlr_id = " + dlr.getId();
		dlr = dbm.getUserDLR(sql);
		auftrag.setDlr_id(dlr.getId());
		auftrag.setDienstleister(dlr.getFirmaname());
		System.out.println(dlr.getFirmaname());
		List<Dienstleistung> dlnList = new ArrayList<Dienstleistung>();
		
		String[] dln = req.getParameterValues("dlnselect");
		
		for (Dienstleister dlr_ : dienstleisterList)
		{
			if (dlr_.getId()==auftrag.getDlr_id())
			{
			for (Leistungsspektrum ls : dlr_.getLeistungsspektren())
				{
					for (Dienstleistung dln_ : ls.getDienstleistungen())
					{
						for (String s : dln) {
							if (Integer.parseInt(s)==dln_.getDlnId())
							{
								dln_.setLs_id(ls.getId());
								dlnList.add(dln_);
								System.out.println(dln_.getName());
							}
						}
					}
				}
			}
		}
		auftrag.setStatus("Nicht gesendet");
		auftrag.setDienstleistungList(dlnList);
		
		this.auftrag = auftrag;
		
		model.addAttribute("auftrag", this.auftrag);
		

		return "neuerAuftrag";
	}
	
	@RequestMapping(value = "/loeschenDnlausAuftrag", method = RequestMethod.GET)
	public String loeschenDnlausAuftrag(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		int dlnId = Integer.parseInt(req.getParameter("DnlID"));
		int lsID = Integer.parseInt(req.getParameter("LsID"));
		System.out.println(dlnId + " dln - ls " + lsID);
		
		this.auftrag.delDienstleistung(dlnId, lsID);
		
		model.addAttribute("auftrag", this.auftrag);
		
		return "neuerAuftrag";
	}
	
	@RequestMapping(value = "/sendenAuftrag", method = RequestMethod.GET)
	public String sendenAuftrag(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		//int dlnId = Integer.parseInt(req.getParameter("DnlID"));
		//int lsID = Integer.parseInt(req.getParameter("LsID"));
		//System.out.println(dlnId + " dln - ls " + lsID);
		
		//this.auftrag.delDienstleistung(dlnId, lsID);
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//date = format.format(this.auftrag.getDate());
		
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
				
		System.out.println(this.auftrag.getDateTagDB());
		System.out.println(this.auftrag.getGebaeude().getId());
		DBManager dbm = new DBManager();
		
		this.auftrag.setStatus("Warte auf eine Antwort");
		
		String sql = "INSERT INTO auftraege (aft_dma_id, aft_dlr_id, aft_dmadlr_id, aft_dlrdma_id, aft_datum, aft_status, aft_geb_id) "
				+ "values('" + this.auftrag.getDma_idl() + "', '" + this.auftrag.getDlr_id() +"', '" + this.auftrag.getDma_idl() + "', '" + this.auftrag.getDlr_id() +"', '" + this.auftrag.getDateTagDB() + "', '" + this.auftrag.getStatus() + "', '" + this.auftrag.getGebaeude().getId() + "');";
		
		int id = dbm.setAuftrag(sql);
		
		this.auftrag.setId(id);
		
		for (Dienstleistung dnl : this.auftrag.getDienstleistungList())
		{
			sql = "INSERT INTO lnaftdln (lad_aft_id, lad_dln_id) values('" + this.auftrag.getId() + "', '" + dnl.getDlnId() + "');";
			dbm.update(sql);
		}
		//model.addAttribute("auftrag", this.auftrag);
		user.setAuftraege(this.auftrag);
		
		return "dezernatmitarbeiter";
	}

	
	@RequestMapping(value = "/oeffnenAuftragDLR", method = RequestMethod.GET)
	public String aenderungAuftraegeDLR(HttpServletRequest req, HttpServletResponse res, Model model)
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
		view = "auftragDLR";
		return view;

	}
	
	@RequestMapping(value = "/oeffnenAuftragDMA", method = RequestMethod.GET)
	public String aenderungAuftraegeDMA(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.AuftragID = Integer.parseInt(req.getParameter("AuftragID"));
		String view;
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
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
		view = "auftragDMA";
		return view;

	}
	
	@RequestMapping(value = "/erstellenAuftragDMA", method = RequestMethod.GET)
	public String erstellenAuftragDMA(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		
		String view;
		
		//this.AuftragID = Integer.parseInt(req.getParameter("AuftragID"));
		
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Gebaeude> gebaeudeList = new ArrayList<Gebaeude>();
		String sql = "SELECT * from gebaeude where geb_dma_id = " + user.getId() + ";";
		//System.out.println("1");
		
		DBManager dbm = new DBManager();	
		gebaeudeList = dbm.getGebaeudeList(sql);

		if (gebaeudeList == null)
		{
			gebaeudeList = new ArrayList<Gebaeude>();
		}
		
		List<Dienstleister> dlrList = new ArrayList<Dienstleister>();
		sql = "SELECT * from dienstleister";
		dlrList = dbm.getUserDLRList(sql);
		
		List<Lnlspdln> lnlspdlnList = new ArrayList<Lnlspdln>();
		List<Leistungsspektrum> leistungsspektrumList = new ArrayList<Leistungsspektrum>();
		List<Dienstleister> dlrListNew = new ArrayList<Dienstleister>();
		
		for (Dienstleister dlr : dlrList)
		{
			List<Leistungsspektrum> lsList = new ArrayList<Leistungsspektrum>();
			sql = "SELECT * FROM leistungsspektren where lsp_dlr_id = " + dlr.getId();
			lsList = dbm.getLeistungsspektren(sql);
			//System.out.println(dlr.getFirmaname());
				for (Leistungsspektrum ls : lsList)
				{
					lnlspdlnList = new ArrayList<Lnlspdln>();
					sql = "SELECT * from lnlspdln where lld_lsp_id = " + ls.getId();
					lnlspdlnList = dbm.getLnlspdln(sql);
					//System.out.println(ls.getName());
					if (lnlspdlnList!=null) {
						List<Dienstleistung> dlnList = new ArrayList();
						for(Lnlspdln ln : lnlspdlnList)
						{
							Dienstleistung dln = null;
							sql = "SELECT * from dienstleistungen where dln_id = " + ln.getLld_dln_id();
							dln = dbm.getDienstleistung(sql);
							dln.setPreis(ln.getLld_preis());
							dlnList.add(dln);
							//System.out.println(dln.getName());
						}
						ls.setDienstleistungen(dlnList);
					}
					
			}
			dlr.setLeistungsspektren(lsList);
			dlrListNew.add(dlr);
				
		}
		
		this.dienstleisterList = dlrListNew;
		
		
		List<Integer> select = new ArrayList<Integer>();
		List<Dienstleistung> dienstleistungenList = new ArrayList<Dienstleistung>();
		sql = "SELECT * from dienstleistungen";
		dienstleistungenList = dbm.getDienstleistungen(sql);
		select.add(1);
		select.add(2);
		model.addAttribute("dienstleiter", dlrList);
		model.addAttribute("gebaeude", gebaeudeList);
		model.addAttribute("leistungen", dienstleistungenList);
		req.setAttribute("selectedDln", select);
		
		view = "erstellenAuftrag";
		return view;

	}
	
	@RequestMapping(value = "/aenderungStatusDLR", method = RequestMethod.GET)
	public String aenderungStatusDLR(HttpServletRequest req, HttpServletResponse res, Model model)
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
			String sql = "UPDATE auftraege SET aft_dlrdma_id = NULL WHERE aft_id = " + AuftragID;
			DBManager dbm = new DBManager();
			dbm.update(sql);
			user.delAuftrag(AuftragID);
			warte_auftrag = warteAuftrag(user);
			model.addAttribute("warte_auftrag", warte_auftrag);
			pruefungAuftrag();
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

		view = "auftragDLR";
		return view;
	}
	
	public String warteAuftrag(Dienstleister dlr)
	{
		int auftrag_warte = 0;
		String warte_auftrag = ""; 
		
		for (Auftrag at : dlr.getAuftraegeList())
		{
			if (at.getStatus().equals("Warte auf eine Antwort"))
			{
			auftrag_warte ++;
			};
		}
		
		if (auftrag_warte!=0) warte_auftrag = String.valueOf(auftrag_warte);
		return warte_auftrag;
	}
	
	@RequestMapping(value = "/aenderungStatusDMA", method = RequestMethod.GET)
	public String aenderungStatusDMA(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view;
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		int status_int = Integer.parseInt(req.getParameter("status"));
		String status = "";
		String sql = "";
		DBManager dbm = new DBManager();
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
			sql = "UPDATE auftraege SET aft_dmadlr_id = NULL WHERE aft_id = " + AuftragID;
			dbm.update(sql);
			user.delAuftrag(AuftragID);
			pruefungAuftrag();
			return "dezernatmitarbeiter";
		case 5:
			sql = "DELETE FROM auftraege WHERE aft_id = " + AuftragID;
			dbm = new DBManager();
			dbm.update(sql);
			user.delAuftrag(AuftragID);
			return "dezernatmitarbeiter";
		}
		
		view = "dezernatmitarbeiter";
		return view;
	}
	
	public void pruefungAuftrag() {
		DBManager dbm = new DBManager();
		String sqlDelAuftrag = "DELETE FROM auftraege WHERE aft_id = " + AuftragID + " AND aft_dmadlr_id IS NULL AND aft_dlrdma_id IS NULL";
		String sqlDelAuftragDnl = "DELETE FROM lnaftdln WHERE lad_aft_id IS NULL";

		try {
			dbm.update(sqlDelAuftrag);
			dbm.update(sqlDelAuftragDnl);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
