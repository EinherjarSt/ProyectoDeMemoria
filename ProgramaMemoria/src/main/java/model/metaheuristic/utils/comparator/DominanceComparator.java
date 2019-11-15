package model.metaheuristic.utils.comparator;

import java.io.Serializable;
import java.util.Comparator;

import exception.ApplicationException;
import model.metaheuristic.solution.Solution;

/**
 * This class implements a solution comparator taking into account the violation
 * constraints
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 *         Base on code from https://github.com/jMetal/jMetal
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
@SuppressWarnings("serial")
public class DominanceComparator<S extends Solution<?>> implements Comparator<S>, Serializable {
	private ConstraintViolationComparator<S> constraintViolationComparator;

	/** Constructor */
	public DominanceComparator() {
		this(new OverallConstraintViolationComparator<S>());
	}


	/** Constructor */
	public DominanceComparator(ConstraintViolationComparator<S> constraintComparator) {
		constraintViolationComparator = constraintComparator;
	}

	/**
	 * Compares two solutions.
	 *
	 * @param solution1 Object representing the first <code>Solution</code>.
	 * @param solution2 Object representing the second <code>Solution</code>.
	 * @return -1, or 0, or 1 if solution1 dominates solution2, both are
	 *         non-dominated, or solution1 is dominated by solution2, respectively.
	 */
	@Override
	public int compare(S solution1, S solution2) {
		if (solution1 == null) {
			throw new ApplicationException("Solution1 is null");
		} else if (solution2 == null) {
			throw new ApplicationException("Solution2 is null");
		} else if (solution1.getNumberOfObjectives() != solution2.getNumberOfObjectives()) {
			throw new ApplicationException("Cannot compare because solution1 has " + solution1.getNumberOfObjectives()
					+ " objectives and solution2 has " + solution2.getNumberOfObjectives());
		}
		int result;
		result = constraintViolationComparator.compare(solution1, solution2);
		if (result == 0) {
			result = dominanceTest(solution1, solution2);
		}

		return result;
	}

	/**
	 * Compare if a solution dominate other solution based in the objectives
	 * @param solution1
	 * @param solution2
	 * @return
	 */
	private int dominanceTest(S solution1, S solution2) {
		int bestIsOne = 0;
		int bestIsTwo = 0;
		int result;
		for (int i = 0; i < solution1.getNumberOfObjectives(); i++) {
			double value1 = solution1.getObjective(i);
			double value2 = solution2.getObjective(i);
			if (value1 != value2) {
				if (value1 < value2) {
					bestIsOne = 1;
				}
				if (value2 < value1) {
					bestIsTwo = 1;
				}
			}
		}
		if (bestIsOne > bestIsTwo) {
			result = -1;
		} else if (bestIsTwo > bestIsOne) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}
}