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

import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.service.TipTreningaService;

@Controller
@RequestMapping(value="/TipoviTreninga")
public class TipoviTreningaController {
	
	//za potrebe vezne tabele

	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {
		baseURL = servletContext.getContextPath() + "/";
	}

	@GetMapping
	public ModelAndView index(@RequestParam(required=false) String naziv, HttpSession session) {
		//ako je input tipa text i ništa se ne unese 
		//a parametar metode Sting onda će vrednost parametra handeler metode biti "" što nije null
		if(naziv!=null && naziv.trim().equals(""))
			naziv=null;
		
		// čitanje
		List<TipTreninga> tipoviTreninga = tipTreningaService.find(naziv);

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("tipoviTreninga");
		rezultat.addObject("tipoviTreninga", tipoviTreninga);

		return rezultat;
	}

	@GetMapping(value="/Details")
	public ModelAndView details(@RequestParam Long id, HttpSession session) throws IOException {
		// čitanje
		TipTreninga tipTreninga = tipTreningaService.findOne(id);

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("tipTreninga");
		rezultat.addObject("tipTreninga", tipTreninga);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public String create(HttpSession session, HttpServletResponse response) throws IOException {

		return "dodavanjeTipaTreninga";
	}
	
	@PostMapping(value="/Create")
	public void create(@RequestParam String naziv, 
			HttpSession session, HttpServletResponse response) throws IOException {

		// validacija
		if (naziv.equals("")) {
			response.sendRedirect(baseURL + "TipoviTreninga/Create");
			return;
		}

		// kreiranje
		TipTreninga tipTreninga = new TipTreninga(naziv);
		tipTreningaService.save(tipTreninga);

		response.sendRedirect(baseURL + "TipoviTreninga");
	}

	@PostMapping(value="/Edit")
	public void edit(@RequestParam Long id, 
			@RequestParam String naziv, 
			HttpSession session, HttpServletResponse response) throws IOException {

		// validacija
		TipTreninga tipTreninga = tipTreningaService.findOne(id);

		// izmena
		tipTreninga.setNaziv(naziv);
		tipTreningaService.update(tipTreninga);

		response.sendRedirect(baseURL + "TipoviTreninga");
	}

	@PostMapping(value="/Delete")
	public void delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {

		// brisanje
		tipTreningaService.delete(id);

		response.sendRedirect(baseURL + "TipoviTreninga");
	}
}

