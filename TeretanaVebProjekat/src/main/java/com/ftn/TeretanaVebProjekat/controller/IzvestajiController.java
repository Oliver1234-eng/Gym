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

import com.ftn.TeretanaVebProjekat.model.Izvestaj;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.IzvestajiService;
import com.ftn.TeretanaVebProjekat.service.TerminService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/Izvestaji")
public class IzvestajiController {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final String minDate = LocalDate.MIN.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String maxDate = LocalDate.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String minTime = LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm"));
	public static final String maxTime = LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm"));
	
	@Autowired
	private IzvestajiService izvestajService;

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
			@RequestParam(required=false) Long treningId, @RequestParam(required=false) String trener, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumDo, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeDo,
			@RequestParam(required=false) Integer brojZakazanihTreninga,
			@RequestParam(required=false) Integer cenaOd, @RequestParam(required=false) Integer cenaDo,
			HttpSession session) throws IOException {
		
		// čitanje
		LocalDateTime datumIVremeOd = null;
		if(datumOd!=null || vremeOd!=null)
			datumIVremeOd = LocalDateTime.of(datumOd, vremeOd);
		LocalDateTime datumIVremeDo = null;
		if(datumDo!=null || vremeDo!=null)
			datumIVremeDo = LocalDateTime.of(datumDo, vremeDo);
		
		List<Izvestaj> izvestaji = izvestajService.find(treningId, trener, datumIVremeOd, datumIVremeDo, brojZakazanihTreninga, cenaOd, cenaDo);
		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("izvestaji");
		rezultat.addObject("izvestaji", izvestaji);
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView Details(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		Izvestaj izvestaj = izvestajService.findOne(id);
		if (izvestaj == null) {
			response.sendRedirect(baseURL + "Izvestaji");
			return null;
		}

		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("izvestaj");
		rezultat.addObject("izvestaj", izvestaj);
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
		ModelAndView rezultat = new ModelAndView("dodavanjeIzvestaja");
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void Create(
			@RequestParam Long treningId, @RequestParam(defaultValue="trener2") String trener,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme, 
			@RequestParam int brojZakazanihTreninga, @RequestParam int cena,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}

		// validacija
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}

		// kreiranje
		Izvestaj izvestaj = new Izvestaj(trening, trener, datumIVreme, brojZakazanihTreninga, cena);
		izvestajService.save(izvestaj);
		
		response.sendRedirect(baseURL + "Izvestaji");
	}

	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id, @RequestParam Long treningId, @RequestParam String trener,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme, 
			@RequestParam int brojZakazanihTreninga, @RequestParam int cena, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}

		// validacija
		Izvestaj izvestaj = izvestajService.findOne(id);
		if (izvestaj == null) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}
		
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}

		// izmena
		izvestaj.setTrening(trening);
		izvestaj.setTrener(trener);
		izvestaj.setDatumIVreme(datumIVreme);
		izvestaj.setBrojZakazanihTreninga(brojZakazanihTreninga);
		izvestaj.setCena(cena);
		izvestajService.update(izvestaj);
	
		response.sendRedirect(baseURL + "Izvestaji");
	}

	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Izvestaji");
			return;
		}

		// brisanje
		izvestajService.delete(id);

		response.sendRedirect(baseURL + "Izvestaji");
	}

}
