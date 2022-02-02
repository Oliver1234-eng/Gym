package com.ftn.TeretanaVebProjekat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreningStatistika {

	private Map<Long, TreningBrojac> popularniTreninzi;

	public TreningStatistika() {
		popularniTreninzi = new HashMap<>();
	}

	public void incrementBrojac(Trening trening) {
		TreningBrojac brojac = popularniTreninzi.get(trening.getId());
		if (brojac == null) {
			brojac = new TreningBrojac(trening);
			popularniTreninzi.put(trening.getId(), brojac);
		}
		brojac.incrementBrojac();
	}

	public List<TreningBrojac> getTreninzi() {
		List<TreningBrojac> sorted = new ArrayList<TreningBrojac>(popularniTreninzi.values());
		Collections.sort(sorted, new SorterFilmova());

		return sorted;
	}

	public boolean isEmpty() {
		return popularniTreninzi.isEmpty();
	}
	
	public int getMax() {
		return getTreninzi().get(0).getBrojac();
	}

	private class SorterFilmova implements Comparator<TreningBrojac> {

		int direction = -1;
		
		@Override
		public int compare(TreningBrojac brojacA, TreningBrojac brojacB) {
			if (brojacA.getBrojac() == brojacB.getBrojac())
				return brojacA.getTrening().getNaziv().compareTo(brojacB.getTrening().getNaziv());
			
			return Integer.compare(brojacA.getBrojac(), brojacB.getBrojac())*direction;
		}

	}
}
