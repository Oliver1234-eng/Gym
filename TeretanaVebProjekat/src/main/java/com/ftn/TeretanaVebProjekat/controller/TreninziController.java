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

import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TipTreningaService;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Controller
@RequestMapping(value="/treninzi")
public class TreninziController implements ServletContextAware {

	//public static final String KNJIGE_ZA_IZNAJMLJIVANJE = "treninzi_za_zakazivanje";
	
		@Autowired
		private TreningService treningService;
		
		@Autowired
		private TipTreningaService tipTreningaService;
		
		@Autowired
		private ServletContext servletContext;
		private  String bURL; 
		
		@Override
		public void setServletContext(ServletContext servletContext) {
			this.servletContext = servletContext;
		} 
		
		/** inicijalizacija podataka za kontroler */
		@PostConstruct
		public void init() {	
			bURL = servletContext.getContextPath()+"/";
		}
		
		/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
		@GetMapping
		public ModelAndView index(@RequestParam(required=false) String naziv,
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
				HttpSession session) throws IOException {
			
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
			
			
			List<Trening> treninzi = treningService.find(naziv, tipTreningaId, trener, kratakOpis, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, trajanjeUMinutimaOd, trajanjeUMinutimaDo, prosecnaOcenaOd, prosecnaOcenaDo);
			List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();
			
			ModelAndView rezultat = new ModelAndView("treninzi");
			rezultat.addObject("treninzi", treninzi);
			rezultat.addObject("tipoviTreninga", tipoviTreninga);

			return rezultat;
		}
		
		/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev */
		@GetMapping(value="/add")
		public ModelAndView create(HttpSession session, HttpServletResponse response){

			// čitanje
			List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();

			// prosleđivanje
			ModelAndView rezultat = new ModelAndView("dodavanjeTreninga");
			rezultat.addObject("tipoviTreninga", tipoviTreninga);

			return rezultat;
		}
		
		/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev */
		@GetMapping(value="/details")
		public ModelAndView details(@RequestParam Long id, 
				HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
			// čitanje
			Trening trening = treningService.findOne(id);
			List<TipTreninga> tipoviTreninga = tipTreningaService.findAll();

			// prosleđivanje
			ModelAndView rezultat = new ModelAndView("trening");
			rezultat.addObject("trening", trening);
			rezultat.addObject("tipoviTreninga", tipoviTreninga);

			return rezultat;
		}

		/** obrada podataka forme za unos novog entiteta, post zahtev */
		@PostMapping(value="/add")
		public void create(@RequestParam String naziv,
				@RequestParam String trener, @RequestParam String kratakOpis,
				@RequestParam int cena, @RequestParam String vrstaTreninga,
				@RequestParam String nivoTreninga, @RequestParam int trajanjeUMinutima,
				@RequestParam int prosecnaOcena, @RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaIds,
				HttpSession session, HttpServletResponse response) throws IOException {	
			
			Trening trening = new Trening(naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena);
			trening.setTipoviTreninga(tipTreningaService.find(tipTreningaIds));
			treningService.save(trening);
			response.sendRedirect(bURL+"treninzi");
		}
		
		/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
		// POST: treninzi/edit
		@PostMapping(value="/edit")
		public void edit(@RequestParam Long id, @RequestParam String naziv,
				@RequestParam String trener, @RequestParam String kratakOpis, 
				@RequestParam int cena, 
				@RequestParam String vrstaTreninga, @RequestParam String nivoTreninga, 
				@RequestParam int trajanjeUMinutima, @RequestParam int prosecnaOcena,
				@RequestParam(name="tipTreningaId", required=false) Long[] tipTreningaIds,
				HttpSession session, HttpServletResponse response) throws IOException {	
			
			// validacija
			Trening trening = treningService.findOne(id);
			
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

			response.sendRedirect(bURL + "treninzi");
		}
		
		/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
		//POST: treninzi/delete
		@SuppressWarnings("unused")
		@PostMapping(value="/delete")
		public void delete(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {		
			Trening deleted = treningService.delete(id);
			response.sendRedirect(bURL+"treninzi");
		}
	
	
}