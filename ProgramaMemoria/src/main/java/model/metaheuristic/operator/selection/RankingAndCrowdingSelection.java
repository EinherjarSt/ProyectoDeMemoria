package model.metaheuristic.operator.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exception.ApplicationException;
import model.metaheuristic.solution.Solution;
import model.metaheuristic.utils.comparator.CrowdingDistanceComparator;
import model.metaheuristic.utils.comparator.DominanceComparator;
import model.metaheuristic.utils.solutionattribute.CrowdingDistance;
import model.metaheuristic.utils.solutionattribute.DominanceRanking;

/**
 * This class implements a selection for selecting a number of solutions from a
 * solution list. The solutions are taken by mean of its ranking and crowding
 * distance values.
 *
 * @author Antonio J. Nebro, Juan J. Durillo
 * 
 *         Extract of code from https://github.com/jMetal/jMetal
 * 
 *         Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>
 * 
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 * 
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 * 
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE. © 2019 GitHub, Inc.
 */
public class RankingAndCrowdingSelection<S extends Solution<?>> implements SelectionOperator<List<S>, List<S>> {
	private final int solutionsToSelect;
	private Comparator<S> dominanceComparator;

	/** Constructor */
	public RankingAndCrowdingSelection(int solutionsToSelect, Comparator<S> dominanceComparator) {
		this.dominanceComparator = dominanceComparator;
		this.solutionsToSelect = solutionsToSelect;
	}

	/** Constructor */
	public RankingAndCrowdingSelection(int solutionsToSelect) {
		this(solutionsToSelect, new DominanceComparator<S>());
	}

	/* Getter */
	/**
	 * Get the number of solutions to select
	 * 
	 * @return the number of solutions to select
	 */
	public int getNumberOfSolutionsToSelect() {
		return solutionsToSelect;
	}

	/** Execute() method */
	public List<S> execute(List<S> solutionList) {
		if (null == solutionList) {
			throw new ApplicationException("The solution list is null");
		} else if (solutionList.isEmpty()) {
			throw new ApplicationException("The solution list is empty");
		} else if (solutionList.size() < solutionsToSelect) {
			throw new ApplicationException("The population size (" + solutionList.size() + ") is smaller than"
					+ "the solutions to selected (" + solutionsToSelect + ")");
		}

		DominanceRanking<S> ranking = new DominanceRanking<S>(dominanceComparator);
		ranking.computeRanking(solutionList);

		return crowdingDistanceSelection(ranking);
	}

	/**
	 * Calculate the crowing distance (Density Estimator) and selects the solution
	 * with
	 * 
	 * @param ranking
	 * @return
	 */
	protected List<S> crowdingDistanceSelection(DominanceRanking<S> ranking) {
		CrowdingDistance<S> crowdingDistance = new CrowdingDistance<S>();
		List<S> population = new ArrayList<>(solutionsToSelect);
		int rankingIndex = 0;
		while (population.size() < solutionsToSelect) {
			if (subfrontFillsIntoThePopulation(ranking, rankingIndex, population)) {
				crowdingDistance.computeDensityEstimator(ranking.getSubfront(rankingIndex));
				addRankedSolutionsToPopulation(ranking, rankingIndex, population);
				rankingIndex++;
			} else {
				crowdingDistance.computeDensityEstimator(ranking.getSubfront(rankingIndex));
				addLastRankedSolutionsToPopulation(ranking, rankingIndex, population);
			}
		}

		return population;
	}

	/**
	 * Test if the subfront fill into population.
	 * 
	 * @param ranking    the population separated by rank
	 * @param rank       the rank index to subfront
	 * @param population the population
	 * @return true if especific subfront fill into the population
	 */
	protected boolean subfrontFillsIntoThePopulation(DominanceRanking<S> ranking, int rank, List<S> population) {
		return ranking.getSubfront(rank).size() < (solutionsToSelect - population.size());
	}

	/**
	 * Add all the specific subfront into the population
	 * 
	 * @param ranking    the population separated by rank
	 * @param rank       the rank index to subfront
	 * @param population the population
	 */
	protected void addRankedSolutionsToPopulation(DominanceRanking<S> ranking, int rank, List<S> population) {
		List<S> front;

		front = ranking.getSubfront(rank);

		for (int i = 0; i < front.size(); i++) {
			population.add(front.get(i));
		}
	}

	/**
	 * Add only some solution until the size of population is equals to solution to
	 * select.
	 * 
	 * @param ranking    the population separated by rank
	 * @param rank       the rank index to subfront
	 * @param population the population
	 */
	protected void addLastRankedSolutionsToPopulation(DominanceRanking<S> ranking, int rank, List<S> population) {
		List<S> currentRankedFront = ranking.getSubfront(rank);

		Collections.sort(currentRankedFront, new CrowdingDistanceComparator<S>());

		int i = 0;
		while (population.size() < solutionsToSelect) {
			population.add(currentRankedFront.get(i));
			i++;
		}
	}
}