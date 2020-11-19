package controller.util;

import annotations.*;
import exception.ApplicationException;
import model.metaheuristic.experiment.Experiment;
import model.metaheuristic.operator.crossover.impl.IntegerSBXCrossover;
import model.metaheuristic.operator.crossover.impl.IntegerSinglePointCrossover;
import model.metaheuristic.operator.mutation.impl.IntegerPolynomialMutation;
import model.metaheuristic.operator.mutation.impl.IntegerRangeRandomMutation;
import model.metaheuristic.operator.mutation.impl.IntegerSimpleRandomMutation;
import model.metaheuristic.operator.selection.impl.UniformSelection;
import org.junit.jupiter.api.Test;
import registrable.SingleObjectiveRegistrable;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilsTest {

	@Test
	void testGetNameOfProblem() {
		String name = ReflectionUtils.getNameOfProblem(OnlyNewProblemAnnotation.class);
		assertEquals("Test", name);
	}

	@Test
	void testGetNameOfAlgorithm() {
		String name = ReflectionUtils.getNameOfAlgorithm(OnlyNewProblemAnnotation.class);
		assertEquals("NSGAII", name);
	}

	@Test
	void testValidateRegistrableProblem_ConstructorWithoutAnnotation_ApplicationException() {
		assertThrows(ApplicationException.class, () -> ReflectionUtils.validateRegistrableProblem(NoAnnotation.class));
	}

	@Test
	void testValidateRegistrableProblem_OnlyNewProblemAnnotation_NotException() {
		assertDoesNotThrow(() -> ReflectionUtils.validateRegistrableProblem(OnlyNewProblemAnnotation.class));

	}

	@Test
	void testValidateRegistrableProblem_ParameterInCorrectOrder_NotException() {
		assertDoesNotThrow(() -> ReflectionUtils.validateRegistrableProblem(WithParameterInCorrectOrder.class));
	}

	@Test
	void testValidateRegistrableProblem_ParametersInWrongOrder_ApplicationException() {
		assertThrows(ApplicationException.class,
				() -> ReflectionUtils.validateRegistrableProblem(WithParameterInWrongOrder.class));
	}

	@Test
	void testValidateRegistrableProblem_IncorrectNumberOfParameterInConstructor_ApplicationException() {
		assertThrows(ApplicationException.class,
				() -> ReflectionUtils.validateRegistrableProblem(IncorrectNumberOfParameterInConstructor.class));
	}

	@Test
	void testValidateRegistrableProblem_IncorrectNumberOfParameterInAnnotation_ApplicationException() {
		assertThrows(ApplicationException.class,
				() -> ReflectionUtils.validateRegistrableProblem(IncorrectNumberOfParameterInAnnotation.class));
	}

	@Test
	void testValidateRegistrableProblem_TwoConstructor_ApplicationException() {
		assertThrows(ApplicationException.class,
				() -> ReflectionUtils.validateRegistrableProblem(WithTwoConstructor.class));
	}

	@Test
	void testValidateRegistrableProblem_SameGroupInNumberToggleInputConsecutive_NotException() {
		assertDoesNotThrow(
				() -> ReflectionUtils.validateRegistrableProblem(NumberToggleInputInSameGroupConsecutively.class));
	}

	@Test
	void testValidateRegistrableProblem_SameGroupInNumberToggleInputNotConsecutive_ApplicationException() {
		assertThrows(ApplicationException.class,
				() -> ReflectionUtils.validateRegistrableProblem(NumberToggleInputInSameGroupNotConsecutively.class));
	}

	@Test
	void testValidateRegistrableProblem_WithParametersThatDoesNotCorrespondToAnnotation_ApplicationException() {
		assertThrows(ApplicationException.class, () -> ReflectionUtils
				.validateRegistrableProblem(WithParametersTypeDoesNotCorrespondToTheParametersAnnotation.class));
	}

	static abstract class TestSingleobjective implements SingleObjectiveRegistrable {

		@Override
		public Experiment<?> build(String inpPath) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}

	static class NoAnnotation extends TestSingleobjective {

		public NoAnnotation() {
		}

	}

	static class OnlyNewProblemAnnotation extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		public OnlyNewProblemAnnotation() {
		}

		@Override
		public Experiment<?> build(String inpPath) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}

	static class WithParameterInCorrectOrder extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public WithParameterInCorrectOrder(Object selectionOperator, Object crossoverOperator, Object mutationOperator,
				File gama, int minPressure, int populationSize, int numberWithoutImprovement, int maxEvaluations) {
		}

	}

	static class WithParameterInWrongOrder extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public WithParameterInWrongOrder(Object selectionOperator, Object crossoverOperator, Object mutationOperator,
				int minPressure, File gama, int populationSize, int numberWithoutImprovement, int maxEvaluations) {
		}

	}

	static class IncorrectNumberOfParameterInConstructor extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public IncorrectNumberOfParameterInConstructor(Object selectionOperator, Object crossoverOperator,
				Object mutationOperator, int minPressure, int populationSize, int numberWithoutImprovement,
				int maxEvaluations) {
		}

	}

	static class IncorrectNumberOfParameterInAnnotation extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public IncorrectNumberOfParameterInAnnotation(Object selectionOperator, Object crossoverOperator,
				Object mutationOperator, File gama, int minPressure, int populationSize, int numberWithoutImprovement,
				int maxEvaluations) {
		}

	}

	static class WithTwoConstructor extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters()
		public WithTwoConstructor() {
		}

		public WithTwoConstructor(int a) {
		}

	}

	static class NumberToggleInputInSameGroupConsecutively extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public NumberToggleInputInSameGroupConsecutively(Object selectionOperator, Object crossoverOperator,
				Object mutationOperator, File gama, int minPressure, int populationSize, int numberWithoutImprovement,
				int maxEvaluations) {
		}

	}

	static class NumberToggleInputInSameGroupNotConsecutively extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition2", displayName = "Number of iteration"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public NumberToggleInputInSameGroupNotConsecutively(Object selectionOperator, Object crossoverOperator,
				Object mutationOperator, File gama, int populationSize, int numberWithoutImprovement,
				int numberOfIteration, int maxEvaluations) {
		}

	}

	static class WithParametersTypeDoesNotCorrespondToTheParametersAnnotation extends TestSingleobjective {
		@NewProblem(displayName = "Test", algorithmName = "NSGAII")
		@Parameters(operators = {
				@OperatorInput(displayName = "Selection Operator", value = {
						@OperatorOption(displayName = "Uniform Selection", value = UniformSelection.class) }),
				@OperatorInput(displayName = "Crossover Operator", value = {
						@OperatorOption(displayName = "Integer SBX Crossover", value = IntegerSBXCrossover.class),
						@OperatorOption(displayName = "Integer Single Point Crossover", value = IntegerSinglePointCrossover.class) }), //
				@OperatorInput(displayName = "Mutation Operator", value = {
						@OperatorOption(displayName = "Integer Polynomial Mutation", value = IntegerPolynomialMutation.class),
						@OperatorOption(displayName = "Integer Range Random Mutation", value = IntegerRangeRandomMutation.class),
						@OperatorOption(displayName = "Integer Simple Random Mutation", value = IntegerSimpleRandomMutation.class) }) }, //
				files = { @FileInput(displayName = "Gama") }, //
				numbers = { @NumberInput(displayName = "Min pressure"), @NumberInput(displayName = "Population Size") }, //
				numbersToggle = {
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Number of iteration without improvement"),
						@NumberToggleInput(groupID = "Finish Condition", displayName = "Max number of evaluation") })
		public WithParametersTypeDoesNotCorrespondToTheParametersAnnotation(Object selectionOperator,
				Object crossoverOperator, File file2, File gama, int minPressure, int populationSize,
				int numberWithoutImprovement, int maxEvaluations) {
		}
	}
}