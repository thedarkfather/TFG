package utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class Parser {
	public static List<Date> parseFecha(String fecha, String title) {
		List<Date> fechas = new LinkedList<Date>();
		fecha = fecha.toLowerCase(); // minuscula
		fecha = fecha.replace(" ", ""); // quitamos caracteres en blanco
		title = title.toLowerCase();
		title = title.replace(" ", "");
		Integer diaInicioJornada = -1;
		Integer diaFinJornada = -1;
		Integer mesFinJornada = -1;
		Integer mesInicioJornada = -1;
		Integer anyoInicio = -1;
		Integer anyoFin = -1;
		Integer anyoActual = -1;
		// Mar. 28 y Jue. 30 Abril
		Pattern p = Pattern.compile("[^\\.]+\\.([^y]+)[^0-9]+([0-9]+)(.*)");
		Matcher matcher = p.matcher(fecha);
		while (matcher.find()) {
			diaInicioJornada = new Integer(matcher.group(1));
			diaFinJornada = new Integer(matcher.group(2));
			mesFinJornada = new Integer(mesANumero(matcher.group(3)));
			break;
		}

		// Calendario Liga BBVA 2014-2015 - MARCA.com
		Pattern pTitle = Pattern.compile("[^0-9]+([0-9]+)-([0-9]+).*");
		Matcher matcherTitle = pTitle.matcher(title);
		while (matcherTitle.find()) {
			anyoInicio = new Integer(matcherTitle.group(1));
			anyoFin = new Integer(matcherTitle.group(2));
			break;
		}

		Calendar calendarJornadaFin = Calendar.getInstance();
		if (mesFinJornada > 7) {
			anyoActual = anyoInicio;
		} else {
			anyoActual = anyoFin;
		}
		calendarJornadaFin.set(anyoActual, mesFinJornada - 1, diaFinJornada);
		Date jornadaFin = calendarJornadaFin.getTime();

		Calendar calendarJornadaInicio = Calendar.getInstance();
		// Si el día de inicio es más grande que el del final es que son
		// diferentes meses
		if (diaInicioJornada > diaFinJornada) {
			mesInicioJornada = (mesFinJornada + 11) % 12;
		} else {
			mesInicioJornada = mesFinJornada;
		}
		if (mesInicioJornada > 7) {
			anyoActual = anyoInicio;
		} else {
			anyoActual = anyoFin;
		}
		calendarJornadaInicio.set(anyoActual, mesInicioJornada - 1,
				diaInicioJornada);
		Date jornadaInicio = calendarJornadaInicio.getTime();

		fechas.add(jornadaInicio);
		fechas.add(jornadaFin);

		return fechas;
	}

	public static List<Date> parseFecha(String cadenaFechas) {
		List<Date> fechas = new LinkedList<Date>();
		cadenaFechas = cadenaFechas.replace(" ", ""); // quitamos caracteres en blanco
		Integer d1 = -1;
		Integer d2 = -1;
		Integer m1 = -1;
		Integer m2 = -1;
		Integer y1 = -1;
		Integer y2 = -1;
		// 03.02.2015 - 04.02.2015
		Pattern pTitle = Pattern
				.compile("([0-9]+)[^0-9]+([0-9]+)[^0-9]+([0-9]+)[^0-9]+([0-9]+)[^0-9]+([0-9]+)[^0-9]+([0-9]+)");
		Matcher matcherTitle = pTitle.matcher(cadenaFechas);
		while (matcherTitle.find()) {
			m1 = new Integer(matcherTitle.group(1));
			d1 = new Integer(matcherTitle.group(2));
			y1 = new Integer(matcherTitle.group(3));
			m2 = new Integer(matcherTitle.group(4));
			d2 = new Integer(matcherTitle.group(5));
			y2 = new Integer(matcherTitle.group(6));
			break;
		}
		// Puede que la jornada tenga solo una fecha y empieze el mismo día que
		// termina
		if (d1.equals(-1)) {
			pTitle = Pattern.compile("([0-9]+)[^0-9]+([0-9]+)[^0-9]+([0-9]+)");
			matcherTitle = pTitle.matcher(cadenaFechas);
			while (matcherTitle.find()) {
				m1 = new Integer(matcherTitle.group(1));
				d1 = new Integer(matcherTitle.group(2));
				y1 = new Integer(matcherTitle.group(3));
				m2 = new Integer(matcherTitle.group(1));
				d2 = new Integer(matcherTitle.group(2));
				y2 = new Integer(matcherTitle.group(3));
				break;
			}
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(y1, m1 - 1, d1);
		Date startDate = calendar.getTime();
		calendar.set(y2, m2 - 1, d2);
		Date finishDate = calendar.getTime();

		fechas.add(startDate);
		fechas.add(finishDate);

		return fechas;

	}

	public static List<Date> getStartDateAndFinishDate(String title) {
		List<Date> result = new LinkedList<Date>();

		// Calendario Liga BBVA 2014-2015 - MARCA.com
		Integer anyoInicio = -1;
		Integer anyoFin = -1;
		Pattern pTitle = Pattern.compile("[^0-9]+([0-9]+)-([0-9]+).*");
		Matcher matcherTitle = pTitle.matcher(title);
		while (matcherTitle.find()) {
			anyoInicio = new Integer(matcherTitle.group(1));
			anyoFin = new Integer(matcherTitle.group(2));
			break;
		}

		Assert.isTrue(!anyoInicio.equals(-1));
		Assert.isTrue(!anyoFin.equals(-1));
		Calendar calendarInicio = Calendar.getInstance();
		Calendar calendarFin = Calendar.getInstance();
		calendarInicio.set(anyoInicio, 6, 2);
		calendarFin.set(anyoFin, 6, 1);
		Date startDate = calendarInicio.getTime();
		Date finishDate = calendarFin.getTime();
		result.add(startDate);
		result.add(finishDate);

		return result;
	}

	public static String mesANumero(String mes) {
		String res = "";
		switch (mes) {
		case "enero":
			res = "01";
			break;
		case "febrero":
			res = "02";
			break;
		case "marzo":
			res = "03";
			break;
		case "abril":
			res = "04";
			break;
		case "mayo":
			res = "05";
			break;
		case "junio":
			res = "06";
			break;
		case "julio":
			res = "07";
			break;
		case "agosto":
			res = "08";
			break;
		case "septiembre":
			res = "09";
			break;
		case "octubre":
			res = "10";
			break;
		case "noviembre":
			res = "11";
			break;
		default:
			res = "12";
			break;
		}
		return res;
	}

	public static Integer getRoundNumber(String numeroJornada) {
		Integer result = -1;
		Pattern pTitle = Pattern.compile("[^0-9]+([0-9]+).*");
		Matcher matcherTitle = pTitle.matcher(numeroJornada);
		while (matcherTitle.find()) {
			result = new Integer(matcherTitle.group(1));
			break;
		}
		Assert.isTrue(!result.equals(-1));
		return result;
	}

}