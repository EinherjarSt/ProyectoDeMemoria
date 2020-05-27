package controller.utils;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import model.metaheuristic.experiment.Experiment;
import model.metaheuristic.experiment.util.ExperimentAlgorithm;
import model.metaheuristic.solution.Solution;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is used to perform the algorithm execution in other thread
 * updating the JavaFXApplicationThread.
 *
 */
public class SingleObjectiveExperimentTask extends Task<List<SingleObjectiveExperimentTask.Result>> {

	//**********************************************************************************
	//Additional properties to task
	//**********************************************************************************

	/**
	 * Used to send a partial result of the execution in a thread-safe
	 * manner from the subclass to the FX application thread. AtomicReference is
	 * used so as to coalesce updates such that we don't flood the event queue.
	 */
	@NotNull private final AtomicReference<Result> customValueUpdate = new AtomicReference<>();
	@NotNull private final ObjectProperty<Result> customValue = new SimpleObjectProperty<>(this, "customValue");
	//**********************************************************************************

	private final Experiment<?> experiment;


	public SingleObjectiveExperimentTask(Experiment<?> experiment) {
		this.experiment = experiment;
	}

//	/**
//	 * Execute the algorithm
//	 */
//	@Override
//	protected SingleObjectiveExperimentTask.Result call() throws Exception {
//		int numberOfIteration = 0;
//		experiment.runSingleStep(); // run the first step
//		updateMessage(this.experiment.getStatusOfExecution());
//		while (!this.experiment.isStoppingConditionReached()) {
//			// Check if the task is cancelled
//			if (this.isCancelled()) {
//				break;
//			}
//			experiment.runSingleStep();
//			updateMessage(this.experiment.getStatusOfExecution());
//
//			updateValue(new Result(Collections.unmodifiableList(experiment.getResult()), numberOfIteration));
//			numberOfIteration++;
//		}
//		experiment.close();
//
//		return new Result(Collections.unmodifiableList(experiment.getResult()), numberOfIteration);
//	}

	@Override
	protected List<Result> call() throws Exception {
		// count of algorithm executed
		int progress = 0;
		updateProgress(progress, experiment.getAlgorithmList().size());
		// A list to save the final result of the algorithm.
		List<Result> finalResultList = new ArrayList<>(experiment.getAlgorithmList().size());

		for (ExperimentAlgorithm<?> algorithm : experiment.getAlgorithmList()) {

			// break the for loop if the task is cancelled
			if (this.isCancelled()) {
				break;
			}
			int numberOfIterations = 0;
			while (algorithm.algorithmHasANextStep()) {

				// run only a iteration of the current algorithm
				algorithm.runASingleStepOfAlgorithm();
				// update the message of progress of the current algorithm
				updateMessage("Progress of current algorithm:\n" + algorithm.getAlgorithm().getStatusOfExecution());

				// break the while loop if the task is cancelled
				if (this.isCancelled()) {
					break;
				}
				updateCustomValue(new Result(algorithm.getResult().get(0), numberOfIterations));// <-- notify result here to algorithm level
				numberOfIterations++;
			}
			if (!this.isCancelled()) {
				finalResultList.add(new Result(algorithm.getResult().get(0), numberOfIterations));
				progress++;
				updateProgress(progress, experiment.getAlgorithmList().size());
			}
		}

		// close resources (as epanet library)
		experiment.getProblem().closeResources();
		// if task is cancelled return null
		if (this.isCancelled()) {
			return null;
		}

		return finalResultList;
	}

	/**
	 * This inner class is used to return a pair of values.
	 *
	 */
	public static class Result{
		private final Solution<?> solution;
		private final int numberOfIterations;
		
		/**
		 * 
		 * @param solutionList the solution list
		 * @param numberOfIterations the number of iterations
		 * @throws NullPointerException if solution list is null
		 */
		public Result(Solution<?> solutionList, int numberOfIterations) {
			Objects.requireNonNull(solutionList);
			this.solution = solutionList;
			this.numberOfIterations = numberOfIterations;
		}

		/**
		 * @return the solution
		 */
		public Solution<?> getSolution() {
			return solution;
		}

		/**
		 * @return the numberOfIterations
		 */
		public int getNumberOfIterations() {
			return numberOfIterations;
		}
	}

	//**********************************************************************************
	// Additional method to new properties of task
	//**********************************************************************************

	/**
	 * Get the customValue. This value corresponds a partial result of the final list.
	 * @return customValue
	 */
	public final Result getCustomValue() {
		checkThread();
		return customValue.get();
	}

	/**
	 * Get the customValueProperty. This value corresponds a partial result of the final list.
	 * <p>
	 * @return customValue property
	 */
	public final @NotNull ReadOnlyObjectProperty<Result>  customValueProperty() {
		checkThread();
		return customValue;
	}

	/**
	 *
	 * This method return a partial result that can be a instance of the final result list.
	 *
	 * Updates the <code>customValue</code> property. Calls to updateCustomValueProperty are coalesced
	 * and run later on the FX application thread, so calls to updateCustomValueProperty, even
	 * from the FX Application thread, may not necessarily result in immediate
	 * updates to this property, and intermediate message values may be coalesced to
	 * save on event notifications.
	 * <p>
	 * <em>This method is safe to be called from any thread.</em>
	 * </p>
	 *
	 * @param result is a parcial result of the total solution
	 */
	protected void updateCustomValue(Result result) {
		if (Platform.isFxApplicationThread()) {
			this.customValue.set(result);
		} else {
			// As with the workDone, it might be that the background thread
			// will update this message quite frequently, and we need
			// to throttle the updates so as not to completely clobber
			// the event dispatching system.
			if (customValueUpdate.getAndSet(result) == null) {
				Platform.runLater(() -> {
					this.customValue.setValue(customValueUpdate.getAndSet(null));
				});
			}
		}
	}

	private void checkThread() {
		if (!Platform.isFxApplicationThread()) {
			throw new IllegalStateException("Task must only be used from the FX Application Thread");
		}
	}
}