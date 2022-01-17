package com.ftn.TeretanaVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
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
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/treninzi")
public class TreninziController implements ServletContextAware {

	//public static final String KNJIGE_ZA_IZNAJMLJIVANJE = "treninzi_za_zakazivanje";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private TreningService treningService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
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
			HttpSession session) throws IOException {
		
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
		
		
		List<Trening> treninzi = treningService.find(naziv, trener, kratakOpis, tipTreninga, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, trajanjeUMinutimaOd, trajanjeUMinutimaDo, prosecnaOcenaOd, prosecnaOcenaDo);
		
		ModelAndView rezultat = new ModelAndView("treninzi");
		rezultat.addObject("treninzi", treninzi);

		return rezultat;
	}
	
	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev */
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeTreninga";
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: treninzi/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, @RequestParam String trener,  
			@RequestParam String kratakOpis, @RequestParam String tipTreninga,
			@RequestParam int cena, @RequestParam String vrstaTreninga,
			@RequestParam String nivoTreninga, @RequestParam int trajanjeUMinutima,
			@RequestParam int prosecnaOcena, HttpServletResponse response) throws IOException {		
		Trening trening = new Trening(naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena);
		Trening saved = treningService.save(trening);
		response.sendRedirect(bURL+"treninzi");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	// POST: treninzi/edit
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam String naziv, 
			@RequestParam String trener, @RequestParam String kratakOpis, 
			@RequestParam String tipTreninga, @RequestParam int cena, 
			@RequestParam String vrstaTreninga, @RequestParam String nivoTreninga, 
			@RequestParam int trajanjeUMinutima, @RequestParam int prosecnaOcena, 
			HttpServletResponse response) throws IOException {	
		Trening trening = treningService.findOne(id);
		if(trening != null) {
			if(naziv != null && !naziv.trim().equals(""))//				
				trening.setNaziv(naziv);
			if(trener != null && !trener.trim().equals(""))
				trening.setTrener(trener);
			if(kratakOpis != null && !kratakOpis.trim().equals(""))
				trening.setKratakOpis(kratakOpis);
			if(tipTreninga != null && !tipTreninga.trim().equals(""))
				trening.setTipTreninga(tipTreninga);
			if(cena > 0)
				trening.setCena(cena);
			if(vrstaTreninga != null && !vrstaTreninga.trim().equals(""))
				trening.setVrstaTreninga(vrstaTreninga);
			if(nivoTreninga != null && !nivoTreninga.trim().equals(""))
				trening.setNivoTreninga(nivoTreninga);
			if(trajanjeUMinutima > 0)
				trening.setTrajanjeUMinutima(trajanjeUMinutima);
			if(prosecnaOcena > 0)
				trening.setProsecnaOcena(prosecnaOcena);
		}
		Trening saved = treningService.update(trening);
		response.sendRedirect(bURL+"treninzi");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	//POST: treninzi/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Trening deleted = treningService.delete(id);
		response.sendRedirect(bURL+"treninzi");
	}
	
	/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev */
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Trening trening = treningService.findOne(id);
		
		ModelAndView rezultat = new ModelAndView("trening"); 
		rezultat.addObject("trening", trening); 

		return rezultat; 
	}
	
	
}