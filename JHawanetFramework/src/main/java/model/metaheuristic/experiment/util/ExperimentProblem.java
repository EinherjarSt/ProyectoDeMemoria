package model.metaheuristic.experiment.util;

import model.metaheuristic.problem.Problem;
import model.metaheuristic.solution.Solution;

/**
 * Class used to add a tag field to a problem.
 *
 * @author Antonio J. Nebro <ajnebro@uma.es>
 * 
 *         <pre>
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
 * SOFTWARE. � 2019 GitHub, Inc.
 *         </pre>
 */
public class ExperimentProblem<S extends Solution<?>> {
	private Problem<S> problem;
	private String tag;
	private String referenceFront;

	public ExperimentProblem(Problem<S> problem, String tag) {
		this.problem = problem;
		this.tag = tag;
		this.referenceFront = this.problem.getName() + ".pf";

	}

	public ExperimentProblem(Problem<S> problem) {
		this(problem, problem.getName());
	}

	public ExperimentProblem<S> changeReferenceFrontTo(String referenceFront) {
		this.referenceFront = referenceFront;
		return this;
	}

	public Problem<S> getProblem() {
		return problem;
	}

	public String getTag() {
		return tag;
	}

	public String getReferenceFront() {
		return referenceFront;
	}
}