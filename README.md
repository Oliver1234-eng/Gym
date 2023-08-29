# Implementirati web aplikaciju za rad teretane koju koriste prijavljeni i neprijavljeni korisnici.
Među prijavljene korisnike ubrajamo članove teretane i administratore.
Trening
● Naziv
● Trener/i
● Kratak opis
● Slika
● Tip treninga
● Cena
● Vrsta treninga (pojedinačni ili grupni)
● Nivo treninga (amaterski/srednji/napredni)
● Trajanje treninga
● Prosečna ocena (generiše aplikacija)
Tip treninga (npr. Joga, Fitness, Kardio…)
● Ime
● Opis
Članska kartica
● Popust
● Broj poena
Korisnik
● Korisničko ime
● Lozinka
● Email adresa
● Ime
● Prezime
● Datum rođenja
● Adresa
● Broj telefona
● Datum i vreme registracije (generiše aplikacija)
● Uloga (član teretane ili administrator)
Sala
● Oznaka sale
● Kapacitet
Termin održavanja treninga
● Sala u kojoj se održava trening
● Trening koji se održava
● Datum kada se trening održava
Komentar
● Tekst komentara
● Ocena (od 1 do 5)
● Datum kada je komentar postavljen
● Autor komentara (registrovani korisnik)
● Trening na koji se odnosi komentar
● Status
● Anoniman (Korisnik može označiti da komentar bude anoniman i u tom slučaju se ne
navodi autor komentara)
Status komentara
● Na čekanju
● Odobren
● Nije odobren
Pri registraciji, uloga se ne unosi, a korisnik dobija ulogu člana teretane. Administratori se ne
registruju, dodaju se programski (kroz bazu podataka). Tipovi treninga se takođe dodaju
programski kroz bazu podataka.
Svaki trening može da ima više nego jedan tip treninga, a svakom tipu treninga pripada jedan ili
više treninga.
Modelovati klijentsku korpu (shopping cart) kao objekat gde bi se čuvali svi odabrani treninzi za
zakazivanje. Ponašanje vezano za shopping cart dato je u nastavku specifikacije.
Korisnik prilikom svakog zakazivanja ima mogućnost da zakaže jedan ili više treninga.
Potrebno je voditi računa o kapacitetu sale prilikom zakazivanja, kao i o tome da korisnik ne
može da zakaže više različitih treninga u isto vreme.
Treninzi
Na podrazumevanoj, glavnoj stranici aplikacije potrebno je svim korisnicima omogućiti pregled
svih dostupnih treninga (onih za koje nije popunjen kapacitet sale). Ova stranica treba da
prikaže sliku treninga, naziv, cenu i prosečnu ocenu. Ime treninga treba da bude realizovano
kao link, gde će korisnik klikom na njega preći na stranicu za pregled jednog treninga. Na
glavnoj stranici je potrebno omogućiti pretragu treninga po nazivu, tipu, ceni (sa mogućnošću
unosa minimalne i maksimalne cene), trenerima, vrsti i nivou treninga.
Omogućiti i sortiranje treninga po svakom od pomenutih kriterijuma pretrage (i dodatno osim
ovih parametara omogućiti sortiranje treninga po prosečnoj oceni), po rastućem ili opadajućem
poretku.
Prethodno pomenuta stranica za prikaz pojedinačnog treninga treba da prikaže sve podatke o
treningu uključujući i termine kada se trening održava i u kojoj sali se održava. Članovima
teretane na ovoj stranici omogućiti dodavanje treninga u korpu za zakazivanje termina
(shopping cart) tako što će odabrati željeni termin iz spiska termina, ukoliko kapacitet sale nije
popunjen. Pored ove opcije, na stranici pojedinačnog treninga, omogućiti korisniku i dodavanje
treninga u ličnu listu želja (lista koju korisnik čuva kako bi vodio računa o nekim treninzima na
koje bi išao u budućnosti).
Na stranici za prikaz pojedinačnog treninga, administratorima omogućiti izmenu podataka o
treningu.
Na stranici pojedinačnog treninga, potrebno je prikazati i postavljene komentare o treningu.
Administratorima je potrebno omogućiti funkcionalnost za dodavanje novog treninga. Takođe,
potrebno je omogućiti i dodavanje novog termina treninga. Prilikom dodavanja termina potrebno
je izabrati trening koji će se održavati, salu u kojoj će se trening održavati i uneti datum i vreme
održavanja termina. Voditi računa da se ne mogu dva različita treninga održavati u istoj sali u
isto vreme (napomena: voditi računa da preklapanje termina treninga u jednoj sali nije samo
situacija u kojoj dva različita treninga traju od 8 do 12h, već i situacije u kojoj jedan traje od 8 do
12h, a drugi od 9 do 13h. U ovim situacijama se treninzi delimično preklapaju, te je moguće da
samo jedan bude održan u datoj sali).
Sala
Potrebno je omogućiti administratorima na početnoj stranici link za rad sa salama. Na stranici za
prikaz svih sala omogućiti administratoru pretragu po oznaci sale, kao i rastuće i opadajuće
sortiranje po tom obeležju. Za svaku od sala omogućiti brisanje, pri čemu voditi računa da se ne
može obrisati sala u kojoj postoji zakazan trening.
Omogućiti administratorima dodavanje i ažuriranje sala, pri čemu je oznaka sale jedinstveno
obeležje i ne može se menjati.
Korisnička korpa (Shopping cart)
Omogućiti korisniku pristup posebnoj stranici za prikaz sadržaja korisničke korpe. Za svaku od
stavki u korpi navesti naziv treninga, trenera, tip treninga, datum i vreme održavanja i cenu.
Pružiti korisniku mogućnost izbacivanja stavke iz korpe.
Na ovoj stranici korisniku omogućiti i funkcionalnost zakazivanja treninga, gde će korisnik
izborom akcije (npr. klikom na dugme) zakazati izabrane treninge.
Prilikom zakazivanja, na loyalty kartici se dodaju bodovi koje korisnik moze da iskoristi za
dobijanje popusta (nakon zakazivanja broj bodova koje je korisnik iskoristio se briše sa kartice i
ne može se ponovo iskoristiti). Na svakih potrošenih 500 dinara korisnik dobija 1 bod. 1 bod
donosi popust od 5%. Korisnik može prilikom zakazivanja da iskoristi više bodova i na taj način
ostvari veći popust, ali ne može da iskoristi više od 5 bodova na jedno zakazivanje. Napomena:
korisnik po registraciji ne dobija loyalty karticu, ali može da zatraži njeno kreiranje.
Korisnik može da ima popust na zakazivanje ukoliko su specijalni datumi (specijalne datume i
količinu popusta za taj datum definiše admin) i prilikom tog zakazivanja korisnik ne dobija
bodove i ne može da iskoristi bodove kako bi dobio dodatni popust.
Administratoru je potrebno omogućiti kalendarni prikaz godine u okviru kog će moći svaki datum
da obeleži kao specijalni i za svaki specijalni datum da definiše popust (u procentima) koji će taj
datum važiti na celokupno zakazivanje.
Loyalty kartica (Članska kartica)
U prethodnom odeljku opisan je način upotrebe loyalty kartice ukoliko je korisnik poseduje.
Ukoliko je ne poseduje, korisnik ima pravo tj. mogućnost slanja zahteva za kreiranje loyalty
kartice. Sa druge strane administrator ima pregled svih zahteva za kreiranje loyalty kartica i ima
mogućnost da ih odobri ili odbije. Kada se zahtev odobri, kreira se kartica za konkretnog
korisnika sa početnih 10 bodova dobrodošlice. Ukoliko administrator odbije zahtev, korisnik
može ponovo da ga pošalje.
Nakon što je zahtev odobren, korisnik može da koristi svoju karticu u skladu sa opisan načinom
u prethodnom odeljku.
Komentari
Obični korisnici (članovi teretane) imaju mogućnost komentarisanja treninga koje su zakazali.
Korisniku na stranici pojedinačnog treninga omogućiti opciju komentarisanja (ukoliko je
zakazao), gde on navodi komentar u slobodnoj formi i ostavlja ocenu (od 1 do 5), pri čemu su
oba polja obavezna (i ocena i komentar). Kada korisnik ostavi komentar on neće biti javno vidljiv
tj. drugi korisnici ga neće videti, dok god ga admin ne odobri.
Administratorima omogućiti na početnoj stranici link ka upravljanju komentarima, gde se
prikazuju svi komentari koji čekaju da budu odobreni. Administrator može da odobri ili odbije
komentar. Ukoliko ga odobri on će biti javan i vidljiv na stranici konkretnog treninga, a ukoliko ga
odbije on neće biti vidljiv ali ga nije potrebno fizički obrisati iz baze i takođe ga nije potrebno
prikazivati sledeći put na stranici komentara koji čekaju odobrenje. Jednom kada administrator
odobri komentar, potrebno je ažurirati srednju ocenu treninga.
Rad sa korisnicima
Neprijavljeni korisnik može da se registruje na sistem. Na glavnoj stranici je neprijavljenim
korisnicima potrebno omogućiti link ka stranici za registraciju i prijavu. Nakon registracije,
korisnik biva preusmeren na stranicu za prijavu. Prijavljenim korisnicima je na svim
stranicama potrebno omogućiti link ka stranici njihovog profila i link za odjavu.
Administratorima je potrebno omogućiti opciju za upravljanje korisnicima. Na toj stranici
je potrebno prikazati tabelarno sve korisnike sa korisničkim imenom (koje je potrebno prikazati
kao link, gde se klikom na njega prelazi na profil korisnika), datumom registracije i korisničkom
ulogom. Potrebno je omogućiti pretragu korisnika po korisničkom imenu i ulozi (omogućiti odabir
među ponuđenim vrednostima). Potrebno je omogućiti i sortiranje korisnika po korisničkom
imenu i ulozi (omogućiti odabir među ponuđenim vrednostima), po rastućem ili opadajućem
poretku.
Na stranici svakog korisnika potrebno je prikazati sve podatke osim lozinke i omogućiti njihovu
izmenu. Izmenu lozinke potrebno je implementirati tako da ukoliko se ne unese, ona ostaje ista.
Takođe, omogućiti da je korisnik unose kroz dva unosa (unos i potvrda). Potrebno je izvršiti
validaciju unetih lozinki i dopustiti ažuriranje samo ukoliko se one poklapaju.
Administratorima je na profilu nekog korisnika, potrebno dozvoliti samo izmenu uloge za nekog
korisnika (omogućiti odabir među ponuđenim vrednostima), kao i mogućnost blokiranja i
deblokiranja korisnika (moguće je blokirati samo obične korisnike, ali ne i administratore).
Blokiran korisnik, nema mogućnosti prijave na sistem.
Korisnicima čija se stranica posmatra i administratorima je potrebno prikazati tabelu zakazanih
treninga (tog korisnika) sa ukupnom cenom i datumom i vremenom kada je zakazao (datum i
vreme treba da bude implementirano kao link ka stranici te konkretne rezervacije), sortiranu po
opadajućem poretku (opadajućem datumu i vremenu - na vrhu se nalaze najskorije rezervacije).
Običnom korisniku na profilu potrebno je prikazati tabelarno i sve što se trenutno nalazi u
njegovoj listi želja, gde se korisniku za svaki od treninga na listi želja omogućuje da neki od
treninga ukloni iz liste želja.
Na profilu korisnika je potrebno prikazati sve njegove zakazane treninge s mogućnošću
otkazivanja istog najkasnije 24h pre održavanja tog treninga.
Izveštavanje
Administratorima je potrebno omogućiti pristup posebnoj stranici za izveštavanje. Za unet opseg
datuma potrebno je prikazati tabelu svih treninga koji su bili zakazani u zadatom periodu i za
svaki prikazati:
1. naziv treninga (link ka njegovoj stranici)
2. trenere
3. broj zakazanih treninga
4. ukupnu cenu za te treninge (broj zakazanih treninga*jedinična cena treninga).
Potrebno je omogućiti sortiranje tabele po svima kolonama osim imena treninga i trenera, po
rastućem i opadajućem poretku.
U poslednjem redu tabele prikazati i ukupan broj svih zakazanih treninga i ukupnu cenu svih
zakazanih treninga.
