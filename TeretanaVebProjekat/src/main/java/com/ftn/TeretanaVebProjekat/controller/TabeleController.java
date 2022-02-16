package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Tabela;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TabelaService;
import com.ftn.TeretanaVebProjekat.service.TerminService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/Tabele")
public class TabeleController {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final String minDate = LocalDate.MIN.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String maxDate = LocalDate.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String minTime = LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm"));
	public static final String maxTime = LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm"));
	
	@Autowired
	private TabelaService tabelaService;

	@Autowired
	private TreningService treningService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {
		baseURL = servletContext.getContextPath() + "/";	
	}

	@GetMapping
	public ModelAndView Index(
			@RequestParam(required=false) Long treningId, @RequestParam(required=false) String korisnik,
			@RequestParam(required=false) Integer cenaOd, @RequestParam(required=false) Integer cenaDo,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumDo, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeDo,  
			HttpSession session) throws IOException {
		
		// čitanje
		LocalDateTime datumIVremeOd = null;
		if(datumOd!=null || vremeOd!=null)
			datumIVremeOd = LocalDateTime.of(datumOd, vremeOd);
		LocalDateTime datumIVremeDo = null;
		if(datumDo!=null || vremeDo!=null)
			datumIVremeDo = LocalDateTime.of(datumDo, vremeDo);
		
		List<Tabela> tabele = tabelaService.find(treningId, korisnik, cenaOd, cenaDo, datumIVremeOd, datumIVremeDo);
		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("tabele");
		rezultat.addObject("tabele", tabele);
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView Details(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		Tabela tabela = tabelaService.findOne(id);
		if (tabela == null) {
			response.sendRedirect(baseURL + "Tabele");
			return null;
		}

		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("tabela");
		rezultat.addObject("tabela", tabela);
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public ModelAndView Create(@RequestParam(name="treningId", required=false) Long treningId, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || prijavljeniKorisnik.isAdministrator() == false) {
			response.sendRedirect(baseURL + "prijava.html");
			return null;
		}

		// čitanje
		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("dodavanjeTabele");
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void Create(
			@RequestParam Long treningId, @RequestParam(defaultValue="mika") String korisnik,
			@RequestParam int cena, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}

		// validacija
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}

		// kreiranje
		Tabela tabela = new Tabela(trening, korisnik, cena, datumIVreme);
		tabelaService.save(tabela);
		
		response.sendRedirect(baseURL + "Tabele");
	}

	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id, @RequestParam Long treningId, @RequestParam String korisnik,
			@RequestParam int cena,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme,  
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}

		// validacija
		Tabela tabela = tabelaService.findOne(id);
		if (tabela == null) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}
		
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}

		// izmena
		tabela.setTrening(trening);
		tabela.setKorisnik(korisnik);
		tabela.setCena(cena);
		tabela.setDatumIVreme(datumIVreme);
		tabelaService.update(tabela);
	
		response.sendRedirect(baseURL + "Tabele");
	}

	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Tabele");
			return;
		}

		// brisanje
		tabelaService.delete(id);

		response.sendRedirect(baseURL + "Tabele");
	}

}
