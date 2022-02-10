package com.ftn.TeretanaVebProjekat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
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

import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.service.KorisnikService;

@Controller
@RequestMapping(value="/Korisnici")
public class KorisnikController {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final String minDate = LocalDate.MIN.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String maxDate = LocalDate.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String minTime = LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm"));
	public static final String maxTime = LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm"));

	public static final String KORISNIK_KEY = "prijavljeniKorisnik";
	
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
	public ModelAndView index(
			@RequestParam(required=false) String korisnickoIme,
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String ime,
			@RequestParam(required=false) String prezime,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenjaOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRodjenjaOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenjaDo, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRodjenjaDo, 
			@RequestParam(required=false) String adresa,
			@RequestParam(required=false) String brojTelefona,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracijeOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRegistracijeOd, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracijeDo, 
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRegistracijeDo, 
			@RequestParam(required=false) Boolean administrator,
			HttpSession session, HttpServletResponse response) throws IOException {		
		// autentikacija, autorzacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL);
			return null;
		}

		//ako je input tipa text i ništa se ne unese 
		//a parametar metode Sting onda će vrednost parametra handeler metode biti "" što nije null
		if(korisnickoIme!=null && korisnickoIme.trim().equals(""))
			korisnickoIme=null;
		
		if(email!=null && email.trim().equals(""))
			email=null;
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		if(prezime!=null && prezime.trim().equals(""))
			prezime=null;
		
		if(adresa!=null && adresa.trim().equals(""))
			adresa=null;
		
		if(brojTelefona!=null && brojTelefona.trim().equals(""))
			brojTelefona=null;
		
		// čitanje
		LocalDateTime datumIVremeRodjenjaOd = null;
		if(datumRodjenjaOd!=null || vremeRodjenjaOd!=null)
			datumIVremeRodjenjaOd = LocalDateTime.of(datumRodjenjaOd, vremeRodjenjaOd);
		LocalDateTime datumIVremeRodjenjaDo = null;
		if(datumRodjenjaDo!=null || vremeRodjenjaDo!=null)
			datumIVremeRodjenjaDo = LocalDateTime.of(datumRodjenjaDo, vremeRodjenjaDo);
		
		LocalDateTime datumIVremeRegistracijeOd = null;
		if(datumRegistracijeOd!=null || vremeRegistracijeOd!=null)
			datumIVremeRegistracijeOd = LocalDateTime.of(datumRegistracijeOd, vremeRegistracijeOd);
		LocalDateTime datumIVremeRegistracijeDo = null;
		if(datumRegistracijeDo!=null || vremeRegistracijeDo!=null)
			datumIVremeRegistracijeDo = LocalDateTime.of(datumRegistracijeDo, vremeRegistracijeDo);
		
		List<Korisnik> korisnici = korisnikService.find(korisnickoIme, email, ime, prezime, datumIVremeRodjenjaOd, datumIVremeRodjenjaDo, adresa, brojTelefona, datumIVremeRegistracijeOd, datumIVremeRegistracijeDo, administrator);

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("korisnici");
		rezultat.addObject("korisnici", korisnici);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam String korisnickoIme, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		// samo administrator može da vidi druge korisnike; svaki korisnik može da vidi sebe
		if (prijavljeniKorisnik == null || (!prijavljeniKorisnik.isAdministrator() && !prijavljeniKorisnik.getKorisnickoIme().equals(korisnickoIme))) {
			response.sendRedirect(baseURL + "Korisnici");
			return null;
		}

		// validacija
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		if (korisnik == null) {
			response.sendRedirect(baseURL + "Korisnici");
			return null;
		}

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("korisnik");
		rezultat.addObject("korisnik", korisnik);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public String create(HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		// samo administrator može da kreira korisnike
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Filmovi");
			return "filmovi";
		}

		return "dodavanjeKorisnika";
	}
	
	@PostMapping(value="/Create")
	public void create(@RequestParam String korisnickoIme, @RequestParam String lozinka, 
			@RequestParam String email, @RequestParam String ime, 
			@RequestParam String prezime, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRodjenja,
			@RequestParam String adresa, @RequestParam String brojTelefona,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracije, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRegistracije,
			@RequestParam(required=false) String administrator,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		// samo administrator može da kreira korisnike
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Korisnici");
			return;
		}
	

		// validacija
		Korisnik postojeciKorisnik = korisnikService.findOne(korisnickoIme);
		if (postojeciKorisnik != null) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (korisnickoIme.equals("") || lozinka.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (email.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (ime.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (prezime.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (adresa.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		if (brojTelefona.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Create");
			return;
		}
		
		LocalDateTime datumIVremeRodjenja = LocalDateTime.of(datumRodjenja, vremeRodjenja);
		LocalDateTime datumIVremeRegistracije = LocalDateTime.of(datumRegistracije, vremeRegistracije);

		// kreiranje
		Korisnik korisnik = new Korisnik(korisnickoIme, lozinka, email, ime, prezime, datumIVremeRodjenja, adresa, brojTelefona,datumIVremeRegistracije, administrator != null);
		korisnikService.save(korisnik);

		response.sendRedirect(baseURL + "Korisnici");
	}

	@PostMapping(value="/Edit")
	public void edit(@RequestParam String korisnickoIme, 
			@RequestParam String lozinka, String email, 
			@RequestParam String ime, @RequestParam String prezime, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRodjenja, 
			@RequestParam String adresa, @RequestParam String brojTelefona,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracije, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRegistracije, 
			@RequestParam(required=false) String administrator,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		// samo administrator može da menja druge korisnike; svaki korisnik može da menja sebe
		if (prijavljeniKorisnik == null || (!prijavljeniKorisnik.isAdministrator() && !prijavljeniKorisnik.getKorisnickoIme().equals(korisnickoIme))) {
			response.sendRedirect(baseURL + "Korisnici");
			return;
		}

		// validacija
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		if (korisnik == null) {
			response.sendRedirect(baseURL + "Korisnici");
			return;
		}
		
		if (email.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Edit?korisnicoIme=" + korisnickoIme);
			return;
		}
		
		if (ime.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Edit?korisnicoIme=" + korisnickoIme);
			return;
		}
		
		if (prezime.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Edit?korisnicoIme=" + korisnickoIme);
			return;
		}
		
		if (adresa.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Edit?korisnicoIme=" + korisnickoIme);
			return;
		}
		
		if (brojTelefona.equals("")) {
			response.sendRedirect(baseURL + "Korisnici/Edit?korisnicoIme=" + korisnickoIme);
			return;
		}
		
		
		LocalDateTime datumIVremeRodjenja = LocalDateTime.of(datumRodjenja, vremeRodjenja);
		LocalDateTime datumIVremeRegistracije = LocalDateTime.of(datumRegistracije, vremeRegistracije);

		// izmena
		if (!lozinka.equals("")) {
			korisnik.setLozinka(lozinka);
		}
		
		korisnik.setEmail(email);
		korisnik.setIme(ime);
		korisnik.setPrezime(prezime);
		korisnik.setDatumRodjenja(datumIVremeRodjenja);
		korisnik.setAdresa(adresa);
		korisnik.setBrojTelefona(brojTelefona);
		korisnik.setDatumRegistracije(datumIVremeRegistracije);
		
		// privilegije može menjati samo administrator i to drugim korisnicima
		if (prijavljeniKorisnik.isAdministrator() && !prijavljeniKorisnik.equals(korisnik)) {
			korisnik.setAdministrator(administrator != null);
		}
		korisnikService.update(korisnik);

		// sigurnost
		if (!prijavljeniKorisnik.equals(korisnik)) {
			// TODO odjaviti korisnika
		}

		if (prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Korisnici");
		} else {
			response.sendRedirect(baseURL);
		}
	}

	@PostMapping(value="/Delete")
	public void delete(@RequestParam String korisnickoIme, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		// samo administrator može da briše korisnike, ali ne i sebe
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator() || prijavljeniKorisnik.getKorisnickoIme().equals(korisnickoIme)) {
			response.sendRedirect(baseURL + "Korisnici");
			return;
		}

		// brisanje
		korisnikService.delete(korisnickoIme);

		// sigurnost
		// TODO odjaviti korisnika
		
		response.sendRedirect(baseURL + "Korisnici");
	}

	@PostMapping(value="/Register")
	public ModelAndView register(@RequestParam String korisnickoIme, @RequestParam String lozinka,
			@RequestParam String email, @RequestParam String ime, 
			@RequestParam String prezime,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRodjenja,
			@RequestParam String adresa, @RequestParam String brojTelefona,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracije, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vremeRegistracije,
			@RequestParam String ponovljenaLozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		try {
			// validacija
			Korisnik postojeciKorisnik = korisnikService.findOne(korisnickoIme);
			if (postojeciKorisnik != null) {
				throw new Exception("Korisnicko ime vec postoji!");
			}
			if (korisnickoIme.equals("") || lozinka.equals("")) {
				throw new Exception("Korisnicko ime i lozinka ne smeju biti prazni!");
			}
			if (!lozinka.equals(ponovljenaLozinka)) {
				throw new Exception("Lozinke se ne podudaraju!");
			}
			if (email.equals("")) {
				throw new Exception("E-mail ne sme biti prazan!");
			}
			if (ime.equals("")) {
				throw new Exception("Ime ne sme biti prazno!");
			}
			if (prezime.equals("")) {
				throw new Exception("Prezime ne sme biti prazno!");
			}
			if (adresa.equals("")) {
				throw new Exception("Adresa ne sme biti prazno!");
			}
			if (brojTelefona.equals("")) {
				throw new Exception("Broj telefona ne sme biti prazno!");
			}
			
			
			LocalDateTime datumIVremeRodjenja = LocalDateTime.of(datumRodjenja, vremeRodjenja);
			LocalDateTime datumIVremeRegistracije = LocalDateTime.of(datumRegistracije, vremeRegistracije);

			// registracija
			Korisnik korisnik = new Korisnik(korisnickoIme, lozinka, email, ime, prezime, datumIVremeRodjenja, adresa, brojTelefona, datumIVremeRegistracije);
			korisnikService.save(korisnik);

			response.sendRedirect(baseURL + "prijava.html");
			return null;
		} catch (Exception ex) {
			// ispis greške
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna registracija!";
			}

			// prosleđivanje
			ModelAndView rezultat = new ModelAndView("registracija");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
	}
	
	@PostMapping(value="/Login")
	public ModelAndView postLogin(@RequestParam String korisnickoIme, @RequestParam String lozinka, 
			HttpSession session, HttpServletResponse response) throws IOException {
		try {
			// validacija
			Korisnik korisnik = korisnikService.findOne(korisnickoIme, lozinka);
			if (korisnik == null) {
				throw new Exception("Neispravno korisnicko ime ili lozinka!");
			}			

			// prijava
			session.setAttribute(KorisnikController.KORISNIK_KEY, korisnik);
			
			response.sendRedirect(baseURL);
			return null;
		} catch (Exception ex) {
			// ispis greške
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna prijava!";
			}
			
			// prosleđivanje
			ModelAndView rezultat = new ModelAndView("prijava");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
	}

	@GetMapping(value="/Logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		// odjava	
		session.invalidate();
		
		response.sendRedirect(baseURL);
	}
	
}