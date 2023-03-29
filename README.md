# labA_Uninsubria
Repository per il corso di laboratorio interdisciplinare A, anno accademico 2022/2023\
Partecipanti:\
Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it\
Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it

# Climate Monitoring\
Un sistema di monitoraggio di parametri climatici\
fornito da centri di monitoraggio sul territorio italiano, in grado\
di rendere disponibili, a operatori ambientali e comuni cittadini\
i dati relativi alla propria zona d'interesse.

+ parametri rilevati per una zona geografica -> indicano l'intensità\
del fenomeno su una scala che va da 1(critico) a 5(ottimale)
+ Note opzionali testuali (256 max caratteri)
---
*A partire da un repository di dati da costruire, l'applicazione permette:*

	+ Ad operatori autorizzati:
	-> Di creare una o piu' aree d'interesse (tramite coordinate
		geografiche) per raggrupparle per centro di monitoraggio
		e annotarle singolarmente con i parametri forniti a un
		operatore in una specifica data, secondo i parametri dati
		nella tabella precedente.
	+ Ai comuni cittadini:
	-> Di mostrare un prospetto riassuntivo sui parametri climatici
	in forma aggregata per ciascuna area d'interesse.
---
*Per consultare le informazioni di ogni area d'interesse(non è necessario in o registrazione)*

	1. Funzionalita' di ricerca *cercaAreaGeografica()*
		- Ricerca per denominazione (prende in input una stringa di
		caratteri e restituisce le aree nel cui nome compare la stringa di caratteri)
		e per Stato di appartenenza.
		- Ricerca per coordinate geografiche: prende in input una
		latitudine e restituisce il nome dell'area corrispondente alle
		coordinate geografiche/delle aree corrispondenti con coordinate
		piu' vicine.
	2. Funzionalita' di selezione e visualizzazione *visualizzaAreaGeografica()*
		- Una volta trovata l'area d'interesse 
--
*Per registrarsi all'applicazione, tramite la funzione registrazione() l'operatore deve inserire:*

	* Nome e cognome
	* codice fiscale
	* indirizzo di posta elettronica
	* userid
	* password per accedere al sistema
	* centro di monitoraggio di afferenza (Se presente, altrimenti si veda
		la creazione di altri centri monitoraggio)
---
*Per creare i centri e le aree d'interesse, il primo operatore:*

	1. deve proseguire dopo la sua registrazione oppure autenticarsi tramite
		userid e password fornite al momento della registrazione
	2. tramite la funzione *registraCentroAree()* 
		* Nome Centro monitoraggio
		* Indirizzo fisico(via/piazza, numero civico, cap, comune, provincia)
		* Elenco aree di interesse di cui l'operatore intende inserire i
			parametri climatici.
---
*Per inserire eventuali parametri climatici, l'operatore registrato:*

	1. deve autenticarsi tramite userid e password fornite al momento
		della registrazione.
	2. può ricercare e selezionare l'area di interesse all'intero delle
		aree registrate per il suo centro di monitoraggio.
	3. può ora usare la funzione *inserisciParametriClimatici()*
---
*All'avvio l'applicazione mostra un menù iniziale dove:*

	1.	Tutti possono:
		* cercare aree tramite nome, stato o coordinate geografiche:
		* visualizzare i parametri climatici associati a ciascuna area
			d'interesse.
	2. 	Gli operatori autorizzati possono:
		* registrarsi all'applicazione
		* creare centri di monitoraggio con l'elenco delle aree di interesse
			(questa funzionalità può essere incorporata all'interno della
			procedurar di registrazione del primo operatore).
	3. 	Inserire i valori dei parametri climatici per un'area di interesse

---
*Riassumendo, l'applicazione permette di:*

	1. Consultare le informazioni del repository delle aree d'interesse
		(accesso libero ai comuni cittadini)
	2. Registrarsi all'applicazione(solo operatori dei centri di monitoraggio).
	3. Creare centri di monitoraggio e aggiungervi aree d'interesse
	   (solo operatori registrati e solo dopo il login)
	4. Inserire i dati rielaborati secondo la scala fornita e relativi ai
	   parametri climatici (solo operatori registrati e solo dopo login)
	   per ciascuna area d'interesse per quel centro di monitoraggio,
	   per una specifica data di rivelazione.
	5. I dati della registrazione devono essere salvati in un file denominato:
		OperatoriRegistrati.dati
	6. I dati di ogni centro monitoraggio sono memorizzati in un file dedicato nominato
		CentroMonitoraggio.dati
		Il file OperatoriRegistrati deve essere aggiornato con un riferimento al centro
		di monitoraggio appena creato, che risulterà essere il centro di riferimento dell'
		operatore.
	7.	Centro di monitoraggio, area d'interesse, data di rilevazione e parametri
		climatici associati dall'operatore sono memorizzati nel file:
		ParametriCLimatici.dati


