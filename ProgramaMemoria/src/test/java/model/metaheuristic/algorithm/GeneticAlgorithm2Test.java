package model.metaheuristic.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.Mock;

import model.metaheuristic.operator.crossover.CrossoverOperator;
import model.metaheuristic.operator.mutation.MutationOperator;
import model.metaheuristic.operator.selection.SelectionOperator;
import model.metaheuristic.problem.Problem;
import model.metaheuristic.solution.IntegerSolution;

class GeneticAlgorithm2Test {

	@Mock
	Problem<IntegerSolution> problem;
	@Mock
	SelectionOperator<List<IntegerSolution>, List<IntegerSolution>> selectionOperator;
	@Mock
	CrossoverOperator<IntegerSolution> crossoverOperator;
	@Mock
	MutationOperator<IntegerSolution> mutationOperator;

	@Test
	void setMaxEvaluations_LessThanZero_Exception() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertThrows(RuntimeException.class, () -> algorithm.setMaxEvaluations(-1));
	}

	@Test
	void setMaxNumberOfIterationWithoutImprovement_LessThanZero_Exception() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertThrows(RuntimeException.class, () -> algorithm.setMaxNumberOfIterationWithoutImprovement(-1));
	}

	@Test
	void setMaxEvaluationsAndSetMaxNumberOfIterationWithoutImprovement_BothZeroFirstsetMaxNumberOfIterationWithoutImprovementAftersetMaxEvaluation_Exception() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertAll(() -> algorithm.setMaxNumberOfIterationWithoutImprovement(0));
		assertThrows(RuntimeException.class, () -> algorithm.setMaxEvaluations(0));

	}
	
	@Test
	void setMaxEvaluationsAndSetMaxNumberOfIterationWithoutImprovement_BothZeroChangeSetMaxEvaluations_Exception() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertThrows(RuntimeException.class, () -> algorithm.setMaxEvaluations(0));
	}

	@Test
	void setMaxEvaluationsAndSetMaxNumberOfIterationWithoutImprovement_BothDistinctToZeroAndPositive_Nothing() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertAll(() -> algorithm.setMaxNumberOfIterationWithoutImprovement(10), () -> algorithm.setMaxEvaluations(15));
	}
	
	@Test
	void setMaxEvaluationsAndSetMaxNumberOfIterationWithoutImprovement_OneZeroAndOtherDistinctZero_Nothing() {
		GeneticAlgorithm2<IntegerSolution> algorithm = new GeneticAlgorithm2<IntegerSolution>(problem, 10,
				selectionOperator, crossoverOperator, mutationOperator);
		assertAll(() -> algorithm.setMaxNumberOfIterationWithoutImprovement(0), () -> algorithm.setMaxEvaluations(15));
	}
}