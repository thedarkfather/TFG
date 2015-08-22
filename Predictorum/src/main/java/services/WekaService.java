package services;

import org.springframework.stereotype.Service;

import weka.core.Attribute;
import weka.core.FastVector;

@Service
public class WekaService {

	private FastVector createFastVector() {

		Attribute startYearSeason = new Attribute("startYearSeason");

		Attribute team1 = new Attribute("team1");
		Attribute moreThan251 = new Attribute("moreThan251");
		Attribute homeWonMatches1 = new Attribute("homeWonMatches1");
		Attribute awayWonMatches1 = new Attribute("awayWonMatches1");
		Attribute homeLostMatches1 = new Attribute("homeLostMatches1");
		Attribute awayLostMatches1 = new Attribute("awayLostMatches1");
		Attribute homeGoals1 = new Attribute("homeGoal1");
		Attribute awayGoals1 = new Attribute("awayGoals1");
		Attribute points1 = new Attribute("points1");
		Attribute totalGames1 = new Attribute("totalGames1");

		Attribute team2 = new Attribute("team2");
		Attribute moreThan252 = new Attribute("moreThan252");
		Attribute homeWonMatches2 = new Attribute("homeWonMatches2");
		Attribute awayWonMatches2 = new Attribute("awayWonMatches2");
		Attribute homeLostMatches2 = new Attribute("homeLostMatches2");
		Attribute awayLostMatches2 = new Attribute("awayLostMatches2");
		Attribute homeGoals2 = new Attribute("homeGoal2");
		Attribute awayGoals2 = new Attribute("awayGoals2");
		Attribute points2 = new Attribute("points2");
		Attribute totalGames2 = new Attribute("totalGames2");

		FastVector winnerClass = new FastVector(3);
		winnerClass.addElement("1");
		winnerClass.addElement("2");
		winnerClass.addElement("X");
		Attribute winner = new Attribute("winner", winnerClass);

		FastVector fvWekaAttributes = new FastVector(22);
		fvWekaAttributes.addElement(startYearSeason);
		fvWekaAttributes.addElement(team1);
		fvWekaAttributes.addElement(moreThan251);
		fvWekaAttributes.addElement(homeWonMatches1);
		fvWekaAttributes.addElement(awayWonMatches1);
		fvWekaAttributes.addElement(homeLostMatches1);
		fvWekaAttributes.addElement(awayLostMatches1);
		fvWekaAttributes.addElement(homeGoals1);
		fvWekaAttributes.addElement(awayGoals1);
		fvWekaAttributes.addElement(points1);
		fvWekaAttributes.addElement(totalGames1);
		fvWekaAttributes.addElement(team2);
		fvWekaAttributes.addElement(moreThan252);
		fvWekaAttributes.addElement(homeWonMatches2);
		fvWekaAttributes.addElement(awayWonMatches2);
		fvWekaAttributes.addElement(homeLostMatches2);
		fvWekaAttributes.addElement(awayLostMatches2);
		fvWekaAttributes.addElement(homeGoals2);
		fvWekaAttributes.addElement(awayGoals2);
		fvWekaAttributes.addElement(points2);
		fvWekaAttributes.addElement(totalGames2);
		fvWekaAttributes.addElement(winner);

		return fvWekaAttributes;
	}
}
