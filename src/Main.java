

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * <p>
 * The Main class.
 * <p> 
 * Call this class to run the project.
 * <p>
 * Example Usage: java Main ensemble.csv 1
 * 
 */
public class Main {

	/**
	 * <p>
	 * The main method which read's in the CSV_FILE_PATH and MPRS_NUMBER as parameters from the command line, and then
	 * run's the {@link SnakeSimulator} to determine the maximum length song.
	 * 
	 * @param args The arguments provided should be CSV_FILE_PATH MPRS_NUMBER
	 * @throws ArrayIndexOutOfBoundsException NOT ENOUGH PARAMATERS PROVIDED
	 */
	public static void main(String[] args) {
		try{
			String csvFilePath = args[0];
			int MPRS = Integer.parseInt(args[1].trim());
			runSimulator(csvFilePath,MPRS);
		}catch(ArrayIndexOutOfBoundsException ex){
			System.err.println("NOT ENOUGH PARAMATERS PROVIDED. USAGE -- <CSV_FILE_PATH> <MPRS_NUMBER>");
		}
	}
	/**
	 * <p>
	 * Run's the snake simulator to find the maximum MPRS-length song 
	 * <p>
	 * @param csvFilePath The path to the CSV file containing the snakes
	 * @param MPRS the maximum repeats per snake
	 * @see SnakeSimulator
	 * 
	 */
	public static  void runSimulator(String csvFilePath, int MPRS){
		HashMap<Integer,Snake> snakes = readSnakesFromCSV(csvFilePath);

		if(!snakes.isEmpty()){
			SnakeSimulator simulator = new SnakeSimulator(snakes, MPRS);
			simulator.runSimulation();	
			simulator.printResult();
		}
	}
	/**
	 * <p>
	 * Reads the CSV file and returns a HashMap of snakes, where each Snake is mapped against the line it appeared in the file.
	 * <p>
	 * @param csvFilePath The path to the CSV file containing the snakes
	 * @return A HashMap of snakes, where each Snake is mapped against the line it appeared in the file.
	 * @throws FileNotFoundException FAILED TO LOCATE FILE
	 * @throws IOException FAILED TO READ FROM FILE
	 * @throws ArrayIndexOutOfBoundsException INVALID CSV FILE: ARRAY OUT OF BOUNDS.. ENSURE EACH LINE IN YOUR CSV FILE MATCHES THE FOLLOWING FORMAT: SNAKE_NAME, TAIL_ID, HEAD_ID
	 * 
	 */
	public static HashMap<Integer,Snake> readSnakesFromCSV(String csvFilePath){
		HashMap<Integer,Snake> snakes = new HashMap<Integer,Snake>();
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(csvFilePath));
			int lineIndex = 0;
			while ((line = br.readLine()) != null) {
				String[] snake = line.split(",");
				snakes.put(lineIndex, new Snake(snake[0], Integer.parseInt(snake[1].trim()), Integer.parseInt(snake[2].trim()), snakes));
				lineIndex++;
			}

		} catch (FileNotFoundException e) {
			System.err.println("FAILED TO LOCATE FILE: "+csvFilePath);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("FAILED TO READ FROM FILE: "+csvFilePath);
			e.printStackTrace();
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("INVALID CSV FILE: ARRAY OUT OF BOUNDS.. ENSURE EACH LINE IN YOUR CSV FILE MATCHES THE FOLLOWING FORMAT: SNAKE_NAME, TAIL_ID, HEAD_ID");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return snakes;
	}
}
