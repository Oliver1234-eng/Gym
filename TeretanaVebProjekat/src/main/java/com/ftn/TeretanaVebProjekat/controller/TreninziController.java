package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningStatistika;
import com.ftn.TeretanaVebProjekat.service.TipTreningaService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/Treninzi")
public class TreninziController implements ServletContextAware {

	public static final String STATISTIKA_TRENINGA_KEY = "statistikaTreninga";
	public static final String POSECENI_TRENINZI_ZA_KORISNIKA_KEY = "poseceniTreninziZaKorisnika";
	
	@Autowired
	private TreningService treningService;

	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 

	@PostConstruct
	public void init() {
		baseURL = servletContext.getContextPath() + "/";
	}

	@GetMapping
	public ModelAndView index(
			@RequestParam(required=false) String naziv, 
			@RequestParam(required=false) Long tipTreningaId,
			@RequestParam(required=false) String trener, 
			@RequestParam(required=false) String kratakOpis,
			@RequestParam(required=false) Integer cenaOd, 
			@RequestParam(required=false) Integer cenaDo,
			@RequestParam(required=false) String vrstaTreninga,
			@RequestParam(required=false) String nivoTreninga,
			@RequestParam(required=false) Integer trajanjeUMinutimaOd, 
			@RequestParam(required=false) Integer trajanjeUMinutimaDo,
			@RequestParam(required=false) Integer prosecnaOcenaOd, 
			@RequestParam(required=false) Integer prosecnaOcenaDo,
			HttpSession session)  throws IOException {
		//ako je input tipa text i ništa se ne unese 
		//a parametar metode Sting onda će vrednost parametra handeler metode biti "" što nije null
		if(naziv!=null && naziv.trim().equals(""))
			naziv=null;
		
		if(trener!=null && trener.trim().equals(""))
			trener=null;
		
		if(kratakOpis!=null && kratakOpis.trim().equals(""))
			kratakOpis=null;
		
		if(vrstaTreninga!=null && vrstaTreninga.trim().equals(""))
			vrstaTreninga=null;
		
		if(nivoTreninga!=null && nivoTreninga.trim().equals(""))
			nivoTreninga=null;
		
		// čitanje
		List<Trening> treninzi = treningService.find(naziv, tipTreningaId, trener, kratakOpis, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, trajanjeUMinutimaOd, trajanjeUMinutimaDo, prosecnaOcenaOd, prosecnaOcenaDo);
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("treninzi");
		rezultat.addObject("treninzi", treninzi);
		rezultat.addObject("tipoviTreninga", tipoviTreninga);

		return rezultat;
	}

	@GetMapping(value="/Details")
	@SuppressWarnings("unchecked")
	public ModelAndView details(@RequestParam Long id, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// čitanje
		Trening trening = treningService.findOne(id);
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();

		// obrada
		TreningStatistika statistikaTreninga = (TreningStatistika) servletContext.getAttribute(TreninziController.STATISTIKA_TRENINGA_KEY);
		statistikaTreninga.incrementBrojac(trening);

		List<Trening> poseceniTreninzi = (List<Trening>) session.getAttribute(TreninziController.POSECENI_TRENINZI_ZA_KORISNIKA_KEY);
		if (!poseceniTreninzi.contains(trening)) {
			poseceniTreninzi.add(trening);
		}

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("trening");
		rezultat.addObject("trening", trening);
		rezultat.addObject("tipoviTreninga", tipoviTreninga);

		return rezultat;
	}

	@GetMapping(value="/Create")
	public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Treninzi");
			return null;
		}

		// čitanje
		List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();

		// prosleđivanje
		ModelAndView rezultat = new ModelAndView("dodavanjeTreninga");
		rezultat.addObject("tipoviTreninga", tipoviTreninga);

		return rezultat;
	}

	@PostMapping(value="/Create")
	public void create(@RequestParam String naziv, @RequestParam String trener,
			@RequestParam String kratakOpis, @RequestParam int cena, 
			@RequestParam(defaultValue="pojedinacni") String vrstaTreninga,
			@RequestParam(defaultValue="amaterski") String nivoTreninga,
			@RequestParam int trajanjeUMinutima, @RequestParam int prosecnaOcena,  
			@RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaIds, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Treninzi");
			return;
		}

		// kreiranje
		Trening trening = new Trening(naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena);
		trening.setTipoviTreninga(tipTreningaService.find(tipTreningaIds));
		treningService.save(trening);

		response.sendRedirect(baseURL + "Treninzi");
	}

	@PostMapping(value="/Edit")
	public void edit(@RequestParam Long id, 
			@RequestParam String naziv, 
			@RequestParam String trener, @RequestParam String kratakOpis,
			@RequestParam int cena, @RequestParam String vrstaTreninga,
			@RequestParam String nivoTreninga, @RequestParam int trajanjeUMinutima,
			@RequestParam int prosecnaOcena,
			@RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaIds, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Treninzi");
			return;
		}

		// validacija
		Trening trening = treningService.findOne(id);
		if (trening == null) {
			response.sendRedirect(baseURL + "Treninzi");
			return;
		}	
		if (naziv == null || naziv.equals("") || trener == null || trener.equals("") || 
				kratakOpis == null || kratakOpis.equals("") || cena < 100 ||
				vrstaTreninga == null || vrstaTreninga.equals("") || nivoTreninga == null ||
				nivoTreninga.equals("") || trajanjeUMinutima < 60 || prosecnaOcena < 1) {
			response.sendRedirect(baseURL + "Treninzi/Details?id=" + id);
			return;
		}

		// izmena
		trening.setNaziv(naziv);
		trening.setTrener(trener);
		trening.setKratakOpis(kratakOpis);
		trening.setCena(cena);
		trening.setVrstaTreninga(vrstaTreninga);
		trening.setNivoTreninga(nivoTreninga);
		trening.setTrajanjeUMinutima(trajanjeUMinutima);
		trening.setProsecnaOcena(prosecnaOcena);
		trening.setTipoviTreninga(tipTreningaService.find(tipTreningaIds));
		treningService.update(trening);

		response.sendRedirect(baseURL + "Treninzi");
	}

	@PostMapping(value="/Delete")
	public void delete(@RequestParam Long id, 
			HttpSession session, HttpServletResponse response) throws IOException {
		// autentikacija, autorizacija
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		if (prijavljeniKorisnik == null || !prijavljeniKorisnik.isAdministrator()) {
			response.sendRedirect(baseURL + "Treninzi");
			return;
		}

		// brisanje
		treningService.delete(id);
	
		response.sendRedirect(baseURL + "Treninzi");
	}

}