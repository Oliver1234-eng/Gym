package com.ftn.TeretanaVebProjekat.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import com.ftn.TeretanaVebProjekat.model.Trening;

//import com.ftn.TeretanaVebProjekat.controller.ClanskeKarteController;
//import com.ftn.TeretanaVebProjekat.controller.KnjigeController;
//import com.ftn.TeretanaVebProjekat.model.Knjiga;

@Component
public class InitHttpSessionListener implements HttpSessionListener {

	/** kod koji se izvrsava po kreiranju sesije */
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Inicijalizacija sesisje HttpSessionListener...");

		// pri kreiranju sesije inicijalizujemo je ili radimo neke dodatne aktivnosti	
		HttpSession session  = event.getSession();
		System.out.println("Session id korisnika je "+ session.getId());

		//session.setAttribute(TreninziController.POSECENI_TRENINZI_ZA_KORISNIKA_KEY, new ArrayList<Trening>());

		System.out.println("Uspeh HttpSessionListener!");
	}
	
	/** kod koji se izvrsava po brisanju sesije */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Brisanje sesisje HttpSessionListener...");
		
		System.out.println("Uspeh HttpSessionListener!");
	}
}


