package services;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
	
	//URLs
	static final String urlCalendarioResultadosBBVA = "http://www.marca.com/futbol/primera/calendario.html";
	static final String urlEquiposBBVA= "http://www.marca.com/futbol/primera/clasificacion.html";
	
	//css classes
	static final String classJornada = "jornadaCalendario";
	static final String classVisitante = "visitante";
	static final String classEquipo = "equipo";
	static final String classFechaJornada = "fechaJornada";
	static final String classLoval = "local";
	
	//html tags
	static final String tagH2 = "h2";
	static final String tagH3 = "h3";
	
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
	@Scheduled(cron="*/5 * * * * ?")
	public void inicializaCalendario() throws IOException {
		/* Primero tengo que crear la temporada, después tengo que añadir los equipos a la temporada, después tengo que crear la jornada,
		 * cuando cree la jornada creo todos los partidos asociados a esa jornada.
		 */
		
		String urlCalendarioResultados = "";
		String urlEquipos = "";
		Collection<League> leagues = leagueService.findAll();		
		
		for(League league : leagues){
			if ("BBVA".equals(league.getName())) {
				urlEquipos = urlEquiposBBVA;
				urlCalendarioResultados= urlCalendarioResultadosBBVA;
			}else{
				break;
			}
			
			//Primero creo la temporada
			Document doc = Jsoup.connect(urlCalendarioResultados).get();
			String title = doc.title();
			Season season = new Season();
			List<Date> startDateAndFinishDate = Parser.getStartDateAndFinishDate(title);
			season.setStartDate(startDateAndFinishDate.get(0));
			season.setFinishDate(startDateAndFinishDate.get(1));
			season.setLeague(league);
			season.setRounds(new LinkedList<Round>());
			season.setTeams(new LinkedList<Team>());
			season = seasonService.saveEasy(season);
			
			//Añado equipos a la temporada
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
			
			
			
			//Añado jornadas a la temporada
			doc = Jsoup.connect(urlCalendarioResultados).get();
			title = doc.title();
			Elements elementJornadas = doc.getElementsByClass(classJornada);								
			for (Element elementJornada : elementJornadas) {
				Elements elementNumJornada = elementJornada.getElementsByTag(tagH2);
				String numeroJornada = elementNumJornada.get(0).text();
				String fechaJornada = elementJornada.getElementsByClass(classFechaJornada).get(0).text();
				List<Date>fechas = Parser.parseFecha(fechaJornada,title);
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
					
					String equipoLocal = elementPartido.getElementsByClass(classLoval).text();
					String equipoVisitante = elementPartido.getElementsByClass(classVisitante).text();
					Team localTeam = teamService.findByTeamName(equipoLocal);
					Team awayTeam = teamService.findByTeamName(equipoVisitante);
					Game game = new Game();
					game.setHomeTeam(localTeam);
					game.setAwayTeam(awayTeam);
					game.setRound(round);
					game.setPredictions(new LinkedList<Prediction>());
					game = gameService.saveEasy(game);

				}						
				
			}			
			
		}

	}

}
