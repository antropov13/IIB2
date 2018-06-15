package webController;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.apstex.ifc4toolbox.ifcmodel.IfcModel;

import org.springframework.stereotype.Controller;  

import beansDB.Dezernatmitarbeiter;
import beansDB.Gebaeude;
import ifcClasses.ModelIfc;
import manage.DBManager;

@Controller
public class IFCController {
	@RequestMapping(value = { "/", "ifc" })
	public String home(HttpServletRequest req, HttpServletResponse res, Model model)
			throws ClassNotFoundException, SQLException {
		Dezernatmitarbeiter dma = (Dezernatmitarbeiter) req.getSession().getAttribute("user");

		List<Gebaeude> gebAll = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager();

		String sql = "SELECT * from gebaeude;";
		//
		gebAll = dbm.getGeb(sql);
 

		req.getSession().setAttribute("gebaeude", gebAll); // set session attribute
		model.addAttribute("gebaeude", gebAll); 
		req.getSession().setAttribute("user", dma); // set session attribute
		model.addAttribute("user", dma);
		System.out.println("File" + req.getSession().getAttribute("ifcCreated"));

		return "dezernatmitarbeiter";
	}
	@RequestMapping(value = "/readIfcModel", method=RequestMethod.POST)
	public String addModelfromFile(HttpServletRequest req, HttpServletResponse res, Model model) {
		String path = req.getSession().getServletContext().getRealPath("") + File.separator + "example.ifc";
		ModelIfc mI = new ModelIfc(path);
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value = "/exportIfcModel", method=RequestMethod.POST)
	public String getModelfromDB(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception {
	 

		String path = req.getSession().getServletContext().getRealPath("") + File.separator + "example.ifc";
		ModelIfc mI = new ModelIfc(); 
		List<Gebaeude> gebL = new ArrayList<Gebaeude>(); 
		DBManager dbm = new DBManager(); 
		String sql = "SELECT * from gebaeude;";
		gebL = dbm.getGeb(sql);
		mI.createGebaeude(gebL);
		mI.getFile(path);
		Boolean ifcCreated = true;
		model.addAttribute("ifcCreated", ifcCreated);
		return "dezernatmitarbeiter";
	}
}
