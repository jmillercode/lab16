package lab16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ListOfCountriesApp {

	private static Path filePath = Paths.get("countries.txt");
	
	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(System.in);
		int userInput;
		String userContinue = "y";
		boolean loop;
		String addLoopInput;
		boolean addLoop = true;
		
		System.out.println("Welcome to Jake's Country Directory\n");
		
		while(userContinue.equals("y")) {
			loop = true;
			while(loop) {
				System.out.println("Please enter a actions list number.");
				System.out.println("1. List the countries on the list right now.\n2. Add a country to the list.\n3. Exit.");
				userInput = scnr.nextInt();
				
				if (userInput == 1) {
					List<Country> countries = readFile();
					for (Country c : countries) {
						System.out.println(c.getName() + " has " + c.getPopulation() + " people.");
					}
					loop = false;
				}
				else if (userInput == 2) {
					while(addLoop) {
						scnr.nextLine();
						System.out.print("Enter a Country name to add: ");
						String name = scnr.nextLine();
						System.out.print("Enter their population: ");
						int population = scnr.nextInt();
						Country newCountry = new Country(name, population);
						appendToFile(newCountry);
						loop = false;
						System.out.println("Would you like to add another country?(y/n)");
						addLoopInput = scnr.next();
						if (addLoopInput.equals("y")) {
							addLoop = true;
						}
						else {
							addLoop = false;
						}
					}
				}
				else if (userInput == 3) {
					userContinue = "n";
					loop = false;
				}
				else {
					System.out.println("Im sorry, the number you entered was not an option. Please try again.");
				}
			}
			if (userContinue.equals("y")) {
				System.out.println("Would you like to continue?(y/n)");
				userContinue = scnr.next();
				if (userContinue.equals("n")) {
					System.out.println("Goodbye");
				}
			}
			else {
				System.out.println("Goodbye");
			}
		}
			
	}
	
	private static void appendToFile(Country country) throws IOException {
		//creates file if file is unable to be found
		if ( Files.notExists(filePath) ) {
			Files.createFile(filePath);
		}
		
		//creating a line for the file
		String line = country.getName() + "\t" + country.getPopulation();
		
		List<String> linesToAdd = Arrays.asList(line);
		// Write those lines to the end of the file
		Files.write(filePath, linesToAdd, StandardOpenOption.APPEND);
	}
	
	private static List<Country> readFile() {
		// ** Example of reading a file into a list
		try {
			List<String> lines = Files.readAllLines(filePath);
			List<Country> countries = new ArrayList<Country>();
			for (String line : lines) {
				String[] parts = line.split("\t");
				Country c = new Country();
				c.setName(parts[0]);
				c.setPopulation(Integer.parseInt(parts[1]));
				countries.add(c);
			}
			return countries;

		} catch (NoSuchFileException ex) {
			return new ArrayList<Country>();
		} catch (IOException ex) {
			ex.printStackTrace();
			return new ArrayList<Country>();
		}
	}
}
