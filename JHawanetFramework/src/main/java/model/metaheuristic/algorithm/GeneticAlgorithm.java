package model.metaheuristic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import epanet.core.EpanetException;
import exception.ApplicationException;
import model.metaheuristic.operator.crossover.CrossoverOperator;
import model.metaheuristic.operator.mutation.MutationOperator;
import model.metaheuristic.operator.selection.SelectionOperator;
import model.metaheuristic.problem.Problem;
import model.metaheuristic.solution.Solution;
import model.metaheuristic.utils.comparator.ObjectiveComparator;

/**
 * Class to perform algorithm genetic. <br>
 * <br>
 * 
 * This class allows you to choose between two stopping conditions to stop the
 * execution of the algorithm. The first condition establishes a maximum number
 * of evaluations. The second condition sets a maximum number of iterations
 * without improving the results of the algorithm. These configurations are
 * mutually exclusive, i.e. if you try to configure the two conditions at the
 * same time, the last one will prevail. <br>
 * <br>
 * 
 * If you do not set any stopping conditions, by default the maximum number of
 * evaluations with a value of 10000 is set.
 * 
 * <pre>
 * Base on code from https://github.com/jMetal/jMetal
 * 
 * Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. © 2019 GitHub, Inc.
 * </pre>
 */
public class GeneticAlgorithm<S extends Solution<?>> extends AbstractEvolutionaryAlgorithm<S> {
	private int maxPopulationSize;
	private Problem<S> problem;
	private List<S> population;
	private SelectionOperator<List<S>, S> selectionOperator;
	private CrossoverOperator<S> crossoverOperator;
	private MutationOperator<S> mutationOperator;
	private ObjectiveComparator<S> comparator;
	/**
	 * Max number of evaluation
	 */
	private int maxEvaluations;
	/**
	 * Count with the number of the number of evaluation performed
	 */
	private int performedEvaluationsNumber;
	/**
	 * Max number of evaluation without a improvement of the result
	 */
	private int maxNumberOfIterationWithoutImprovement;
	/**
	 * Count of number of evaluation without a improvement of the result
	 */
	private int numberOfIterationWithoutImprovement;

	private S bestSolution;

	public GeneticAlgorithm(Problem<S> problem, int populationSize, SelectionOperator<List<S>, S> selectionOperator,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
		this.problem = problem;
		this.maxPopulationSize = populationSize;
		this.selectionOperator = selectionOperator;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.maxEvaluations = 10000;
		this.comparator = new ObjectiveComparator<S>(0);
	}

	/**
	 * @return the population
	 */
	public List<S> getPopulation() {
		return population;
	}

	/**
	 * @return the maxPopulationSize
	 */
	public int getMaxPopulationSize() {
		return maxPopulationSize;
	}

	/**
	 * Get the max number of evaluation. <br>
	 * <br>
	 * 
	 * When the result returned by this method is 0 the stop condition of the
	 * algorithm don't take into account this value and only use the
	 * MaxNumberOfIterationWithoutImprovement
	 * {@link GeneticAlgorithm2#getMaxNumberOfIterationWithoutImprovement()}. If the
	 * value is other than 0 so it condition is taked into account.<br>
	 * <br>
	 * 
	 * 
	 * The default is 10000
	 * 
	 * @return the maxEvaluations
	 */
	public int getMaxEvaluations() {
		return this.maxEvaluations;
	}

	/**
	 * Set max number of evaluation.<br>
	 * <br>
	 * 
	 * This disables the maximum number of iteration without improvement condition.
	 * 
	 * The default is 10000 <br>
	 * <br>
	 * 
	 * @param maxEvaluations the maxEvaluations to set
	 */
	public void setMaxEvaluations(int maxEvaluations) {
		validateMaxStoppingConditionCounters(maxEvaluations, this.maxNumberOfIterationWithoutImprovement);
		this.maxNumberOfIterationWithoutImprovement = 0;
		this.maxEvaluations = maxEvaluations;
	}

	/**
	 * Get the max number of iteration without a improvement of the result.
	 * 
	 * When the result returned by this method is 0 the stop condition of the
	 * algorithm don't take into account this value and only use the MaxEvaluation
	 * {@link GeneticAlgorithm2#getMaxEvaluations()}. If the value is other than 0
	 * so it condition is taked into account. <br>
	 * <br>
	 * 
	 * The default is 0.
	 * 
	 * @return MaxNumberOfIterationWithoutImprovement.
	 */
	public int getMaxNumberOfIterationWithoutImprovement() {
		return maxNumberOfIterationWithoutImprovement;
	}

	/**
	 * Set the max number of iteration without a improvement of the result.
	 * 
	 * This disables the maximum number of evaluations condition.
	 * 
	 * The default is 0.
	 * 
	 * @param maxNumberOfIterationWithoutImprovement
	 */
	public void setMaxNumberOfIterationWithoutImprovement(int maxNumberOfIterationWithoutImprovement) {
		validateMaxStoppingConditionCounters(this.maxEvaluations, maxNumberOfIterationWithoutImprovement);
		this.maxEvaluations = 0;
		this.maxNumberOfIterationWithoutImprovement = maxNumberOfIterationWithoutImprovement;
	}

	/**
	 * @return the problem
	 */
	public Problem<S> getProblem() {
		return problem;
	}

	@Override
	public void run() throws EpanetException {
		List<S> offspringPopulation;
		List<S> selectionPopulation;

		population = createInitialPopulation();
		population = evaluatePopulation(population);
		initProgress();
		while (!isStoppingConditionReached()) {
			selectionPopulation = selection(population);
			offspringPopulation = reproduction(selectionPopulation);
			offspringPopulation = evaluatePopulation(offspringPopulation);
			population = replacement(population, offspringPopulation);
			updateProgress();
		}

	}

	/**
	 * @return the problem
	 */
	@Override
	protected List<S> createInitialPopulation() {
		List<S> initialPopulation = new ArrayList<S>();
		for (int i = 0; i < getMaxPopulationSize(); i++) {
			initialPopulation.add(problem.createSolution());
		}
		return initialPopulation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<S> replacement(List<S> population, List<S> offspringPopulation) {

		Collections.sort(population, comparator);
		offspringPopulation.add(population.get(0));
		offspringPopulation.add(population.get(1));
		Collections.sort(offspringPopulation, comparator);
		offspringPopulation.remove(offspringPopulation.size() - 1);
		offspringPopulation.remove(offspringPopulation.size() - 1);

		return offspringPopulation;
	}

	/**
	 * Applies the SelectionOperation
	 * 
	 * @param population
	 * @return The list of selected population.
	 */
	@Override
	protected List<S> selection(List<S> population) {
		List<S> selectionPopulation = new ArrayList<S>(population.size());
		for (int i = 0; i < getMaxPopulationSize(); i++) {
			S solution = selectionOperator.execute(population);
			selectionPopulation.add(solution);
		}
		return selectionPopulation;
	}

	/**
	 * Applies crossover operator
	 * 
	 * @param selectionPopulation
	 * @return The offspring population
	 */
	@Override
	protected List<S> reproduction(List<S> selectionPopulation) {
		int numberOfParents = crossoverOperator.getNumberOfRequiredParents();

		checkNumberOfParents(population, numberOfParents);

		List<S> offspringPopulation = new ArrayList<S>(getMaxPopulationSize());

		for (int i = 0; i < getMaxPopulationSize(); i += numberOfParents) {
			List<S> parents = new ArrayList<S>();
			for (int j = 0; j < numberOfParents; j++) {
				parents.add(population.get(i + j));
			}

			List<S> offspring = crossoverOperator.execute(parents);
			for (S solution : offspring) {
				mutationOperator.execute(solution);
				offspringPopulation.add(solution);
			}
		}
		return offspringPopulation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<S> getResult() {
		Collections.sort(getPopulation(), comparator);
		return Arrays.asList(this.getPopulation().get(0));
	}

	/** {@inheritDoc} */
	@Override
	public boolean isStoppingConditionReached() {
		boolean result = false;
		if (maxEvaluations > 0) {
			result = performedEvaluationsNumber >= getMaxEvaluations();
		}
		else if (maxNumberOfIterationWithoutImprovement > 0) {
			result = numberOfIterationWithoutImprovement >= getMaxNumberOfIterationWithoutImprovement();
		}
		return result;
	}


	/**
	 * A crossover operator is applied to a number of parents, and it assumed that
	 * the population contains a valid number of solutions. This method checks that.
	 * 
	 * @param population
	 * @param numberOfParentsForCrossover
	 * @throws ApplicationException if there is a wrong number of parent
	 */
	protected void checkNumberOfParents(List<S> population, int numberOfParentsForCrossover) {
		if ((population.size() % numberOfParentsForCrossover) != 0) {
			throw new ApplicationException("Wrong number of parents: the remainder if the " + "population size ("
					+ population.size() + ") is not divisible by " + numberOfParentsForCrossover);
		}
	}
	
	/**
	 * Check if maxEvaluations and maxNumberOfIterationWithoutImprovement are
	 * valid.<br>
	 * <br>
	 * To be valid both can't be less than 0 and both can't be 0 at the same time.
	 * @param maxEvaluations the max number of evaluation for the algorithm.
	 * @param maxNumberOfIterationWithoutImprovement the max number of iteration without improvement
	 * @throws if some of the parameters have a negative value
	 */
	private void validateMaxStoppingConditionCounters(int maxEvaluations, int maxNumberOfIterationWithoutImprovement) {
		if (maxEvaluations < 0) {
			throw new ApplicationException("Wrong MaxEvaluations can't be less than 0");
		}
		if (maxNumberOfIterationWithoutImprovement < 0) {
			throw new ApplicationException("Wrong MaxNumberOfIterationWithoutImprovement can't be less than 0");
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void initProgress() {
		if (getMaxEvaluations() > 0) {
			this.performedEvaluationsNumber = getMaxPopulationSize();
		} else if (getMaxNumberOfIterationWithoutImprovement() > 0 ) {
			this.numberOfIterationWithoutImprovement = 0;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void updateProgress() {
		if (getMaxEvaluations() > 0) {
			this.performedEvaluationsNumber += getMaxPopulationSize();
		}
		else if (getMaxNumberOfIterationWithoutImprovement() > 0) {
			// Initialize best solution if it not exist
			if (this.bestSolution == null) {
				this.bestSolution = getResult().get(0);
			}
			S solution = getResult().get(0);
			// Check if there is a new best solution
			if (comparator.compare(solution, bestSolution) < 0) {
				this.bestSolution = solution;
				this.numberOfIterationWithoutImprovement = 0;
			}
			this.numberOfIterationWithoutImprovement++;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getStatusOfExecution() {
		if (getMaxEvaluations() > 0) {
			return "Number of evaluations: " + this.performedEvaluationsNumber + " / " + this.maxEvaluations + "\n";
		}
		else if (getMaxNumberOfIterationWithoutImprovement() > 0) {
			return "Number of interation without improvement: " + this.numberOfIterationWithoutImprovement + " / "
					+ this.maxNumberOfIterationWithoutImprovement;
		}
		return "No term settings were set.";
	}

}
