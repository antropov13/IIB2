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

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleistung; 
import manage.DBManager;

@Controller
public class DienstleistungController {
	int dienstleistungID = 0;
	@RequestMapping(value= { "/", "dienstleistung"})
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException { 
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
 
		List<Dienstleistung> dlnAll = new ArrayList<Dienstleistung>();
		List<Dienstleistung> dlnForID = new ArrayList<Dienstleistung>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from dienstleistungen;";
		//
		dlnAll = dbm.getDienstleistungen(sql);

		sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + user.getId() + ";";
		dlnForID = dbm.getDienstleistungen(sql);
		model.addAttribute("distleistungen", dlnAll);
		model.addAttribute("mDienstleistungen", dlnForID); 
		return "redirect:/dezernatmitarbeiter.jsp";
	}

	@RequestMapping(value = "/aenderungDienstleistung", method = RequestMethod.GET)
	public String aenderungDienstleistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		this.dienstleistungID = Integer.parseInt(req.getParameter("dlnID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		List<Dienstleistung> dlnAll = new ArrayList<Dienstleistung>();
		List<Dienstleistung> dlnForID = new ArrayList<Dienstleistung>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from dienstleistungen;";
		//
		dlnAll = dbm.getDienstleistungen(sql);

		sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + user.getId() + ";";
		dlnForID = dbm.getDienstleistungen(sql);
		model.addAttribute("distleistungen", dlnAll);
		model.addAttribute("mDienstleistungen", dlnForID);
		model.addAttribute("dlnToEdit", dienstleistungID);
		view = "/aenderungDienstleistung";
		return view;

	}

	@RequestMapping(value = "/aenderungDienstleistungForm", method = RequestMethod.POST)
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.dienstleistungID = Integer.parseInt(req.getParameter("gebID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Dienstleistung> dlnAll = (List<Dienstleistung>) req.getSession().getAttribute("dienstleistungen");
		List<Dienstleistung> dlnForID = new ArrayList<Dienstleistung>();
		DBManager dbm = new DBManager();
		String name = req.getParameter("name");
		String bes = req.getParameter("bes");
		String hgk = req.getParameter("hgk"); 

		for (Dienstleistung d : dlnAll) {
			if (d.getId() == dienstleistungID) {
				d.setName(name);
				d.setBeschreibung(bes);
				d.setHaeufigkeit(hgk); 
			}
		}

		String sql = "UPDATE dienstleistungen SET dln_name = \" " + name + "\" , dln_bes = \"" + bes + "\" , dln_hgk = \""
				+ hgk + "\" where geb_id = " + dienstleistungID + ";";
		dbm.update(sql);

		sql = "SELECT * from dienstleistungen;";
		dlnAll = dbm.getDienstleistungen(sql);

		sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + user.getId() + ";";
		dlnForID = dbm.getDienstleistungen(sql);
		model.addAttribute("dienstleistungen", dlnAll);
		model.addAttribute("mDienstleistungen", dlnForID);

		return "redirect:/dezernatmitarbeiter.jsp";

	}

	@RequestMapping(value = "/loeschenDienstleistung", method = RequestMethod.POST)
	public String loeschenDienstleistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		this.dienstleistungID = Integer.parseInt(req.getParameter("dlnID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Dienstleistung> dlnForID = new ArrayList<Dienstleistung>();
		List<Dienstleistung> dlnAll = new ArrayList<Dienstleistung>();

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM dienstleistungen WHERE delnb_id = " + dienstleistungID + ";";

		dbm.update(sql);

		sql = "SELECT * from dienstleistungen;";
		dlnAll = dbm.getDienstleistungen(sql);

		sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + user.getId() + ";";
		dlnForID = dbm.getDienstleistungen(sql);
		model.addAttribute("dienstleistungen", dlnAll);
		model.addAttribute("mDienstleistungen", dlnForID);
		view = "redirect:/dezernatmitarbeiter.jsp";
		return view;
	}

	@RequestMapping(value = "/hinzufuegenDienstleistung", method = RequestMethod.GET)
	public String hinzufuegenDienstleistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		return "/hinzufuegenDienstleistung";
	}

	@RequestMapping(value = "/hinzufuegenDlnForm", method = RequestMethod.POST)
	public String hinzufuegenDlnForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		List<Dienstleistung> dlnForID = new ArrayList<Dienstleistung>();
		List<Dienstleistung> dlnAll = new ArrayList<Dienstleistung>();

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String name = req.getParameter("name");
		String bes = req.getParameter("bes");
		String hgk = req.getParameter("hgk"); 

		String sql = "INSERT INTO dienstleistungen (dln_name, dln_bes, dln_hgk, dln_dma_id)  VALUES (\"" + name
				+ "\", \"" + bes + "\", \"" + hgk + "\", " + user.getId() + ");";
		dbm.update(sql);
		Dienstleistung d = new Dienstleistung();
		d.setName(name);
		d.setBeschreibung(bes);
		d.setHaeufigkeit(hgk); 
		d.setDmaId(user.getId());

		sql = "SELECT * from dienstleistungen;";
		dlnAll = dbm.getDienstleistungen(sql);

		sql = "SELECT * from dienstleistungen WHERE dln_dma_id = " + user.getId() + ";";
		dlnForID = dbm.getDienstleistungen(sql);
		model.addAttribute("dienstleistungen", dlnAll);
		model.addAttribute("mDienstleistungen", dlnForID);
		view = "redirect:/dezernatmitarbeiter.jsp";
		return view;
	}
}
