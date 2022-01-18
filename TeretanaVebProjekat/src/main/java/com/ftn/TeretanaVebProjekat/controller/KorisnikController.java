package com.ftn.TeretanaVebProjekat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {
	
	public static final String KORISNIK_KEY = "korisnik";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private KorisnikService korisnikService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 

	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String sifra,
			HttpSession session, HttpServletResponse response) throws IOException {
		postLogin(email, sifra, session, response);
	}

	@PostMapping(value="/login")
	public ModelAndView postLogin(@RequestParam String email, @RequestParam String sifra, 
			HttpSession session, HttpServletResponse response) throws IOException {
		try {
			// validacija
			Korisnik korisnik = korisnikService.findOne(email, sifra);
			if (korisnik == null) {
				throw new Exception("Neispravno korisnicko ime ili lozinka!");
			}			

			// prijava
			session.setAttribute(KorisnikController.KORISNIK_KEY, korisnik);
			
			response.sendRedirect(bURL + "treninzi");
			return null;
		} catch (Exception ex) {
			// ispis greske
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna prijava!";
			}
			
			// prosledjivanje
			ModelAndView rezultat = new ModelAndView("prijava");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
	}
	
	@PostMapping(value = "/registracija")
	public void registracija(@RequestParam(required = true) String korisnickoIme, @RequestParam(required = true) String sifra, 
			@RequestParam(required = true) String email, @RequestParam(required = true) String ime, 
			@RequestParam(required = true) String prezime, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
			@RequestParam(required = true) String adresa, @RequestParam(required = true) String brojTelefona,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRegistracije, @RequestParam(required = true) String uloga,
			HttpSession session, HttpServletResponse response) throws IOException {
		Korisnik korisnik = new Korisnik(korisnickoIme, sifra, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, uloga);
		korisnikService.save(korisnik);
		
		response.sendRedirect(bURL + "korisnici");
	}
	
	@GetMapping
	@ResponseBody
	public ModelAndView getKorisnici(HttpSession session, HttpServletResponse response){
		List<Korisnik> korisnici = korisnikService.findAll();
		
		ModelAndView rezultat = new ModelAndView("korisnici");
		rezultat.addObject("korisnici", korisnici);

		return rezultat;
	}
	
	@PostMapping(value="/obrisi")
	public void obrisiKorisnika(@RequestParam Long id, HttpServletResponse response) throws IOException {				
		korisnikService.delete(id);
		response.sendRedirect(bURL+"korisnici");
	}

	@GetMapping(value="/Logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		// odjava	
		session.invalidate();
		
		response.sendRedirect(bURL);
	}
}
