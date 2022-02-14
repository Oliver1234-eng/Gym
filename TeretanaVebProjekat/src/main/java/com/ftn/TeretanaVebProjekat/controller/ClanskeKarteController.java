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
public class ClanskeKarteController {
	
	//public static final String CLANSKE_KARTE_KEY = "clanske_karte";
	//public static final String KORISNICI_KEY = "korisnici";
	//public static final String CLANSKA_KARTA = "clanska_karta";
	
	@Autowired
	private ClanskaKartaService clanskaKartaService;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {
		baseURL = servletContext.getContextPath() + "/";	
	}

	@GetMapping
	public ModelAndView Index( 
			@RequestParam(required=false) Integer popust,
			@RequestParam(required=false) Integer brojPoenaOd,
			@RequestParam(required=false) Integer brojPoenaDo,
			@RequestParam(required=false) String registarskiBroj,
			@RequestParam(required=false) Long korisnikId, 
			HttpSession session) throws IOException {
		
		List<ClanskaKarta> clanskeKarte = clanskaKartaService.find(popust, brojPoenaOd, brojPoenaDo, registarskiBroj, korisnikId);
		List<Korisnik> korisnici = korisnikService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("clanskeKarte");
		rezultat.addObject("clanskeKarte", clanskeKarte);
		rezultat.addObject("korisnici", korisnici);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView Details(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		ClanskaKarta clanskaKarta = clanskaKartaService.findOne(id);
		if (clanskaKarta == null) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return null;
		}

		List<Korisnik> korisnici = korisnikService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("clanskaKarta");
		rezultat.addObject("clanskaKarta", clanskaKarta);
		rezultat.addObject("korisnici", korisnici);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public ModelAndView Create(@RequestParam(name="korisnikId", required=false) Long korisnikId, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		//if (prijavljeniKorisnik == null || prijavljeniKorisnik.isAdministrator() == false) {
			//response.sendRedirect(baseURL + "prijava.html");
			//return null;
		//}

		// čitanje
		List<Korisnik> korisnici = korisnikService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("dodavanjeClanskeKarte");
		rezultat.addObject("korisnici", korisnici);

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void Create(@RequestParam int popust, @RequestParam int brojPoena,
			@RequestParam String registarskiBroj, @RequestParam Long korisnikId,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		//if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			//response.sendRedirect(baseURL + "Termini");
			//return;
		//}
		
		Korisnik korisnik = korisnikService.findOne(korisnikId);
		if (korisnik == null) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return;
		}

		// kreiranje
		ClanskaKarta clanskaKarta = new ClanskaKarta(popust, brojPoena, registarskiBroj, korisnik);
		clanskaKartaService.save(clanskaKarta);
		
		response.sendRedirect(baseURL + "ClanskeKarte");
	}

	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id, @RequestParam int popust,
			@RequestParam int brojPoena, @RequestParam String registarskiBroj,
			@RequestParam Long korisnikId,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return;
		}

		// validacija
		ClanskaKarta clanskaKarta = clanskaKartaService.findOne(id);
		if (clanskaKarta == null) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return;
		}
		
		Korisnik korisnik = korisnikService.findOne(korisnikId);
		if (korisnik == null) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return;
		}

		// izmena
		clanskaKarta.setPopust(popust);
		clanskaKarta.setBrojPoena(brojPoena);
		clanskaKarta.setRegistarskiBroj(registarskiBroj);
		clanskaKarta.setKorisnik(korisnik);
		clanskaKartaService.update(clanskaKarta);
	
		response.sendRedirect(baseURL + "ClanskeKarte");
	}

	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "ClanskeKarte");
			return;
		}

		// brisanje
		clanskaKartaService.delete(id);

		response.sendRedirect(baseURL + "ClanskeKarte");
	}

	
	/*
	 * // POST: clanskeKarte/izdajKnjigu -> izdajKnjige sve sa sesije
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value="/zakaziTreninge") public void izdajKnjigu(@RequestParam
	 * String registarskiBroj, HttpSession session, HttpServletResponse response)
	 * throws IOException { ClanskaKarta ck =
	 * clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj); if (ck ==
	 * null) { //todo } List<TreningKorpa> zaZakazivanje = (List<TreningKorpa>)
	 * session.getAttribute(TreninziKorpaController.TRENINZI_ZA_ZAKAZIVANJE);
	 * 
	 * for (TreningKorpa k : zaZakazivanje) { if (ck != null) {
	 * ck.getZakazaniTreninzi().add(k); ck = clanskaKartaService.update(ck); if (ck
	 * != null) { k.setZakazan(true); treningKorpaService.update(k); } } }
	 * 
	 * session.setAttribute(TreninziKorpaController.TRENINZI_ZA_ZAKAZIVANJE, new
	 * ArrayList<TreningKorpa>());
	 * 
	 * response.sendRedirect(bURL+"TreninziKorpa"); }
	 * 
	 * // POST: clanskeKarte/vratiKnjigu
	 * 
	 * @PostMapping(value="/otkaziTrening") public void vratiKnjigu(@RequestParam
	 * Long idTreninga, @RequestParam String registarskiBroj, HttpServletResponse
	 * response) throws IOException { ClanskaKarta ck =
	 * clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);
	 * 
	 * if (ck != null) { TreningKorpa treningKorpa =
	 * treningKorpaService.findOne(idTreninga);
	 * ck.getZakazaniTreninzi().remove(treningKorpa); ck =
	 * clanskaKartaService.update(ck); if (ck != null) {
	 * treningKorpa.setZakazan(false); treningKorpaService.update(treningKorpa); } }
	 * 
	 * response.sendRedirect(bURL+"TreninziKorpa"); }
	 */

}
