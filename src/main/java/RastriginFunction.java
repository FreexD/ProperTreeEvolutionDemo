/**
 * Class implementing the Rastrigin function based evolutionary algorithm.
 * @authors Daniel Ogiela, Michał Wolny
 */

import org.apache.log4j.Logger;
import pl.edu.agh.propertree.finder.ResourceFinder;
import pl.edu.agh.propertree.generated.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RastriginFunction {

    /**
     * Parameter holding the amount of population for every step of the algorithm.
     * It is parametrized with basePopulation config variable.
     */
	private static int N;

    /**
     * Parameter holding the amount of mutated population for every step of the algorithm.
     * It is parametrized with mutatedPopulation config variable.
     */
	private static int M;

    /**
     * Parameter holding the amount of iterations of the algorithm.
     * It is parametrized with iterations config variable.
     */
    private static int iterations;

    /**
     * Parameter holding double precision number's table standard deviations per each step of the algorithm.
     */
	private static double[] standard_deviations;

    /**
     * Parameter holding the dimension of output vector optimized by the algorithm.
     * It is parametrized with dimension config variable.
     */
	private static int dim;

    /**
     * Parameter holding information if program is run in debugging mode.
     * It is parametrized with dimension config variable.
     */
    private static boolean debug;

    /**
     * Function setting configuration variables based on given postfix.
     * @param configurationPostfix postfix used for configuration search and set
     */
    private static void setConfigurationVariables(String configurationPostfix){
        N = (int) ResourceFinder.getResource(R.integers.basePopulation, configurationPostfix);
        M = (int) ResourceFinder.getResource(R.integers.mutatedPopulation, configurationPostfix);
        iterations = (int) ResourceFinder.getResource(R.integers.iterations, configurationPostfix);
        dim = (int) ResourceFinder.getResource(R.integers.dimension, configurationPostfix);
        debug = (boolean) ResourceFinder.getResource(R.booleans.debug, configurationPostfix);
    }

    /**
     * Log4j Logger.
     */
    private final static Logger logger = Logger.getLogger(RastriginFunction.class);

	public static void main(String[] args) {
        ResourceFinder.setReferencesPath("Config/reference_table");
        String configurationPostfix = (String) ResourceFinder.getResource(R.strings.initialConfiguration, "");
        setConfigurationVariables(configurationPostfix);

        standard_deviations = new double[iterations];
		for(int i=0; i<iterations; i++){
			standard_deviations[i] = i + 1.5;
		}
		
		List<Solution> solutions = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			solutions.add(new Solution(dim));
		}

        if(debug){
            for (Solution solution : solutions) {
                List<Pair> pairs = solution.getCollectionOfPairs();
                for (Pair pair : pairs) {
                    logger.debug("X: " + pair.getX() + " S: " + pair.getS());
                }
            }
        }
		
		for (int i = 0; i < iterations; i++) {
			solutions.addAll(mutation(solutions, M/2, N, i));
			Collections.sort(solutions);

            if(debug) {
                for (Solution solution : solutions) {
                    logger.debug(solution.getValueOfRastriginFunction() + " ");
			    }
            }

            for (int j = 0; j < M/2; j++) {
				solutions.remove(solutions.size() - 1);
			}

            if(debug) {
                logger.debug(solutions.size());
            }

            for (Solution solution : solutions) {
                logger.info(solution.getValueOfRastriginFunction() + " ");
			}
            logger.info("\n");
		}
	}
	
	private static List <Solution> mutation(
			List<Solution> solutions, int numberOfMutatedSolutions, int numberOfSolutions, int iteration_no) {
		
		Set<Solution> tempSolutions = new HashSet<>();
		List <Solution> mutatedSolutions = new ArrayList<>();
		Solution solution;
		Random random = new Random();
		int i = 0;
		while(i < numberOfMutatedSolutions) {
			solution = solutions.get(random.nextInt(N));
			while(tempSolutions.contains(solution)){
				solution = solutions.get(random.nextInt(N));
			}
			tempSolutions.add(solution);
			i++;
		}
		
		for (Solution sol : tempSolutions) {
			List <Pair> mutatedPairs = new ArrayList<>();
			List <Pair> pairs = sol.getCollectionOfPairs();
			for (int j = 0; j < dim; j++) {
				Pair pair = pairs.get(j);
				double newX = pair.getX() + random.nextGaussian() * pair.getS();
				while(Math.abs(newX)>5.12) {
					newX = pair.getX() + random.nextGaussian() * pair.getS();
				}
				//double newS = pair.getS() * Math.pow(Math.E, random.nextGaussian());
				double newS = standard_deviations[iteration_no];
				mutatedPairs.add(new Pair(newX, newS));
			}
			mutatedSolutions.add(new Solution(dim, mutatedPairs));
		}
		
		return mutatedSolutions;
	}
}
