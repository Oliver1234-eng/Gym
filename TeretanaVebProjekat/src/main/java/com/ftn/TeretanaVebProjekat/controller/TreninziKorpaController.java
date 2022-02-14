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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;
import com.ftn.TeretanaVebProjekat.service.TreningKorpaService;

@Controller
@RequestMapping(value="/TreninziKorpa")
public class TreninziKorpaController implements ServletContextAware {

	public static final String TRENINZI_ZA_ZAKAZIVANJE = "treninzi_za_zakazivanje";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private TreningKorpaService treningKorpaService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@GetMapping
	public ModelAndView index(@RequestParam(required=false) String naziv,
			@RequestParam(required=false) String trener, 
			@RequestParam(required=false) String kratakOpis,
			@RequestParam(required=false) String tipTreninga,
			@RequestParam(required=false) Integer cenaOd, 
			@RequestParam(required=false) Integer cenaDo,
			@RequestParam(required=false) String vrstaTreninga,
			@RequestParam(required=false) String nivoTreninga,
			@RequestParam(required=false) Integer trajanjeUMinutimaOd, 
			@RequestParam(required=false) Integer trajanjeUMinutimaDo,
			@RequestParam(required=false) Integer prosecnaOcenaOd, 
			@RequestParam(required=false) Integer prosecnaOcenaDo,
			HttpSession session) {
		
		if(naziv!=null && naziv.trim().equals(""))
			naziv=null;
		
		if(trener!=null && trener.trim().equals(""))
			trener=null;
		
		if(kratakOpis!=null && kratakOpis.trim().equals(""))
			kratakOpis=null;
		
		if(tipTreninga!=null && tipTreninga.trim().equals(""))
			tipTreninga=null;
		
		if(vrstaTreninga!=null && vrstaTreninga.trim().equals(""))
			vrstaTreninga=null;
		
		if(nivoTreninga!=null && nivoTreninga.trim().equals(""))
			nivoTreninga=null;
		
		List<TreningKorpa> treninziKorpa = treningKorpaService.find(naziv, trener, kratakOpis, tipTreninga, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, trajanjeUMinutimaOd, trajanjeUMinutimaDo, prosecnaOcenaOd, prosecnaOcenaDo);	
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("treninziKorpa"); // naziv template-a
		rezultat.addObject("treninziKorpa", treninziKorpa); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeUKorpu"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, @RequestParam String trener, 
			@RequestParam String kratakOpis, @RequestParam String tipTreninga, 
			@RequestParam int cena, @RequestParam String vrstaTreninga,
			@RequestParam String nivoTreninga, @RequestParam int trajanjeUMinutima,
			@RequestParam int prosecnaOcena, HttpServletResponse response) throws IOException {		
		TreningKorpa treningKorpa = new TreningKorpa(naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena);
		TreningKorpa saved = treningKorpaService.save(treningKorpa);
		response.sendRedirect(bURL+"TreninziKorpa");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	// POST: knjige/edit
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@ModelAttribute TreningKorpa treningKorpaEdited , HttpServletResponse response) throws IOException {	
		TreningKorpa treningKorpa = treningKorpaService.findOne(treningKorpaEdited.getId());
		if(treningKorpa != null) {
			
			if(treningKorpaEdited.getNaziv() != null && !treningKorpaEdited.getNaziv().trim().equals(""))
				treningKorpa.setNaziv(treningKorpaEdited.getNaziv());
			
			if(treningKorpaEdited.getTrener() != null && !treningKorpaEdited.getTrener().trim().equals(""))
				treningKorpa.setTrener(treningKorpaEdited.getTrener());
			
			if(treningKorpaEdited.getKratakOpis() != null && !treningKorpaEdited.getKratakOpis().trim().equals(""))
				treningKorpa.setKratakOpis(treningKorpaEdited.getKratakOpis());
			
			if(treningKorpaEdited.getTipTreninga() != null && !treningKorpaEdited.getTipTreninga().trim().equals(""))
				treningKorpa.setTipTreninga(treningKorpaEdited.getTipTreninga());
			
			if(treningKorpaEdited.getCena() > 0)
				treningKorpa.setCena(treningKorpaEdited.getCena());
			
			if(treningKorpaEdited.getVrstaTreninga() != null && !treningKorpaEdited.getVrstaTreninga().trim().equals(""))
				treningKorpa.setVrstaTreninga(treningKorpaEdited.getVrstaTreninga());
			
			if(treningKorpaEdited.getNivoTreninga() != null && !treningKorpaEdited.getNivoTreninga().trim().equals(""))
				treningKorpa.setNivoTreninga(treningKorpaEdited.getNivoTreninga());
			
			if(treningKorpaEdited.getTrajanjeUMinutima() > 0)
				treningKorpa.setTrajanjeUMinutima(treningKorpaEdited.getTrajanjeUMinutima());
			
			if(treningKorpaEdited.getProsecnaOcena() > 0)
				treningKorpa.setProsecnaOcena(treningKorpaEdited.getProsecnaOcena());
			
		}
		
		TreningKorpa saved = treningKorpaService.update(treningKorpa);
		response.sendRedirect(bURL+"TreninziKorpa");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: knjige/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		TreningKorpa deleted = treningKorpaService.delete(id);
		response.sendRedirect(bURL+"TreninziKorpa");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		TreningKorpa treningKorpa = treningKorpaService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("treningKorpa"); // naziv template-a
		rezultat.addObject("treningKorpa", treningKorpa); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	// POST: knjige/zeljene/dodaj
	@SuppressWarnings("unchecked")
	@PostMapping(value="/zeljene/dodaj")
	@ResponseBody
	public void dodajUZeljene(@RequestParam Long idTreninga, HttpSession session, HttpServletResponse response) throws IOException {
		List<TreningKorpa> zaZakazivanje = (List<TreningKorpa>) session.getAttribute(TRENINZI_ZA_ZAKAZIVANJE);	
		TreningKorpa treningKorpa = treningKorpaService.findOne(idTreninga);
		zaZakazivanje.add(treningKorpa);
		
		response.sendRedirect(bURL+"TreninziKorpa/zeljene");
	}
	
//	GET: knjige/zeljene
	@SuppressWarnings("unchecked")
	@GetMapping(value="/zeljene")
	@ResponseBody
	public ModelAndView dodajZeljene(HttpSession session){
		List<TreningKorpa> zaZakazivanje = (List<TreningKorpa>) session.getAttribute(TRENINZI_ZA_ZAKAZIVANJE);
		
		ModelAndView rezultat = new ModelAndView("zaZakazivanje"); // naziv template-a
		rezultat.addObject("treninziKorpa", zaZakazivanje); // podatak koji se šalje template-u

		return rezultat;
	}
	
	// POST: knjige/zeljene/dodaj
	@SuppressWarnings("unchecked")
	@PostMapping(value="/zeljene/ukloni")
	@ResponseBody
	public void ukloniIzZeljenih(@RequestParam Long idTreninga, HttpSession session, HttpServletResponse response) throws IOException {
		List<TreningKorpa> zaZakazivanje = (List<TreningKorpa>) session.getAttribute(TRENINZI_ZA_ZAKAZIVANJE);	

		for (TreningKorpa treningKorpa : zaZakazivanje) {
			if (treningKorpa.getId().equals(idTreninga)) {
				zaZakazivanje.remove(treningKorpa);
				break;
			}
		}
		response.sendRedirect(bURL+"TreninziKorpa/zeljene");
	}
}

