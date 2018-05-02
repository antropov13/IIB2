package webController;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;


import beansDB.Fachrolle;

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

		Fachrolle fr = new Fachrolle();
		fr.setFachrolle(req.getParameter("fachrolle"));
		fr.setUsername(req.getParameter("username"));
		fr.setPass(req.getParameter("password"));

		if (fr.getUsername() == "" || fr.getPass() == "" || fr.getFachrolle() == "") {
			model.addAttribute("warning", "Geben Sie bitte alle Daten ein!");
			return "index";
		} else {

			DBManager dbm = new DBManager();
			String kuerzel = "dma_";
			if (fr.getFachrolle().equals("Dienstleister")) {
				kuerzel = "dlt_";
			}
			String sql = "SELECT * FROM " + fr.getFachrolle() + " WHERE " + kuerzel + "username = '" + fr.getUsername()
					+ "' AND " + kuerzel + "passwort = '" + fr.getPass() + "';";

			fr = dbm.getUser(sql, fr.getFachrolle());
			if (fr != null) {
				req.getSession().setAttribute("user", fr); // set session attribute
				model.addAttribute("user", fr);
		
			} else {
				model.addAttribute("warning", "Passwort oder Nutzerkonto ist falsch!");
				return "index";
			}
		}
		return "redirect:/" + fr.getFachrolle().toLowerCase() + ".jsp";
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
