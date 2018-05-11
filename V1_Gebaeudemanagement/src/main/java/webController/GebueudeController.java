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
public class GebueudeController {
	
	public int GebaeudeID = 0;
	
	@RequestMapping(value = { "/", "gebaeude" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
	    Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		
		List<Gebaeude> gebAll = new ArrayList<Gebaeude>();
		List<Gebaeude> gebForID = new ArrayList<Gebaeude> ();
		
		DBManager dbm = new DBManager();	

		String sql = "SELECT geb_id from gebaeude;";
		//String sql2 = "SELECT geb_id from gebaeude WHERE geb_dma_id = " + dma.getId() + ";";
		gebAll = dbm.getGeb(sql);
		
		for(Gebaeude g: gebAll) {
			if(g.getDma_id() == dma.getId())
				gebForID.add(g);
		}

		req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
		model.addAttribute("gebaeude", gebAll);
		req.getSession().setAttribute("mGebaude", gebForID); // set session attribute for my buildings
		model.addAttribute("mGebaeude", gebForID);
	
		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		//return "redirect:/" + dlr.getFachrolle().toLowerCase() + ".jsp";
	 
		return "/dezernatmitarbeiter";
	}

	@RequestMapping(value = "/aenderungGebaeude", method = RequestMethod.GET)
	public String aenderungLeistung(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {

		this.GebaeudeID = Integer.parseInt(req.getParameter("GebaeudeID"));
		String view;
		Dezernatmitarbeiter user = (Dezernatmitarbeiter) req.getSession().getAttribute("user");
		
			
		//List<Gebaeude> gebaeudeList = (List<Gebaeude>) user.getGebaeude();
		Gebaeude gebaeude = new Gebaeude();
		/*
		for (Gebaeude geb : gebaeudeList) {
			if(geb.getId()==GebaeudeID) {
				gebaeude = geb;
				break;
			}
		}
		*/
		model.addAttribute("gebaeude", gebaeude);
		view = "aenderungGebaeude";
		System.out.println("1234");
		return view;

	}
		
}
