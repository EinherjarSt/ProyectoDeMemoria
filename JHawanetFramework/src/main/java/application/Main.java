package application;

import java.io.IOException;

import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import model.metaheuristic.algorithm.Algorithm;
//import model.metaheuristic.algorithm.GeneticAlgorithm;
//import model.metaheuristic.algorithm.GeneticAlgorithm2;
//import model.metaheuristic.operator.crossover.IntegerSBXCrossover;
//import model.metaheuristic.operator.crossover.IntegerSinglePointCrossover;
//import model.metaheuristic.operator.mutation.IntegerPolynomialMutation;
//import model.metaheuristic.operator.mutation.IntegerRangeRandomMutation;
//import model.metaheuristic.operator.mutation.IntegerSimpleRandomMutation;
//import model.metaheuristic.operator.selection.TournamentSelection;
//import model.metaheuristic.operator.selection.UniformSelection;
//import model.metaheuristic.problem.CostConstructionProblem;
//import model.metaheuristic.problem.Problem;
//import model.metaheuristic.solution.IntegerSolution;
import view.problems.ProblemRegistrar;

/**
 * This class create the principal windows and get his controller. When the
 * controller is loaded the metaheuristics problem are readed for
 * {@link ProblemRegistrar}
 * 
 * @author gsanh
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
			BorderPane root = loader.load();
			MainWindowController controller = loader.getController();
			controller.setWindow(primaryStage);
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		launch(args);

//		EpanetAPI epanet;
//		try {
//			epanet = new EpanetAPI();
//			epanet.ENopen("inp/hanoi-Frankenstein.INP", "inp/hanoi.rpt", "");
//
////			TournamentSelection<IntegerSolution> selection = new TournamentSelection<IntegerSolution>(2);
////			IntegerSBXCrossover crossover = new IntegerSBXCrossover(0.02, 20);
////			IntegerPolynomialMutation mutation = new IntegerPolynomialMutation(0.02, 20);
//			
//			IntegerSinglePointCrossover crossover = new IntegerSinglePointCrossover(0.1); //0.1
//			//IntegerSimpleRandomMutation mutation = new IntegerSimpleRandomMutation(0.03); //0.03
//			IntegerRangeRandomMutation mutation = new IntegerRangeRandomMutation(0.5, 3);
//			
//			UniformSelection<IntegerSolution> selection = new UniformSelection<IntegerSolution>(1.6);//1.6
//			Problem<IntegerSolution> problem = new CostConstructionProblem(epanet, "inp/hanoiHW.Gama", 30);
//			GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10, selection,
//					crossover, mutation);
//			algorithm.setMaxNumberOfIterationWithoutImprovement(10000);
//			algorithm.setMaxEvaluations(250000);
//			algorithm.run();
//
//			epanet.ENclose();// redondear a 2 cifras
//			
//			System.out.println("Result");
//			System.out.println(algorithm.getResult());
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		} catch (EpanetException e) {
//			e.printStackTrace();
//		}

	}
}