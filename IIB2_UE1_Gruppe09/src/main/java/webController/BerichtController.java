package webController;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.LnDokumentiert;
import beansDB.Maengel;
import manage.DBManager;

@Controller
public class BerichtController { 


	@RequestMapping(value = { "/", "maengelBericht" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Maengel> maengel = new ArrayList<Maengel>();
		List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>();
		List<Dienstleister> dienstleistern = new ArrayList<Dienstleister>();
		List<Dienstleistung> dienstleistungen = new ArrayList<Dienstleistung>();

		DBManager dbm = new DBManager();

		String sql = "SELECT * from maengel;";
		//
		maengel = dbm.getMaengel(sql);

		sql = "SELECT * from lndokumentiert;";
		berichte = dbm.getBerichte(sql);

		sql = "SELECT * from dienstleister;";
		dienstleistern = dbm.getDienstleister(sql);

		sql = "SELECT * from dienstleistungen;";
		dienstleistungen = dbm.getDienstleistungen(sql);

		req.getSession().setAttribute("maengel", maengel); // set session attribute
		model.addAttribute("maengel", maengel);
		req.getSession().setAttribute("berichte", berichte); // set session attribute for my buildings 

		req.getSession().setAttribute("dienstleister", dienstleistern); // set session attribute for my buildings
		model.addAttribute("dienstleister", dienstleistern);
 
		req.getSession().setAttribute("dienstleistungen", dienstleistungen); // set session attribute for my buildings
		model.addAttribute("dienstleistungen", dienstleistungen);


		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		// return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";

		return "redirect:/dezernatmitarbeiter.jsp";
	}

	@RequestMapping(value = "/hinzufuegenBerichtForm", method = RequestMethod.POST)
	public String hinzufuegenBerichtForm(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException, ParseException {
		
		SimpleDateFormat f1 = new SimpleDateFormat("dd.MM.yyyy");
		Date date1 = new Date( );
		date1 = f1.parse(req.getParameter("date"));
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date dateDB = java.sql.Date.valueOf(f2.format(date1));
		
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		DBManager dbm = new DBManager();
		String titel = req.getParameter("titel");
		String bes = req.getParameter("bes");
		 
		int auftrag_id = Integer.parseInt(req.getParameter("auftrag"));
		int dlrID = 0;
		String[] dlnAr = req.getParameterValues("dienstleistungen");
		String sql = ""; 
		int mglID; 
		
		sql = "INSERT INTO maengel (mgl_aft_id)  VALUES (" + auftrag_id + ");";
		mglID = dbm.setMaengel(sql);  
		sql = "INSERT INTO lndokumentiert (ldo_dma_id, ldo_mgl_id, ldo_date, ldo_titel, ldo_bes)  VALUES (" + user.getId()
				+ "," + mglID + ", \"" + dateDB + "\", \"" + titel + "\", \"" + bes + "\");";
		dbm.update(sql); 

		sql = "SELECT * from lndokumentiert;";
		List<LnDokumentiert> berichte = dbm.getBerichte(sql);
 
		model.addAttribute("berichte", berichte);
		//view = "dezernatmitarbeiter";
		return "dezernatmitarbeiter";
	}
	@RequestMapping(value="/loeschenBericht", method = RequestMethod.POST)
	public String loeschenBericht(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException{ 
		int ldoID = Integer.parseInt(req.getParameter("ldoID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 
		List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>();

		String sql = "";
		DBManager dbm = new DBManager();
		sql="SELECT * from ldokumentiert WHERE ldo_id = " + ldoID + ";";
		int mglID = dbm.getMaengelID(sql);

		sql = "DELETE FROM lndokumentiert WHERE ldo_id = " + ldoID + ";";
		dbm.update(sql);

		sql = "DELETE FROM maengel WHERE mgl_id = " + mglID + ";";
		dbm.update(sql);

		sql = "SELECT * from lndokumentiert;";
		berichte = dbm.getBerichte(sql);

		model.addAttribute("berichte", berichte); 
		return "dezernatmitarbeiter";
	}
	@RequestMapping(value = "/aenderungBericht", method = RequestMethod.POST)
	public String aenderungGebaeude(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		int ldoID = Integer.parseInt(req.getParameter("ldoID"));
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		String view;
		List<LnDokumentiert> berichte = new ArrayList<LnDokumentiert>(); 
		DBManager dbm = new DBManager();

		String sql = "SELECT * from lndokumentiert;";
		//
		berichte = dbm.getBerichte(sql);
 
		LnDokumentiert ldoToEdit = null;
		for(LnDokumentiert ld: berichte) {  
			if(ld.getId() == ldoID) {
				ldoToEdit= ld;
				break;
			}
		}
		model.addAttribute("berichte", berichte); 
		model.addAttribute("ldoToEdit", ldoToEdit);
		view = "/aenderungBericht";
		return view;

	}

	@RequestMapping(value = "/aenderungBerichtForm", method = RequestMethod.POST)
	public String verifying(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException, ParseException {

		int ldoID = Integer.parseInt(req.getParameter("ldoID"));

		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user"); 
		DBManager dbm = new DBManager();
		SimpleDateFormat f1 = new SimpleDateFormat("dd.MM.yyyy");
		Date date1 = new Date( );
		date1 = f1.parse(req.getParameter("date"));
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date dateDB = java.sql.Date.valueOf(f2.format(date1));
		  
		String titel = req.getParameter("titel");
		String bes = req.getParameter("bes");
		int aft_id = Integer.parseInt(req.getParameter("auftrag"));
		String sql = "SELECT * from lndokumentiert;";
		//
		List<LnDokumentiert>berichte = dbm.getBerichte(sql);
 
		LnDokumentiert ldoToEdit = null;
		for(LnDokumentiert ld: berichte) {  
			if(ld.getId() == ldoID) {
				ldoToEdit= ld;
				break;
			}
		}
		
		List<Maengel> mL = dbm.getMaengel("SELECT * from maengel WHERE mgl_id = " + ldoToEdit.getMgl_id());
		
		
		if(mL.size() == 1 && mL.get(0).getAuftrag() != aft_id) {
			
		}
		else if(mL.isEmpty()) {
			System.out.println("Hola");
		}
		

		String sql = "UPDATE lndokumentiert SET ldo_titel = \" " + titel + "\" , ldo_date = \"" + dateDB + "\" , ldo_bes = \""
				+ bes + "\" ;";
		dbm.update(sql);

		sql = "SELECT * from lndokumentiert;";
		List<LnDokumentiert> berichte = dbm.getBerichte(sql);
		model.addAttribute("berichte", berichte); 

		return "redirect:/dezernatmitarbeiter.jsp";

	}

}
