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

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.Leistungsspektrum;
import manage.DBManager;

@Controller
public class GebaeudeController {

	public int GebaeudeID = 0;

	@RequestMapping(value = { "/", "gebaeude" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Gebaeude> gebAll = new ArrayList<Gebaeude>();
		List<Gebaeude> gebForID = new ArrayList<Gebaeude>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from gebaeude;";
		//
		gebAll = dbm.getGeb(sql);

		for (Gebaeude g : gebAll) {
			if (g.getDma_id() == dma.getId())
				gebForID.add(g);
		}

		req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
		model.addAttribute("gebaeude", gebAll);
		req.getSession().setAttribute("mGebaude", gebForID); // set session attribute for my buildings
		model.addAttribute("mGebaeude", gebForID);

		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		// return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";

		return "/dezernatmitarbeiter";
	}

	@RequestMapping(value = "/aenderungGebaeude", method = RequestMethod.GET)
	public String aenderungGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		List<Gebaeude> gebAll = new ArrayList<Gebaeude>();
		List<Gebaeude> gebForID = new ArrayList<Gebaeude>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from gebaeude;";
		//
		gebAll = dbm.getGeb(sql);

		sql = "SELECT * from gebaeude WHERE geb_dma_id = " + user.getId() + ";";
		gebForID = dbm.getGeb(sql);
		model.addAttribute("gebaeude", gebAll);
		model.addAttribute("mGebaeude", gebForID);
		model.addAttribute("gebToEdit", GebaeudeID);
		view = "/aenderungGebaeude";
		return view;

	}

	@RequestMapping(value = "/aenderungGebaeudeForm", method = RequestMethod.POST)
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Gebaeude> gebaeude = (List<Gebaeude>) req.getSession().getAttribute("gebaeude");
		List<Gebaeude> mGebaeude = new ArrayList<Gebaeude>();
		DBManager dbm = new DBManager();
		String str = req.getParameter("strasse");
		String nr = req.getParameter("nr");
		String ort = req.getParameter("ort");
		int plz = Integer.parseInt(req.getParameter("plz"));

		for (Gebaeude g : gebaeude) {
			if (g.getId() == GebaeudeID) {
				g.setStrasse(str);
				g.setHausnummer(nr);
				g.setOrt(ort);
				g.setPlz(plz);
			}
			if (g.getDma_id() == user.getId()) {
				mGebaeude.add(g);
			}
		}

		String sql = "UPDATE gebaeude SET geb_strasse = \" " + str + "\" , geb_hausnr = \"" + nr + "\" , geb_ort = \""
				+ ort + "\" , geb_plz = " + plz + " where geb_id = " + GebaeudeID + ";";
		dbm.update(sql);
		/*
		 * for (Gebaeude geb : gebaeudeList) { if(geb.getId()==GebaeudeID) { gebaeude =
		 * geb; break; } }
		 */
		model.addAttribute("gebaeude", gebaeude);
		model.addAttribute("mGebaeude", mGebaeude);

		return "/aenderungGebaeude";

	}

	@RequestMapping(value = "/loeschenGebaeude", method = RequestMethod.GET)
	public String loeschenGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Gebaeude> mGebaeude = new ArrayList<Gebaeude>();
		List<Gebaeude> gebaeude = (List<Gebaeude>) req.getSession().getAttribute("gebaeude");

		String sql = "";
		DBManager dbm = new DBManager();

		Iterator<Gebaeude> it = gebaeude.iterator();
		while (it.hasNext()) {
			Gebaeude g = it.next();
			if (g.getId() == GebaeudeID) {
				sql = "DELETE FROM gebaeude WHERE geb_id = " + GebaeudeID + ";";
				it.remove();
			}
		}

		dbm.update(sql);
		model.addAttribute("gebaeude", gebaeude);
		req.getSession().setAttribute("gebaeude", gebaeude); // set session attribute
		sql = "SELECT * from gebaeude WHERE geb_dma_id = " + user.getId() + ";";
		mGebaeude = dbm.getGeb(sql);
		model.addAttribute("mGebaeude", mGebaeude);

		req.getSession().setAttribute("mGebaeude", mGebaeude); // set session attribute
		return "dezernatmitarbeiter";
	}

	@RequestMapping(value = "/hinzufuegenGebaeude", method = RequestMethod.GET)
	public String hinzufuegenGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		return "/hinzufuegenGebaeude";
	}
	
	@RequestMapping(value = "/hinzufuegenGebaeudeForm", method = RequestMethod.POST)
	public String hinzufuegennGebaeudeForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String str = req.getParameter("strasse");
		String nr = req.getParameter("nr");
		String ort = req.getParameter("ort");
		int plz = Integer.parseInt(req.getParameter("plz"));
 
		String sql = "INSERT INTO gebaeude (geb_strasse, geb_hausnr, geb_ort, geb_plz, geb_dma_id)  VALUES (\"" 
						+ str + "\", \""+ nr + "\", \"" + ort + "\", " + plz + ", " + user.getId() + ");";
		dbm.update(sql);
		Gebaeude geb = new Gebaeude();
		geb.setStrasse(str);
		geb.setHausnummer(nr);
		geb.setOrt(ort);
		geb.setPlz(plz);
		geb.setDma_id(user.getId());
		
		List<Gebaeude> gebaeude;
		List<Gebaeude> mGebaeude =  new ArrayList<Gebaeude>();
		mGebaeude = (List<Gebaeude>) req.getSession().getAttribute("mGebaeude");
		 gebaeude = (List<Gebaeude>) req.getSession().getAttribute("gebaeude"); 
		 if(gebaeude == null) {
			gebaeude = new ArrayList<Gebaeude>();
		 }
		 if(mGebaeude == null) {
				mGebaeude = new ArrayList<Gebaeude>();
			 }
		gebaeude.add(geb);
		mGebaeude.add(geb);
		model.addAttribute("gebaeude", gebaeude);
		model.addAttribute("mGebaeude", mGebaeude);
		return "/dezernatmitarbeiter";
	}
}
