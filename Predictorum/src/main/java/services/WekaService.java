package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import domain.Comment;
import domain.Diary;
import domain.Game;
import domain.Prediction;
import domain.TeamStatistics;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@Service
public class WekaService {

	@Autowired
	private DiaryService diaryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PredictionService predictionService;
	
	private static final Logger LOG = Logger.getLogger(UpdateService.class);
	
	//@Scheduled(cron = "*/120 * * * * ?")
	public void calculaPrediccion() throws Exception{
		//Declaro el vector con sus atributos
		FastVector fvWekaAttributes = createFastVector();
		//creo la base de entrenamiento
        Instances isTrainingSet = createSet("rel1",fvWekaAttributes);
        //la entreno
        Classifier cModel = (Classifier)new NaiveBayes();   
		 //pruebo
        //primero cojo los partidos de los que tengo que hacer la predicción
        Collection<Game> games = gameService.findCurrentGames();
        for(Game game : games){
        	//compruebo que el game no tenga ninguna predicción. Supuestamente no seria necesario, pero por si acaso
        	Prediction aux = predictionService.findSystemPredictionByGameId(game.getId());
        	if(aux==null){
        		// La entreno para el resultado simple
                isTrainingSet.setClassIndex(fvWekaAttributes.size()-4);   
                cModel.buildClassifier(isTrainingSet);
                
        		//Prediccion simple y doble
        		double[] fDistributionWinner = createFuncionDistribucion(fvWekaAttributes,game, isTrainingSet, cModel);
            	String simple;  
            	double pSimple;
                if(fDistributionWinner[0] >= fDistributionWinner[1] && fDistributionWinner[0] >= fDistributionWinner[2]){
                	simple = "1";
                	pSimple = fDistributionWinner[0];
                }else if(fDistributionWinner[1] >= fDistributionWinner[0] && fDistributionWinner[1] >= fDistributionWinner[2]){
                	simple = "2";
                	pSimple = fDistributionWinner[1];
                }else{
                	simple = "X";
                	pSimple = fDistributionWinner[2];
                }
                String doble;
                double pDoble;
                if(fDistributionWinner[0] <= fDistributionWinner[1] && fDistributionWinner[0] <= fDistributionWinner[2]){
                	doble = "X2";
                	pDoble = fDistributionWinner[1]+fDistributionWinner[2];
                }else if(fDistributionWinner[1] <= fDistributionWinner[0] && fDistributionWinner[1] <= fDistributionWinner[2]){
                	doble = "1X";
                	pDoble = fDistributionWinner[0]+fDistributionWinner[1];
                }else{
                	doble = "12";
                	pDoble = fDistributionWinner[0]+fDistributionWinner[2];
                }   
                
                //More than 25;
                isTrainingSet.setClassIndex(fvWekaAttributes.size()-1);   
                cModel.buildClassifier(isTrainingSet);
                double[] fDistributionM25 = createFuncionDistribucion(fvWekaAttributes,game, isTrainingSet, cModel);
                Boolean moreThan25;
                double pMoreThan25;
                if(fDistributionM25[0] >= fDistributionM25[1]){
                	moreThan25 = true;
                	pMoreThan25 = fDistributionM25[0];
                }else{
                	moreThan25 = false;
                	pMoreThan25 = fDistributionM25[1];
                }
                
                //HomeGals                 
                isTrainingSet.setClassIndex(fvWekaAttributes.size()-3);   
                cModel.buildClassifier(isTrainingSet);                
                double[] fDistributionHG = createFuncionDistribucion(fvWekaAttributes,game, isTrainingSet, cModel);
                Integer homeGoals=0;
                double pHomeGoals=0;
                for(int i=0; i<fDistributionHG.length;i++){
                	if(pHomeGoals<fDistributionHG[i]){
                		homeGoals =i ;
                		pHomeGoals=fDistributionHG[i];
                	}
                }
                //AwayGoals
                isTrainingSet.setClassIndex(fvWekaAttributes.size()-2);   
                cModel.buildClassifier(isTrainingSet);
                double[] fDistributionAG = createFuncionDistribucion(fvWekaAttributes,game, isTrainingSet, cModel);
                Integer awayGoals=0;
                double pAwayGoals=0;
                for(int i=0; i<fDistributionAG.length;i++){
                	if(pAwayGoals<fDistributionAG[i]){
                		awayGoals =i ;
                		pAwayGoals=fDistributionAG[i];
                	}
                }
                
            	Prediction prediction = new Prediction();
            	prediction.setComments(new LinkedList<Comment>());
            	prediction.setGame(game);
            	prediction.setSimpleResult(simple);
            	prediction.setpSimpleResult(pSimple);
            	prediction.setDoubleResult(doble);
            	prediction.setpDoubleResult(pDoble);
            	prediction.setMoreThan25(moreThan25);
            	prediction.setpMoreThan25(pMoreThan25);
            	prediction.setHomeGoals(homeGoals);
            	prediction.setpHomeGoals(pHomeGoals);
            	prediction.setAwayGoals(awayGoals);
            	prediction.setpAwayGoals(pAwayGoals);      	
            	predictionService.save(prediction);
        	}
        	
        }
	}
	
	private double[] createFuncionDistribucion(FastVector fvWekaAttributes, Game game, Instances conjuntoEntrenado, Classifier cModel) throws Exception {
		Instance instance = new Instance(fvWekaAttributes.size());
		TeamStatistics team1 = game.getHomeTeam().getTeamStatistics();
		TeamStatistics team2 = game.getAwayTeam().getTeamStatistics();
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(game.getRound().getSeason().getStartDate());
		int year = calendar.get(Calendar.YEAR);
		instance.setValue((Attribute)fvWekaAttributes.elementAt(0), year);//fvWekaAttributes.addElement(startYearSeason);
		 
		instance.setValue((Attribute)fvWekaAttributes.elementAt(1), game.getHomeTeam().getName());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(2), team1.getHomeWonMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(3), team1.getAwayWonMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(4), team1.getHomeLostMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(5), team1.getAwayLostMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(6), team1.getHomeGoals());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(7), team1.getAwayGoals());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(8), team1.getMoreThan25());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(9), team1.getPoints());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(10), team1.getTotalGames());
		 
		instance.setValue((Attribute)fvWekaAttributes.elementAt(11), game.getAwayTeam().getName());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(12), team2.getHomeWonMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(13), team2.getAwayWonMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(14), team2.getHomeLostMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(15), team2.getAwayLostMatches());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(16), team2.getHomeGoals());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(17), team2.getAwayGoals());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(18), team2.getMoreThan25());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(19), team2.getPoints());
		instance.setValue((Attribute)fvWekaAttributes.elementAt(20), team2.getTotalGames());
		
		instance.setDataset(conjuntoEntrenado);
        double[] fDistribution = cModel.distributionForInstance(instance);
        return fDistribution;
	}

	private FastVector createFastVector() {
		
		Collection<String> teamNames = teamService.teamsName();
		FastVector teams = new FastVector(teamNames.size());
		for(String teamName : teamNames){
			teams.addElement(teamName);
		}
		Attribute startYearSeason = new Attribute("startYearSeason");
		Attribute team1 = new Attribute("team1", teams);
		Attribute homeWonMatches1 = new Attribute("homeWonMatches1");
		Attribute awayWonMatches1 = new Attribute("awayWonMatches1");
		Attribute homeLostMatches1 = new Attribute("homeLostMatches1");
		Attribute awayLostMatches1 = new Attribute("awayLostMatches1");
		Attribute totalHomeGoals1 = new Attribute("totalHomeGoals1");
		Attribute totalAwayGoals1 = new Attribute("totalAwayGoals1");
		Attribute totalMoreThan251 = new Attribute("totalMoreThan251");
		Attribute points1 = new Attribute("points1");
		Attribute totalGames1 = new Attribute("totalGames1");

		Attribute team2 = new Attribute("team2", teams);
		Attribute homeWonMatches2 = new Attribute("homeWonMatches2");
		Attribute awayWonMatches2 = new Attribute("awayWonMatches2");
		Attribute homeLostMatches2 = new Attribute("homeLostMatches2");
		Attribute awayLostMatches2 = new Attribute("awayLostMatches2");
		Attribute totalHomeGoals2 = new Attribute("totalHomeGoals2");
		Attribute totalAwayGoals2 = new Attribute("totalAwayGoals2");
		Attribute totalMoreThan252 = new Attribute("totalMoreThan252");
		Attribute points2 = new Attribute("points2");
		Attribute totalGames2 = new Attribute("totalGames2");

		//Atributos a predecir
		FastVector winnerClass = new FastVector(3);
		winnerClass.addElement("1");
		winnerClass.addElement("2");
		winnerClass.addElement("X");
		Attribute winner = new Attribute("winner", winnerClass);
		FastVector goalsClass = new FastVector(6);
		goalsClass.addElement("0");
		goalsClass.addElement("1");
		goalsClass.addElement("2");
		goalsClass.addElement("3");
		goalsClass.addElement("4");
		goalsClass.addElement("5");
		Attribute homeGoals = new Attribute("homeGoals",goalsClass);
		Attribute awayGoals = new Attribute("awayGoals",goalsClass);		
		FastVector moreThan25Class = new FastVector(2);
		moreThan25Class.addElement("true");
		moreThan25Class.addElement("false");
		Attribute moreThan25 = new Attribute("moreThan25", moreThan25Class);

		FastVector fvWekaAttributes = new FastVector(22);
		//1 atributos fecha
		fvWekaAttributes.addElement(startYearSeason);
		//10 atributos team1
		fvWekaAttributes.addElement(team1);
		fvWekaAttributes.addElement(homeWonMatches1);
		fvWekaAttributes.addElement(awayWonMatches1);
		fvWekaAttributes.addElement(homeLostMatches1);
		fvWekaAttributes.addElement(awayLostMatches1);
		fvWekaAttributes.addElement(totalHomeGoals1);
		fvWekaAttributes.addElement(totalAwayGoals1);
		fvWekaAttributes.addElement(totalMoreThan251);
		fvWekaAttributes.addElement(points1);
		fvWekaAttributes.addElement(totalGames1);		
		//10 atributos team2
		fvWekaAttributes.addElement(team2);
		fvWekaAttributes.addElement(homeWonMatches2);
		fvWekaAttributes.addElement(awayWonMatches2);
		fvWekaAttributes.addElement(homeLostMatches2);
		fvWekaAttributes.addElement(awayLostMatches2);
		fvWekaAttributes.addElement(totalHomeGoals2);
		fvWekaAttributes.addElement(totalAwayGoals2);
		fvWekaAttributes.addElement(totalMoreThan252);
		fvWekaAttributes.addElement(points2);
		fvWekaAttributes.addElement(totalGames2);
		//4 atributos para predecir
		fvWekaAttributes.addElement(winner);
		fvWekaAttributes.addElement(homeGoals);
		fvWekaAttributes.addElement(awayGoals);
		fvWekaAttributes.addElement(moreThan25);

		return fvWekaAttributes;
	}
	
	private Instances createSet(String relName,FastVector fvWekaAttributes){
		 Collection<Diary> diaries = diaryService.findAll();
		 int diariesSize = diaries.size();
		 int numberOfAttributes = fvWekaAttributes.size();
		 Instances set = new Instances(relName, fvWekaAttributes, diariesSize);  	                 
         // Create the instance
		 for(Diary diary : diaries){
			 try{
				 Instance instance = new Instance(numberOfAttributes);
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(0), diary.getStartYearSeason());//fvWekaAttributes.addElement(startYearSeason);
				 
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(1), diary.getTeam1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(2), diary.getHomeWonMatches1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(3), diary.getAwayWonMatches1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(4), diary.getHomeLostMatches1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(5), diary.getAwayLostMatches1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(6), diary.getTotalHomeGoals1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(7), diary.getTotalAwayGoals1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(8), diary.getTotalMoreThan251());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(9), diary.getPoints1());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(10), diary.getTotalGames1());
				 
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(11), diary.getTeam2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(12), diary.getHomeWonMatches2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(13), diary.getAwayWonMatches2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(14), diary.getHomeLostMatches2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(15), diary.getAwayLostMatches2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(16), diary.getTotalHomeGoals2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(17), diary.getTotalAwayGoals2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(18), diary.getTotalMoreThan252());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(19), diary.getPoints2());
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(20), diary.getTotalGames2());
				 
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(21), diary.getWinner());
				 Integer homeGoals = diary.getHomeGoals();
				 if(homeGoals.compareTo(5)>0){
					 homeGoals = 5;
				 }
				 Integer awayGoals = diary.getAwayGoals();
				 if(awayGoals.compareTo(5)>0){
					 awayGoals = 5;
				 }
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(22), homeGoals);
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(23), awayGoals);
				 instance.setValue((Attribute)fvWekaAttributes.elementAt(24), diary.getMoreThan25());

				 set.add(instance);
			 }catch(Exception e){
				 LOG.info("No se ha podido insertar la entrada para realizar la predicción");
			 }
			 
		 }
		 return set;
  	 }

}
