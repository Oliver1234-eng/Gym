package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Sala;
import com.ftn.TeretanaVebProjekat.service.SalaService;

@Controller
@RequestMapping(value="/Sale")
public class SaleController {
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {
		baseURL = servletContext.getContextPath() + "/";	
	}

	@GetMapping
	public ModelAndView Index(
			@RequestParam(required=false) Integer oznaka,
			@RequestParam(required=false) Integer kapacitetOd, 
			@RequestParam(required=false) Integer kapacitetDo, 
			HttpSession session) throws IOException {
		
		List<Sala> sale = salaService.find(oznaka, kapacitetOd, kapacitetDo);

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("sale");
		rezultat.addObject("sale", sale);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView Details(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		Sala sala = salaService.findOne(id);
		if (sala == null) {
			response.sendRedirect(baseURL + "Sale");
			return null;
		}

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("sala");
		rezultat.addObject("sala", sala);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public ModelAndView Create(HttpSession session, 
			HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || prijavljeniKorisnik.isAdministrator() == false) {
			response.sendRedirect(baseURL + "prijava.html");
			return null;
		}

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("dodavanjeSale");

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void Create(@RequestParam int oznaka, @RequestParam int kapacitet,
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Sale");
			return;
		}

		// kreiranje
		Sala sala = new Sala(oznaka, kapacitet);
		salaService.save(sala);
		
		response.sendRedirect(baseURL + "Sale");
	}

	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id, @RequestParam int oznaka, @RequestParam int kapacitet, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Sale");
			return;
		}

		// validacija
		Sala sala = salaService.findOne(id);
		if (sala == null) {
			response.sendRedirect(baseURL + "Sale");
			return;
		}

		// izmena
		sala.setKapacitet(kapacitet);
	
		response.sendRedirect(baseURL + "Sale");
	}

	@PostMapping(value="/Delete")
	public void Delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Sale");
			return;
		}

		// brisanje
		salaService.delete(id);

		response.sendRedirect(baseURL + "Sale");
	}

}
