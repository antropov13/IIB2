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
