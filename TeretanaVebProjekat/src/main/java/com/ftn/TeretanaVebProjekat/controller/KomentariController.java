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

import com.ftn.TeretanaVebProjekat.model.Komentar;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.KomentarService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/Komentari")
public class KomentariController {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final String minDate = LocalDate.MIN.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String maxDate = LocalDate.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String minTime = LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm"));
	public static final String maxTime = LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm"));
	
	@Autowired
	private KomentarService komentarService;

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
			@RequestParam(required=false) String tekst, 
			@RequestParam(required=false) Integer ocenaOd, 
			@RequestParam(required=false) Integer ocenaDo,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumDo, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeDo, 
			@RequestParam(required=false) Long treningId,
			@RequestParam(required=false) String status,
			@RequestParam(required=false) String korisnik,
			HttpSession session) throws IOException {
		
		//ako je input tipa text i ništa se ne unese 
		//a parametar metode Sting onda će vrednost parametra handeler metode biti "" što nije null
		if(tekst!=null && tekst.trim().equals(""))
			tekst=null;
		
		if(status!=null && status.trim().equals(""))
			status=null;
		
		if(korisnik!=null && korisnik.trim().equals(""))
			korisnik=null;
		
		// čitanje
		LocalDateTime datumIVremeOd = null;
		if(datumOd!=null || vremeOd!=null)
			datumIVremeOd = LocalDateTime.of(datumOd, vremeOd);
		LocalDateTime datumIVremeDo = null;
		if(datumDo!=null || vremeDo!=null)
			datumIVremeDo = LocalDateTime.of(datumDo, vremeDo);
		
		List<Komentar> komentari = komentarService.find(tekst, ocenaOd, ocenaDo, datumIVremeOd, datumIVremeDo, treningId, status, korisnik);
		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("komentari");
		rezultat.addObject("komentari", komentari);
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView Details(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		Komentar komentar = komentarService.findOne(id);
		if (komentar == null) {
			response.sendRedirect(baseURL + "Komentari");
			return null;
		}

		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("komentar");
		rezultat.addObject("komentar", komentar);
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public ModelAndView Create(@RequestParam(name="treningId", required=false) Long treningId, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		//if (prijavljeniKorisnik == null || prijavljeniKorisnik.isAdministrator() == false) {
			//response.sendRedirect(baseURL + "prijava.html");
			//return null;
		//}

		// čitanje
		List<Trening> treninzi = treningService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("dodavanjeKomentara");
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void Create(
			@RequestParam String tekst, @RequestParam int ocena,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme, 
			@RequestParam Long treningId, @RequestParam(defaultValue="odobren") String status, 
			@RequestParam(defaultValue="mika") String korisnik,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		//if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			//response.sendRedirect(baseURL + "Komentari");
			//return;
		//}

		// validacija
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Komentari");
			return;
		}

		// kreiranje
		Komentar komentar = new Komentar(tekst, ocena, datumIVreme, trening, status, korisnik);
		komentarService.save(komentar);
		
		response.sendRedirect(baseURL + "Komentari");
	}

	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id,
			@RequestParam String tekst, @RequestParam int ocena,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme, 
			@RequestParam Long treningId, @RequestParam String status,
			@RequestParam String korisnik,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Komentari");
			return;
		}

		// validacija
		Komentar komentar = komentarService.findOne(id);
		if (komentar == null) {
			response.sendRedirect(baseURL + "Komentari");
			return;
		}
		
		LocalDateTime datumIVreme = LocalDateTime.of(datum, vreme);
		
		Trening trening = treningService.findOne(treningId);
		if (trening == null) {
			response.sendRedirect(baseURL + "Komentari");
			return;
		}

		// izmena
		komentar.setTekst(tekst);
		komentar.setOcena(ocena);
		komentar.setDatumIVreme(datumIVreme);
		komentar.setTrening(trening);
		komentar.setStatus(status);
		komentar.setKorisnik(korisnik);
		komentarService.update(komentar);
	
		response.sendRedirect(baseURL + "Komentari");
	}

	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Komentari");
			return;
		}

		// brisanje
		komentarService.delete(id);

		response.sendRedirect(baseURL + "Komentari");
	}

}
