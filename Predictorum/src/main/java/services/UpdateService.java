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
import domain.Result;
import domain.Round;
import domain.Season;
import domain.Team;
import domain.User;
import utilities.Parser;

@Service
public class UpdateService {

	// NombreLigas
	static final String BBVA = "BBVA";
	static final String adelante = "Adelante";
	static final String bundesliga = "Bundesliga";
	static final String calcio = "Calcio";
	static final String premier = "Premier";

	// URLs Equipos
	static final String urlEquiposBBVA = "http://www.marca.com/futbol/primera/clasificacion.html";
	static final String urlEquiposAdelante = "http://www.marca.com/futbol/segunda/clasificacion.html";
	static final String urlEquiposBundesliga = "http://www.marca.com/deporte/central-de-datos/futbol/bundesliga/";
	static final String urlEquiposCalcio = "http://www.marca.com/deporte/central-de-datos/futbol/calcio/";
	static final String urlEquiposPremier = "http://www.marca.com/deporte/central-de-datos/futbol/premier-league/";
	static final String clasificacionHtml = "/clasificacion.html";

	// URLs Calendario
	static final String urlCalendarioResultadosBBVA = "http://www.marca.com/futbol/primera/calendario.html";
	static final String urlCalendarioResultadosBBVABaseHistorica = "http://www.marca.com/estadisticas/futbol/primera/";
	static final String urlCalendarioResultadosAdelante = "http://www.marca.com/futbol/segunda/calendario.html";
	static final String urlCalendarioResultadosAdelanteBaseHistorica = "http://www.marca.com/estadisticas/futbol/segunda/";
	static final String urlCalendarioResultadosBundesliga = "http://www.marca.com/deporte/central-de-datos/futbol/bundesliga/";
	static final String urlCalendarioResultadosCalcio = "http://www.marca.com/deporte/central-de-datos/futbol/calcio/";
	static final String urlCalendarioResultadosPremier = "http://www.marca.com/deporte/central-de-datos/futbol/premier-league/";
	static final String calendarioHtml = "/calendario.html";

	// css classes
	static final String classJornada = "jornadaCalendario";
	static final String classVisitante = "visitante";
	static final String classEquipo = "equipo";
	static final String classFechaJornada = "fechaJornada";
	static final String classLocal = "local";
	static final String classJor = "jor";
	static final String classResultado = "resultado";
	static final String classProx_Jor = "prox_jor";
	static final String classNavegacion_Jornadas = "navegacion-jornadas";
	static final String classEquipo_Local= "equipo-local";
	static final String classEquipo_Visitante= "equipo-visitante";

	// html tags
	static final String tagH2 = "h2";
	static final String tagH3 = "h3";
	static final String tagTh = "th";
	static final String tagTr = "tr";
	static final String tagTbody = "tbody";
	static final String tagA = "a";

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
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private TeamStatisticsService teamStatisticsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiaryService diaryService;
	
	//@Scheduled(cron = "*/800 * * * * ?")
	public void inicializaBaseHistorica() throws IOException {
		

		String urlCalendarioResultados = "";
		String urlEquipos = "";
		Collection<League> leagues = leagueService.findAll();
		//Integer anyoFin = 2015;
		List<Integer> years = new LinkedList<Integer>();
		years.add(2015);
		years.add(2014);
		years.add(2013);
		for (Integer anyoFin : years) {
			String fin = anyoFin+"";
			fin = fin.substring(2);
			String seasonDateSpanish = (anyoFin - 1) + "_"+fin;
			String seasonDateOther = (anyoFin - 1) + "-" + anyoFin;

			for (League league : leagues) {
				String leagueName = league.getName();
				if (BBVA.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosBBVABaseHistorica
							+ seasonDateSpanish;
					// en la misma pagina se pueden conseguir los equipos
					urlEquipos = urlCalendarioResultados;
				} else if (adelante.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosAdelanteBaseHistorica
							+ seasonDateSpanish;
					// en la misma pagina se pueden conseguir los equipos
					urlEquipos = urlCalendarioResultados;
				} else if (bundesliga.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosBundesliga
							+ seasonDateOther + calendarioHtml;
					urlEquipos = urlEquiposBundesliga + seasonDateOther
							+ clasificacionHtml;
				} else if (calcio.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosCalcio
							+ seasonDateOther + calendarioHtml;
					urlEquipos = urlEquiposCalcio + seasonDateOther
							+ clasificacionHtml;
				} else if (premier.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosPremier
							+ seasonDateOther + calendarioHtml;
					urlEquipos = urlEquiposPremier + seasonDateOther
							+ clasificacionHtml;
				} else {
					break;
				}
				Season season = createSeasonWithYear(league, anyoFin);

				addTeamToSeason(urlEquipos, season);

				if (BBVA.equals(leagueName) || adelante.equals(leagueName)) {
					addRoundToSeasonFormatPrimeraYSegundaBaseHistorica(
							urlCalendarioResultados, season);
				} else {
					addRoundToSeasonFormatOthersLeagues(
							urlCalendarioResultados, season);
				}

			}
		}
	}
	

	//@Scheduled(cron = "*/120 * * * * ?")
	public void actualizaBaseHistorica() throws IOException {
		/*
		 * Primero tengo que crear la temporada, despu�s tengo que a�adir los
		 * equipos a la temporada, despu�s tengo que crear la jornada, cuando
		 * cree la jornada creo todos los partidos asociados a esa jornada.
		 */

		String urlCalendarioResultados = "";
		Collection<League> leagues = leagueService.findAll();
		
		for (League league : leagues) {
			String leagueName = league.getName();
			Collection<Season> seasons = seasonService.findByLeagueId(league.getId());
			for (Season season : seasons) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(season.getFinishDate());
				Integer anyoFin = calendar.get(Calendar.YEAR);
				String fin = anyoFin + "";
				fin = fin.substring(2);
				String seasonDateSpanish = (anyoFin - 1) + "_" + fin;
				String seasonDateOther = (anyoFin - 1) + "-" + anyoFin;

				if (BBVA.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosBBVABaseHistorica
							+ seasonDateSpanish;
				} else if (adelante.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosAdelanteBaseHistorica
							+ seasonDateSpanish;
				} else if (bundesliga.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosBundesliga
							+ seasonDateOther + calendarioHtml;
				} else if (calcio.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosCalcio
							+ seasonDateOther + calendarioHtml;
				} else if (premier.equals(leagueName)) {
					urlCalendarioResultados = urlCalendarioResultadosPremier
							+ seasonDateOther + calendarioHtml;
				} else {
					break;
				}

				// Primero cojo la temporada

				/*
				 * A�ado jornadas a la temporada
				 * 
				 * El html para las ligas bbva y adelante tienen un formato
				 * diferente por lo que habr� que hacer distinci�n seg�n el
				 * nombre de la liga
				 */
				if (BBVA.equals(leagueName) || adelante.equals(leagueName)) {
					updatePrimeraYSegundaBaseHistorica(urlCalendarioResultados,
							season);
				} else {
					updateOthersLeagues(urlCalendarioResultados, season);
				}
			}

		}
	}




	private Season createSeasonWithYear(League league, Integer anyoFin) {

		Date current = new Date(System.currentTimeMillis());
		Calendar startCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		startCalendar.set(anyoFin - 1, 7, 1);
		finishCalendar.set(anyoFin, 6, 31);
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

	
	@Scheduled(cron = "0 0 0 2 8 ?")
	//@Scheduled(cron = "*/120 * * * * ?")
	public void inicializaCalendario() throws IOException {
		/*
		 * Primero tengo que crear la temporada, despu�s tengo que a�adir los
		 * equipos a la temporada, despu�s tengo que crear la jornada, cuando
		 * cree la jornada creo todos los partidos asociados a esa jornada.
		 */

		String urlCalendarioResultados = "";
		String urlEquipos = "";
		Collection<League> leagues = leagueService.findAll();
		String seasonDate = createSeasonDateString();

		for (League league : leagues) {
			String leagueName = league.getName();
			if (BBVA.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosBBVA;
				urlEquipos = urlEquiposBBVA;
			} else if (adelante.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosAdelante;
				urlEquipos = urlEquiposAdelante;
			} else if (bundesliga.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosBundesliga	+ seasonDate + calendarioHtml;
				urlEquipos = urlEquiposBundesliga + seasonDate+ clasificacionHtml;
			} else if (calcio.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosCalcio	+ seasonDate + calendarioHtml;
				urlEquipos = urlEquiposCalcio + seasonDate + clasificacionHtml;
			} else if (premier.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosPremier+ seasonDate + calendarioHtml;
				urlEquipos = urlEquiposPremier + seasonDate + clasificacionHtml;
			} else {
				break;
			}

			// Primero creo la temporada
			Season season = createSeason(league);

			// A�ado equipos a la temporada
			addTeamToSeason(urlEquipos, season);

			/*
			 * A�ado jornadas a la temporada
			 * 
			 * El html para las ligas bbva y adelante tienen un formato
			 * diferente por lo que habr� que hacer distinci�n seg�n el nombre
			 * de la liga
			 */
			if (BBVA.equals(leagueName)) {
				addRoundToSeasonFormatPrimera(urlCalendarioResultados, season);
			} else if(adelante.equals(leagueName)) {
				addRoundToSeasonFormatSegunda(urlCalendarioResultados, season);
			} else {
				addRoundToSeasonFormatOthersLeagues(urlCalendarioResultados,season);
			}

		}

	}
	
	//Martes a las 3 de la ma�ana
	//@Scheduled(cron = "0 0 3 * * 7")
	//@Scheduled(cron = "*/120 * * * * ?")
	@Scheduled(cron = "0 0 3 * * 2")
	public void actualizaCalendario() throws IOException {
		/*
		 * Primero tengo que crear la temporada, despu�s tengo que a�adir los
		 * equipos a la temporada, despu�s tengo que crear la jornada, cuando
		 * cree la jornada creo todos los partidos asociados a esa jornada.
		 */

		String urlCalendarioResultados = "";
		Collection<League> leagues = leagueService.findAll();
		String seasonDate = createSeasonDateString();

		for (League league : leagues) {
			String leagueName = league.getName();
			if (BBVA.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosBBVA;
			} else if (adelante.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosAdelante;
			} else if (bundesliga.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosBundesliga	+ seasonDate + calendarioHtml;
			} else if (calcio.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosCalcio	+ seasonDate + calendarioHtml;
			} else if (premier.equals(leagueName)) {
				urlCalendarioResultados = urlCalendarioResultadosPremier+ seasonDate + calendarioHtml;
			} else {
				break;
			}

			//Primero cojo la temporada
			Season season = seasonService.findCurrentByLeagueId(league.getId());
			if(season!=null){
				/*
				 * A�ado jornadas a la temporada
				 * 
				 * El html para las ligas bbva y adelante tienen un formato
				 * diferente por lo que habr� que hacer distinci�n seg�n el nombre
				 * de la liga
				 */
				if (BBVA.equals(leagueName)) {
					updatePrimera(urlCalendarioResultados, season);
				} else if(adelante.equals(leagueName)) {
					updateSegunda(urlCalendarioResultados, season);
				} else {
					updateOthersLeagues(urlCalendarioResultados,season);
				}
			}			

		}

	}

	private void updateOthersLeagues(String urlCalendarioResultados,Season season) {
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadas = doc.getElementsByClass(classJor);
			for (Element elementJornada : elementJornadas) {
				Elements elementsTh = elementJornada.getElementsByTag(tagTh);
				// Para cada classJor solo habr� dos th, uno para el n�mero de
				// jornada y otro para la fecha
				Assert.isTrue(elementsTh.size() == 2);
				String numeroJornada = elementsTh.get(0).text();
				Integer roundNumber = Parser.getRoundNumber(numeroJornada);
				//Cojo la ronda si no est� actualizada y la fecha ha pasado
				Round round = roundService.findNoUpdatedBySeasonIdAndRoundNumber(season.getId(),roundNumber);
				//Si existe esta ronda, tengo que actualizar los partidos
				if(round!=null){
					//Actualizo los resultados de los diferentes partidos de las jornadas
					Element jornadaAux = elementJornada.getElementsByTag(tagTbody).get(0);
					Elements elementPartidos = jornadaAux.getElementsByTag(tagTr);
					for (Element elementPartido : elementPartidos) {
						Game game = findGame(elementPartido, round);
						Result result = saveResult(elementPartido, game);
						diaryService.create(result);
						teamStatisticsService.update(result);						
					}
					round.setUpdated(true);
					roundService.saveEasy(round);	

				}

			}
			teamStatisticsService.updateLeaguePosition(season);

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}

		
	}

	private void updateSegunda(String urlCalendarioResultados, Season season) {
		//Comparar� por los n�mero de jornada
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadas = doc.getElementsByClass(classProx_Jor);
			for (Element elementJornada : elementJornadas) {
				Elements elementsTh = elementJornada.getElementsByTag(tagTh);
				// Para cada classJor solo habr� dos th, uno para el n�mero de
				// jornada y otro para la fecha
				Assert.isTrue(elementsTh.size() == 2);
				String numeroJornada = elementsTh.get(0).text();
				//Comparar� por el numJornada
				Integer roundNumber =Parser.getRoundNumber(numeroJornada);
				//Cojo la ronda si no est� actualizada y la fecha ha pasado
				Round round = roundService.findNoUpdatedBySeasonIdAndRoundNumber(season.getId(),roundNumber);
				//Si existe esta ronda, tengo que actualizar los partidos
				if(round!=null){
					//Actualizo los resultados de los diferentes partidos de las jornadas
					Element jornadaAux = elementJornada.getElementsByTag(tagTbody).get(0);
					Elements elementPartidos = jornadaAux.getElementsByTag(tagTr);
					for (Element elementPartido : elementPartidos) {
						Game game = findGame(elementPartido, round);
						Result result = saveResult(elementPartido, game);
						userService.updatePointsByResult(result);
						teamStatisticsService.update(result);						
					}
					round.setUpdated(true);
					roundService.saveEasy(round);					
				}

			}
			teamStatisticsService.updateLeaguePosition(season);

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}
				
		
	}

	private void updatePrimera(String urlCalendarioResultados, Season season) {
		
		//Comparar� por los n�mero de jornada
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadas = doc.getElementsByClass(classJornada);
			for (Element elementJornada : elementJornadas) {
				Elements elementNumJornada = elementJornada.getElementsByTag(tagH2);
				String numeroJornada = elementNumJornada.get(0).text();
				//Comparar� por el numJornada
				Integer roundNumber =Parser.getRoundNumber(numeroJornada);
				//Cojo la ronda si no est� actualizada y la fecha ha pasado
				Round round = roundService.findNoUpdatedBySeasonIdAndRoundNumber(season.getId(),roundNumber);
				//Si existe esta ronda, tengo que actualizar los partidos
				if(round!=null){
					//Actualizo los resultados de los diferentes partidos de las jornadas
					Elements elementPartidos = elementJornada.getElementsByTag(tagH3);
					for (Element elementPartido : elementPartidos) {
						Game game = findGame(elementPartido, round);
						Result result = saveResult(elementPartido, game);
						userService.updatePointsByResult(result);
						teamStatisticsService.update(result);
					}
					round.setUpdated(true);
					roundService.saveEasy(round);					
				}

			}
			teamStatisticsService.updateLeaguePosition(season);

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}		
		
	}
	
	private void updatePrimeraYSegundaBaseHistorica(String urlCalendarioResultados, Season season) {
		// Primero calculamos el numero total de jornadas
		// La URL se ir� modificando seg�n la jornada
		// Para cada jornada iremos a�adiendo los games
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadasAux = doc.getElementsByClass(classNavegacion_Jornadas);
			Elements elementJornadas = elementJornadasAux.get(0).getElementsByTag(tagA);
			for (Element elementJornada : elementJornadas) {
				String numeroJornada = elementJornada.text();
				String urlJornada = urlCalendarioResultados + "/jornada_"+ numeroJornada;
				Document doc2 = Jsoup.connect(urlJornada).get();
				Round round = roundService.findNoUpdatedBySeasonIdAndRoundNumber(season.getId(),new Integer(numeroJornada));
				// Si existe esta ronda, tengo que actualizar los partidos
				if (round != null) {
					Elements equipoLocales = doc2.getElementsByClass(classEquipo_Local);
					Elements equipoVisitantes = doc2.getElementsByClass(classEquipo_Visitante);
					Elements resultados = doc2.getElementsByClass(classResultado);
					int size = equipoLocales.size();
					for (int i = 0; i < size; i++) {
						String equipoLocal = equipoLocales.get(i).text();
						String equipoVisitante = equipoVisitantes.get(i).text();
						Game game = gameService.findByRoundIdAndLocalTeamAndAwayTeam(round.getId(), equipoLocal,equipoVisitante);
						Assert.notNull(game);
						String cadenaResultado = resultados.get(i).text();
						List<Integer> goals = Parser.result(cadenaResultado);
						Result result = new Result();
						result.setGame(game);
						result.setHomeGoals(goals.get(0));
						result.setAwayGoals(goals.get(1));
						result = resultService.save(result);
						diaryService.create(result);
						teamStatisticsService.update(result);
					}
					round.setUpdated(true);
					roundService.saveEasy(round);
				}
			}

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: " + season.getLeague().getName());
		}
		
	}
	
	
	
	public Game findGame(Element elementPartido, Round round){
		String equipoLocal = elementPartido.getElementsByClass(classLocal).text();
		String equipoVisitante = elementPartido.getElementsByClass(classVisitante).text();
		Game game = gameService.findByRoundIdAndLocalTeamAndAwayTeam(round.getId(),equipoLocal,equipoVisitante);
		return game;
	}
	
	public Result saveResult(Element elementPartido, Game game){
		Assert.notNull(game);		
		String cadenaResultado = elementPartido.getElementsByClass(classResultado).text();
		List<Integer> goals = Parser.result(cadenaResultado);
		Result result = new Result();
		result.setGame(game);
		result.setHomeGoals(goals.get(0));
		result.setAwayGoals(goals.get(1));						
		result = resultService.save(result);
		return result;
	}

	private String createSeasonDateString() {
		String seasonDate = "";
		Date current = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		int realMonth = calendar.get(Calendar.MONTH) + 1;
		int realYear = calendar.get(Calendar.YEAR);
		if (realMonth > 7) {
			Integer start = realYear;
			Integer finish = realYear + 1;
			seasonDate = start + "-" + finish;

		} else {
			Integer start = realYear - 1;
			Integer finish = realYear;
			seasonDate = start + "-" + finish;
		}
		return seasonDate;
	}

	private Season createSeason(League league) {
		Date current = new Date(System.currentTimeMillis());
		Calendar startCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		int realMonth = calendar.get(Calendar.MONTH) + 1;
		int realYear = calendar.get(Calendar.YEAR);
		if (realMonth > 7) {
			startCalendar.set(realYear, 7, 1);
			finishCalendar.set(realYear + 1, 6, 31);
		} else {
			startCalendar.set(realYear - 1, 7, 1);
			finishCalendar.set(realYear, 6, 31);
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
			for (Element elementEquipo : elementEquipos) {
				Team team = new Team();
				team.setName(elementEquipo.text());
				team.setSeason(season);
				team.setUsers(users);
				team.setAwayMatchs(games);
				team.setHomeMatchs(games);
				team = teamService.saveEasy(team);
				teamStatisticsService.createAndSave(team);
			}
		} catch (IOException e) {
			LOG.info("No se pudo actualizar los equipos con la url: "+ urlEquipos);
		}
	}

	private void addRoundToSeasonFormatPrimera(String urlCalendarioResultados,
			Season season) {

		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			String title = doc.title();
			Elements elementJornadas = doc.getElementsByClass(classJornada);
			for (Element elementJornada : elementJornadas) {
				Elements elementNumJornada = elementJornada.getElementsByTag(tagH2);
				String numeroJornada = elementNumJornada.get(0).text();
				List<Date> fechas = new LinkedList<Date>();
				String fechaJornada = elementJornada.getElementsByClass(classFechaJornada).get(0).text();
				fechas = Parser.parseFecha(fechaJornada, title);
				Round round = new Round();
				round.setStartDate(fechas.get(0));
				round.setFinishDate(fechas.get(1));
				round.setSeason(season);
				round.setRoundNumber(Parser.getRoundNumber(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);
				round = roundService.saveEasy(round);

				// A�ado partidos a las jornadas
				Elements elementPartidos = elementJornada.getElementsByTag(tagH3);
				for (Element elementPartido : elementPartidos) {
					saveGame(elementPartido, round);
				}

			}

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}

	}
	
	private void addRoundToSeasonFormatPrimeraYSegundaBaseHistorica(String urlCalendarioResultados, Season season) {
		//Primero calculamos el numero total de jornadas
		//La URL se ir� modificando seg�n la jornada
		//Para cada jornada iremos a�adiendo los games
		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();			
			Elements elementJornadasAux = doc.getElementsByClass(classNavegacion_Jornadas);
			Elements elementJornadas = elementJornadasAux.get(0).getElementsByTag(tagA);
			for (Element elementJornada : elementJornadas) {
				String numeroJornada  = elementJornada.text();
				String urlJornada = urlCalendarioResultados+"/jornada_"+numeroJornada;
				Document doc2 = Jsoup.connect(urlJornada).get();				
				Round round = new Round();
				//La fecha de la jornada no importa pero lo podremos modificar en el futuro si lo deseamos
				round.setStartDate(season.getStartDate());
				round.setFinishDate(season.getFinishDate());
				round.setSeason(season);
				round.setRoundNumber(new Integer(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);
				round = roundService.saveEasy(round);
				
				Elements equipoLocales = doc2.getElementsByClass(classEquipo_Local);
				Elements equipoVisitantes = doc2.getElementsByClass(classEquipo_Visitante);
				int size = equipoLocales.size();;
				for(int i = 0;i<size;i++){
					String equipoLocal = equipoLocales.get(i).text();
					String equipoVisitante = equipoVisitantes.get(i).text();
					Team localTeam = teamService.findByTeamNameAndSeasonId(equipoLocal,round.getSeason().getId());
					Team awayTeam = teamService.findByTeamNameAndSeasonId(equipoVisitante,round.getSeason().getId());
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
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}
		
	}

	private void addRoundToSeasonFormatSegunda(
			String urlCalendarioResultados, Season season) {

		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			String title = doc.title();
			Elements elementJornadas = doc.getElementsByClass(classProx_Jor);
			for (Element elementJornada : elementJornadas) {
				Elements elementsTh = elementJornada.getElementsByTag(tagTh);
				// Para cada classJor solo habr� dos th, uno para el n�mero de
				// jornada y otro para la fecha
				Assert.isTrue(elementsTh.size() == 2);
				String numeroJornada = elementsTh.get(0).text();
				String cadenaFechas = elementsTh.get(1).text();
				List<Date> fechas = new LinkedList<Date>();
				fechas = Parser.parseFecha(cadenaFechas, title);
				Round round = new Round();
				round.setStartDate(fechas.get(0));
				round.setFinishDate(fechas.get(1));
				round.setSeason(season);
				round.setRoundNumber(Parser.getRoundNumber(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);
				round = roundService.saveEasy(round);

				// A�ado partidos a las jornadas
				Element jornadaAux = elementJornada.getElementsByTag(tagTbody).get(0);
				Elements elementPartidos = jornadaAux.getElementsByTag(tagTr);
				for (Element elementPartido : elementPartidos) {
					saveGame(elementPartido, round);
				}

			}

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}

	}

	private void addRoundToSeasonFormatOthersLeagues(String urlCalendarioResultados, Season season) {

		Document doc;
		try {
			doc = Jsoup.connect(urlCalendarioResultados).get();
			Elements elementJornadas = doc.getElementsByClass(classJor);
			for (Element elementJornada : elementJornadas) {
				Elements elementsTh = elementJornada.getElementsByTag(tagTh);
				// Para cada classJor solo habr� dos th, uno para el n�mero de
				// jornada y otro para la fecha
				Assert.isTrue(elementsTh.size() == 2);
				String numeroJornada = elementsTh.get(0).text();
				String cadenaFechas = elementsTh.get(1).text();
				List<Date> fechas = new LinkedList<Date>();
				fechas = Parser.parseFecha(cadenaFechas);
				Round round = new Round();
				round.setStartDate(fechas.get(0));
				round.setFinishDate(fechas.get(1));
				round.setSeason(season);
				round.setRoundNumber(Parser.getRoundNumber(numeroJornada));
				round.setGames(new LinkedList<Game>());
				round.setUpdated(false);
				round = roundService.saveEasy(round);

				// A�ado partidos a las jornadas
				Element jornadaAux = elementJornada.getElementsByTag(tagTbody).get(0);
				Elements elementPartidos = jornadaAux.getElementsByTag(tagTr);
				for (Element elementPartido : elementPartidos) {
					saveGame(elementPartido, round);
				}

			}

		} catch (IOException e) {
			LOG.info("No se pudo actualizar las jornadas para la liga: "+ season.getLeague().getName());
		}

	}
	
	public Game saveGame(Element elementPartido, Round round){
		String equipoLocal = elementPartido.getElementsByClass(classLocal).text();
		String equipoVisitante = elementPartido.getElementsByClass(classVisitante).text();
		Team localTeam = teamService.findByTeamNameAndSeasonId(equipoLocal,round.getSeason().getId());
		Team awayTeam = teamService.findByTeamNameAndSeasonId(equipoVisitante,round.getSeason().getId());
		if(localTeam==null){
			Team team1 = new Team();
			team1.setName(equipoLocal);
			team1.setSeason(round.getSeason());
			team1.setUsers(new LinkedList<User>());
			team1.setAwayMatchs(new LinkedList<Game>());
			team1.setHomeMatchs(new LinkedList<Game>());
			localTeam = teamService.saveEasy(team1);
			teamStatisticsService.createAndSave(localTeam);
		}
		if(awayTeam==null){
			Team team2 = new Team();
			team2.setName(equipoVisitante);
			team2.setSeason(round.getSeason());
			team2.setUsers(new LinkedList<User>());
			team2.setAwayMatchs(new LinkedList<Game>());
			team2.setHomeMatchs(new LinkedList<Game>());
			awayTeam = teamService.saveEasy(team2);
			teamStatisticsService.createAndSave(awayTeam);
		}
		Assert.notNull(localTeam);
		Assert.notNull(awayTeam);
		Game game = new Game();
		game.setHomeTeam(localTeam);
		game.setAwayTeam(awayTeam);
		game.setRound(round);
		game.setPredictions(new LinkedList<Prediction>());
		game = gameService.saveEasy(game);
		return game;
	}

}
