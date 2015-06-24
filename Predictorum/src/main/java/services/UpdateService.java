package services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Game;
import domain.League;
import domain.Prediction;
import domain.Round;
import domain.Season;
import domain.Team;
import domain.User;
import utilities.Parser;


@Service
public class UpdateService {
	
	//NombreLigas
	static final String BBVA = "BBVA";
	static final String adelante = "Adelante";
	static final String bundesliga = "Bundesliga";
	static final String calcio = "Calcio";
	static final String premier = "Premier";	
	
	//URLs Equipos
	static final String urlEquiposBBVA= "http://www.marca.com/futbol/primera/clasificacion.html";
	static final String urlEquiposAdelante= "http://www.marca.com/futbol/segunda/clasificacion.html";
	static final String urlEquiposBundesliga= "http://www.marca.com/deporte/central-de-datos/futbol/bundesliga/2014-2015/clasificacion.html";
	static final String urlEquiposCalcio = "http://www.marca.com/deporte/central-de-datos/futbol/calcio/2014-2015/clasificacion.html";
	static final String urlEquiposPremier = "http://www.marca.com/deporte/central-de-datos/futbol/premier-league/2014-2015/clasificacion.html";
	
	//URLs Calendario 
	static final String urlCalendarioResultadosBBVA = "http://www.marca.com/futbol/primera/calendario.html";
	static final String urlCalendarioResultadosAdelante = "http://www.marca.com/futbol/segunda/calendario.html";
	static final String urlCalendarioResultadosBundesliga = "http://www.marca.com/deporte/central-de-datos/futbol/bundesliga/2014-2015/calendario.html";
	static final String urlCalendarioResultadosCalcio = "http://www.marca.com/deporte/central-de-datos/futbol/calcio/2014-2015/calendario.html";
	static final String urlCalendarioResultadosPremier = "http://www.marca.com/deporte/central-de-datos/futbol/premier-league/2014-2015/calendario.html";	
	
	//css classes
	static final String classJornada = "jornadaCalendario";
	static final String classVisitante = "visitante";
	static final String classEquipo = "equipo";
	static final String classFechaJornada = "fechaJornada";
	static final String classLocal = "local";
	static final String classJor = "jor";
	
	//html tags
	static final String tagH2 = "h2";
	static final String tagH3 = "h3";
	static final String tagTh = "th";
	static final String tagTr = "tr";
	static final String tagTbody = "tbody";
	
    private static final Logger LOG = Logger.getLogger(UpdateService.class);

	
	@Autowired
	private LeagueService leagueService;

	@Autowired
	private SeasonService seasonService;
	
	@Autowired
	private RoundService roundService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private TeamService teamService;

	//@Scheduled(cron = "0 0 2 * * *")
	@Scheduled(cron="*/120 * * * * ?")
	public void inicializaCalendario() throws IOException {
		/* Primero tengo que crear la temporada, después tengo que añadir los equipos a la temporada, después tengo que crear la jornada,
		 * cuando cree la jornada creo todos los partidos asociados a esa jornada.
		 */
		
		String urlCalendarioResultados = "";
		String urlEquipos = "";
		Collection<League> leagues = leagueService.findAll();		
		
		for(League league : leagues){
			String leagueName = league.getName();
			if (BBVA.equals(leagueName)) {
				urlCalendarioResultados= urlCalendarioResultadosBBVA;
				urlEquipos = urlEquiposBBVA;
			}else if(adelante.equals(leagueName)){
				urlCalendarioResultados= urlCalendarioResultadosAdelante;
				urlEquipos = urlEquiposAdelante;
			}else if(bundesliga.equals(leagueName)){
				urlCalendarioResultados= urlCalendarioResultadosBundesliga;
				urlEquipos = urlEquiposBundesliga;
			}else if(calcio.equals(leagueName)){
				urlCalendarioResultados= urlCalendarioResultadosCalcio;
				urlEquipos = urlEquiposCalcio;
			}else if(premier.equals(leagueName)){
				urlCalendarioResultados= urlCalendarioResultadosPremier;
				urlEquipos = urlEquiposPremier;
			}else{
				break;
			}
			
			//Primero creo la temporada
			Season season = createSeason(league);
						
			//Añado equipos a la temporada
			addTeamToSeason(urlEquipos,season);			
			
			/*Añado jornadas a la temporada
			 * 
			 * El html para las ligas bbva y adelante tienen un formato diferente por lo que habrá que hacer distinción según el nombre de la liga
			 * 
			 */			
			if(BBVA.equals(leagueName)||adelante.equals(leagueName)){
				addRoundToSeasonFormatSpanishLeagues(urlCalendarioResultados,season);
			}else{
				addRoundToSeasonFormatOthersLeagues(urlCalendarioResultados,season);
			}
				
			
		}

	}
	
	private Season createSeason(League league){
		Date actual = new Date();
		Calendar startCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(actual);
		int realMonth = Calendar.MONTH;
		int realYear = Calendar.YEAR;
		if(realMonth>7){
			startCalendar.set(realYear,6,2);
			finishCalendar.set(realYear+1,6,1);
		}else{
			startCalendar.set(realYear-1,6,2);
			finishCalendar.set(realYear,6,1);
		}
		Date startDate = startCalendar.getTime();
		Date finishDate = finishCalendar.getTime();
		
		Season season = new Season();
		season.setStartDate(startDate);
		season.setFinishDate(finishDate);
		season.setLeague(league);
		season.setRounds(new LinkedList<Round>());
		season.setTeams(new LinkedList<Team>());
		season = seasonService.saveEasy(season);
		
		return season;
		
	}

	private void addTeamToSeason(String urlEquipos, Season season) {		
		Document doc;
		try {
			doc = Jsoup.connect(urlEquipos).get();
			Elements elementEquipos = doc.getElementsByClass(classEquipo);
			Collection<User> users = new LinkedList<User>();
			Collection<Game> games = new LinkedList<Game>();
			for(Element elementEquipo: elementEquipos){
				Team team = new Team();
				team.setName(elementEquipo.text());
				team.setSeason(season);
				team.setUsers(users);
				team.setAwayMatchs(games);
				team.setHomeMatchs(games);
				teamService.saveEasy(team);
			}
		} catch (IOException e) {			
			LOG.info("No se pudo actualizar los equipos con la url: "+urlEquipos);
		}
	}	
	
	private void addRoundToSeasonFormatSpanishLeagues(String urlCalendarioResultados, Season season) {
		
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			String	title = doc.title();
			Elements elementJornadas = doc.getElementsByClass(classJornada);								
			for (Element elementJornada : elementJornadas) {
				Elements elementNumJornada = elementJornada.getElementsByTag(tagH2);
				String numeroJornada = elementNumJornada.get(0).text();	
				List<Date>fechas = new LinkedList<Date>();			
				String fechaJornada = elementJornada.getElementsByClass(classFechaJornada).get(0).text();
				fechas = Parser.parseFecha(fechaJornada,title);			
				Round round = new Round();
				round.setStartDate(fechas.get(0));
				round.setFinishDate(fechas.get(1));
				round.setSeason(season);
				round.setRoundNumber(Parser.getRoundNumber(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);			
				round = roundService.saveEasy(round);
				
				//Añado partidos a las jornadas
				Elements elementPartidos = elementJornada.getElementsByTag(tagH3);
				for (Element elementPartido : elementPartidos) {
					
					String equipoLocal = elementPartido.getElementsByClass(classLocal).text();
					String equipoVisitante = elementPartido.getElementsByClass(classVisitante).text();
					Team localTeam = teamService.findByTeamName(equipoLocal);
					Team awayTeam = teamService.findByTeamName(equipoVisitante);
					Assert.notNull(localTeam);
					Assert.notNull(awayTeam);
					Game game = new Game();
					game.setHomeTeam(localTeam);
					game.setAwayTeam(awayTeam);
					game.setRound(round);
					game.setPredictions(new LinkedList<Prediction>());
					game = gameService.saveEasy(game);

				}						
				
			}
			
		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+season.getLeague().getName());
		}
			

	}
	
	private void addRoundToSeasonFormatOthersLeagues(String urlCalendarioResultados, Season season) {
		
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadas = doc.getElementsByClass(classJor);								
			for (Element elementJornada : elementJornadas) {
				Elements elementsTh = elementJornada.getElementsByTag(tagTh);
				//Para cada classJor solo habrá dos th, uno para el número de jornada y otro para la fecha
				Assert.isTrue(elementsTh.size()==2);
				String numeroJornada = elementsTh.get(0).text();	
				String cadenaFechas = elementsTh.get(1).text();	
				List<Date>fechas = new LinkedList<Date>();			
				fechas = Parser.parseFecha(cadenaFechas);			
				Round round = new Round();
				round.setStartDate(fechas.get(0));
				round.setFinishDate(fechas.get(1));
				round.setSeason(season);
				round.setRoundNumber(Parser.getRoundNumber(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);			
				round = roundService.saveEasy(round);
				
				//Añado partidos a las jornadas
				Element jornadaAux = elementJornada.getElementsByTag(tagTbody).get(0);
				Elements elementPartidos = jornadaAux.getElementsByTag(tagTr);
				for (Element elementPartido : elementPartidos) {
					
					String equipoLocal = elementPartido.getElementsByClass(classLocal).text();
					String equipoVisitante = elementPartido.getElementsByClass(classVisitante).text();
					Team localTeam = teamService.findByTeamName(equipoLocal);
					Team awayTeam = teamService.findByTeamName(equipoVisitante);
					Assert.notNull(localTeam);
					Assert.notNull(awayTeam);					
					Game game = new Game();
					game.setHomeTeam(localTeam);
					game.setAwayTeam(awayTeam);
					game.setRound(round);
					game.setPredictions(new LinkedList<Prediction>());
					game = gameService.saveEasy(game);

				}						
				
			}
			
		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+season.getLeague().getName());
		}

	}


}
