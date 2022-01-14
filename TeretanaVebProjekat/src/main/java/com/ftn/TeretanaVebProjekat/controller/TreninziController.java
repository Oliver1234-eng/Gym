package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/treninzi")
public class TreninziController implements ServletContextAware {

	//public static final String KNJIGE_ZA_IZNAJMLJIVANJE = "treninzi_za_zakazivanje";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private TreningService treningService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
	@GetMapping
	public ModelAndView index() {
		List<Trening> treninzi = treningService.findAll();		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("treninzi"); // naziv template-a
		rezultat.addObject("treninzi", treninzi); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
//	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev */
//	@GetMapping(value="/add")
//	public String create(HttpSession session, HttpServletResponse response){
//		return "dodavanjeKnjige"; // stranica za dodavanje knjige
//	}
//
//	/** obrada podataka forme za unos novog entiteta, post zahtev */
//	// POST: knjige/add
//	@SuppressWarnings("unused")
//	@PostMapping(value="/add")
//	public void create(@RequestParam String naziv, @RequestParam String registarskiBrojPrimerka,  
//			@RequestParam String jezik, @RequestParam int brojStranica, 
//			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, HttpServletResponse response) throws IOException {		
//		Knjiga knjiga = new Knjiga(naziv, registarskiBrojPrimerka, jezik, brojStranica, datum);
//		Knjiga saved = knjigaService.save(knjiga);
//		response.sendRedirect(bURL+"knjige");
//	}
//	
//	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
//	// POST: knjige/edit
//	@SuppressWarnings("unused")
//	@PostMapping(value="/edit")
//	public void Edit(@RequestParam Long id, @RequestParam String naziv, @RequestParam String registarskiBrojPrimerka,  
//			@RequestParam String jezik, @RequestParam int brojStranica, 
//			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum , HttpServletResponse response) throws IOException {	
//		Knjiga knjiga = knjigaService.findOne(id);
//		if(knjiga != null) {
//			if(naziv != null && !naziv.trim().equals(""))
//				knjiga.setNaziv(naziv);
//			if(jezik != null && !jezik.trim().equals(""))
//				knjiga.setJezik(jezik);
//			if(brojStranica > 0)
//				knjiga.setBrojStranica(brojStranica);
//			if(datum != null)
//				knjiga.setDatum(datum);
//		}
//		Knjiga saved = knjigaService.update(knjiga);
//		response.sendRedirect(bURL+"knjige");
//	}
//	
//	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
//	// POST: knjige/delete
//	@SuppressWarnings("unused")
//	@PostMapping(value="/delete")
//	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
//		Knjiga deleted = knjigaService.delete(id);
//		response.sendRedirect(bURL+"knjige");
//	}
//	
//	/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev */
//	@GetMapping(value="/details")
//	@ResponseBody
//	public ModelAndView details(@RequestParam Long id) {	
//		Knjiga knjiga = knjigaService.findOne(id);
//		
//		// podaci sa nazivom template-a
//		ModelAndView rezultat = new ModelAndView("knjiga"); // naziv template-a
//		rezultat.addObject("knjiga", knjiga); // podatak koji se šalje template-u
//
//		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
//	}
	
	
}