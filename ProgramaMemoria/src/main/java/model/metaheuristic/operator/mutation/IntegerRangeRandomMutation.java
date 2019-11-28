package model.metaheuristic.operator.mutation;

import annotations.DefaultConstructor;
import model.metaheuristic.solution.IntegerSolution;
import model.metaheuristic.utils.random.BoundedRandomGenerator;
import model.metaheuristic.utils.random.RandomGenerator;
import model.metaheuristic.utils.random.JavaRandom;

public class IntegerRangeRandomMutation implements MutationOperator<IntegerSolution> {

	private double mutationProbability;
	private RandomGenerator<Double> randomGenerator;
	private BoundedRandomGenerator<Integer> pointRandomGenerator;
	private int range;

	/** Constructor */
	@DefaultConstructor({"Probability","Range"})
	public IntegerRangeRandomMutation(double probability, int range) {
		this(probability, range, () -> JavaRandom.getInstance().nextDouble(),
				(a, b) -> JavaRandom.getInstance().nextInt(a, b));
	}

	/** Constructor */
	public IntegerRangeRandomMutation(double probability, int range, RandomGenerator<Double> randomGenerator,
			BoundedRandomGenerator<Integer> pointRandomGenerator) {
		if (probability < 0) {
			throw new RuntimeException("Mutation probability is negative: " + mutationProbability);
		}
		if (range < 0) {
			throw new RuntimeException("Range is negative: " + mutationProbability);
		}

		this.mutationProbability = probability;
		this.range = range;
		this.randomGenerator = randomGenerator;
		this.pointRandomGenerator = pointRandomGenerator;
	}

	/* Getters */
	public double getMutationProbability() {
		return mutationProbability;
	}

	/* Setters */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	@Override
	public IntegerSolution execute(IntegerSolution solution) {
		if (null == solution) {
			throw new RuntimeException("Null parameter");
		}

		doMutation(mutationProbability, range, solution);

		return solution;
	}

	/** Implements the mutation operation */
	private void doMutation(double probability, int range, IntegerSolution solution) {
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			if (randomGenerator.getRandomValue() <= probability) {
				Integer value = solution.getVariable(i);
				int minValue = solution.getLowerBound(i);
				int maxValue = solution.getUpperBound(i);

				int lowerBound = value - range;
				int upperBound = value + range;
				if (lowerBound < minValue) {
					lowerBound = minValue;
				}
				if (upperBound > maxValue) {
					upperBound = maxValue;
				}
				Integer newValue;
				do {
					newValue = pointRandomGenerator.getRandomValue(lowerBound, upperBound + 1);
				} while (newValue == value);
				solution.setVariable(i, newValue);
			}
		}

	}
}