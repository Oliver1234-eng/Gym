package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;
import com.ftn.TeretanaVebProjekat.service.ClanskaKartaService;
import com.ftn.TeretanaVebProjekat.service.KorisnikService;
import com.ftn.TeretanaVebProjekat.service.TerminService;
import com.ftn.TeretanaVebProjekat.service.TreningKorpaService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/ClanskeKarte")
public class ClanskeKarteController implements ServletContextAware {
	
	public static final String CLANSKE_KARTE_KEY = "clanske_karte";
	public static final String KORISNICI_KEY = "korisnici";
	public static final String CLANSKA_KARTA = "clanska_karta";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private ClanskaKartaService clanskaKartaService;
	
	@Autowired
	private TreningKorpaService treningKorpaService;

	/** pristup ApplicationContext */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@GetMapping
	public ModelAndView index() {
		List<ClanskaKarta> clanskeKarte = clanskaKartaService.findAll();		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("clanskeKarte"); // naziv template-a
		rezultat.addObject("clanskeKarte", clanskeKarte); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public ModelAndView create() {
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("dodavanjeClanskeKarte"); // naziv template-a

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: clanskeKarte/add
	@PostMapping(value="/add")
	public void create(@RequestParam int popust, @RequestParam int brojPoena,
			@RequestParam String registarskiBroj, @RequestParam String korisnik,
			@RequestParam String status, HttpServletResponse response) throws IOException {				
	
		ClanskaKarta clanskaKarta = new ClanskaKarta(popust, brojPoena, registarskiBroj, korisnik, status);
		clanskaKartaService.save(clanskaKarta);
		response.sendRedirect(bURL+"ClanskeKarte");
	}
	
	// POST: clanskeKarte/izdajKnjigu -> izdajKnjige sve sa sesije
	@SuppressWarnings("unchecked")
	@PostMapping(value="/zakaziTreninge")
	public void izdajKnjigu(@RequestParam String registarskiBroj, HttpSession session, HttpServletResponse response) throws IOException {		
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);
		if (ck == null) {
			//todo
		}
		List<TreningKorpa> zaZakazivanje = (List<TreningKorpa>) session.getAttribute(TreninziKorpaController.TRENINZI_ZA_ZAKAZIVANJE);	

		for (TreningKorpa k : zaZakazivanje) {
			if (ck != null) {
				ck.getZakazaniTreninzi().add(k);
				ck = clanskaKartaService.update(ck);
				if (ck != null) {
					k.setZakazan(true);
					treningKorpaService.update(k);
				}
			}	
		}
		
		session.setAttribute(TreninziKorpaController.TRENINZI_ZA_ZAKAZIVANJE, new ArrayList<TreningKorpa>());

		response.sendRedirect(bURL+"TreninziKorpa");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam String registarskiBroj) {
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("clanskaKarta"); // naziv template-a
		rezultat.addObject("clanskaKarta", ck); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	// POST: clanskeKarte/vratiKnjigu
	@PostMapping(value="/otkaziTrening")
	public void vratiKnjigu(@RequestParam Long idTreninga, @RequestParam String registarskiBroj, HttpServletResponse response) throws IOException {		
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);

		if (ck != null) {
			TreningKorpa treningKorpa = treningKorpaService.findOne(idTreninga);
			ck.getZakazaniTreninzi().remove(treningKorpa);
			ck = clanskaKartaService.update(ck);
			if (ck != null) {
				treningKorpa.setZakazan(false);
				treningKorpaService.update(treningKorpa);
			}
		}

		response.sendRedirect(bURL+"TreninziKorpa");
	}

}
