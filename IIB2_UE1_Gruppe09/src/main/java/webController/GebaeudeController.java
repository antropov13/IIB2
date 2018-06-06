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
import beansDB.Gebaeude; 
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

		sql = "SELECT * from gebaeude WHERE geb_dma_id = " + dma.getId() + ";";
		gebForID = dbm.getGeb(sql);

		req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
		model.addAttribute("gebaeude", gebAll); 
		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		// return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";

		return "redirect:/dezernatmitarbeiter.jsp";
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
		Gebaeude gebToEdit = null;
		for(Gebaeude g: gebAll) {  
			if(g.getId() == GebaeudeID) {
				gebToEdit= g;
				break;
			}
		}
		model.addAttribute("gebaeude", gebAll); 
		model.addAttribute("gebToEdit", gebToEdit);
		view = "/aenderungGebaeude";
		return view;

	}

	@RequestMapping(value = "/aenderungGebaeudeForm", method = RequestMethod.POST)
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		List<Gebaeude> gebaeude = (List<Gebaeude>) req.getSession().getAttribute("gebaeude"); 
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
		}

		String sql = "UPDATE gebaeude SET geb_strasse = \" " + str + "\" , geb_hausnr = \"" + nr + "\" , geb_ort = \""
				+ ort + "\" , geb_plz = " + plz + " where geb_id = " + GebaeudeID + ";";
		dbm.update(sql);

		sql = "SELECT * from gebaeude;";
		gebaeude = dbm.getGeb(sql); 

		return "redirect:/dezernatmitarbeiter.jsp";

	}

	@RequestMapping(value = "/loeschenGebaeude", method = RequestMethod.POST)
	public String loeschenGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		this.GebaeudeID = Integer.parseInt(req.getParameter("gebID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 
		List<Gebaeude> gebaeude = new ArrayList<Gebaeude>();

		String sql = "";
		DBManager dbm = new DBManager();

		sql = "DELETE FROM gebaeude WHERE geb_id = " + GebaeudeID + ";";

		dbm.update(sql);

		sql = "SELECT * from gebaeude;";
		gebaeude = dbm.getGeb(sql);
 
		model.addAttribute("gebaeude", gebaeude); 
		view = "redirect:/dezernatmitarbeiter.jsp";
		return view;
	}

	@RequestMapping(value = "/hinzufuegenGebaeude", method = RequestMethod.GET)
	public String hinzufuegenGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		return "/hinzufuegenGebaeude";
	}

	@RequestMapping(value = "/hinzufuegenGebaeudeForm", method = RequestMethod.POST)
	public String hinzufuegenGebaeudeForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		String view = "";
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String str = req.getParameter("strasse");
		String nr = req.getParameter("nr");
		String ort = req.getParameter("ort");
		int plz = Integer.parseInt(req.getParameter("plz"));

		String sql = "INSERT INTO gebaeude (geb_strasse, geb_hausnr, geb_ort, geb_plz, geb_dma_id)  VALUES (\"" + str
				+ "\", \"" + nr + "\", \"" + ort + "\", " + plz + ", " + user.getId() + ");";
		dbm.update(sql);
		Gebaeude geb = new Gebaeude();
		geb.setStrasse(str);
		geb.setHausnummer(nr);
		geb.setOrt(ort);
		geb.setPlz(plz);
		geb.setDma_id(user.getId());

		sql = "SELECT * from gebaeude;";
		List<Gebaeude> gebaeude = dbm.getGeb(sql);
 
		model.addAttribute("gebaeude", gebaeude); 
		view = "redirect:/dezernatmitarbeiter.jsp";
		return view;
	}
}
