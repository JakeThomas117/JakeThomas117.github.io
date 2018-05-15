
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class HomeWork {

	private static File hw;
	private static FileWriter fw;
	private static Scanner Tsc;
	private static Scanner Fsc;
	private static String[] Classes;
	private static String[] HomeWorks;
	private static int numClasses;
	private static String Date;
	private static String Today;

	public static void main(String[] args) {
		Classes = new String[50];
		HomeWorks = new String[50];

		hw = new File("HomeWork.txt");
		Tsc = new Scanner(System.in);
		initaializeFileScanner();
		readData();

		Today = dateFormatting(new Date().toString());
		System.out.println("\nHello and Welcome Back!");
		System.out.println("Today's date is " + Today);

		if (Today.equals(Date)) {
			printHW();
			System.exit(0);
		} else if (!Fsc.hasNextLine()) {
			createClasses();
		}
		updateHW();
		initializeWriter();
		writeData();
		printHW();
	}

	private static void initaializeFileScanner() {
		try {
			Fsc = new Scanner(hw);
		} catch (Exception FNE) {
			try {
				hw.createNewFile();
				Fsc = new Scanner(hw);
			} catch (Exception ioe) {
				System.out.println("FILE COULD NOT BE CREATED");
			}
		}
	}

	private static void readData() {
		if (Fsc.hasNextLine()) Date = Fsc.nextLine();

		for (numClasses = 0; Fsc.hasNextLine(); numClasses++) {
			Classes[numClasses] = Fsc.nextLine();
			HomeWorks[numClasses] = Fsc.nextLine();
		}
		Fsc.close();
		try {
			Fsc = new Scanner(hw);
		} catch (Exception e) {
			System.out.println("COULD NOT REINITIALIZE FILE SCANNER AFTER READING DATA");
		}
	}

	private static void initializeWriter() {
		try {
			fw = new FileWriter(hw);
		} catch (Exception e) {
			System.out.println("FILE WAS NOT CREATED");
		}
	}

	private static void writeData() {
		try {
			try {
				fw.write(Today + "\n");
				for (int i = 0; i < numClasses; i++) {
					fw.write(Classes[i] + "\n" + HomeWorks[i] + "\n");
				}
			} catch (Exception e) {
				System.out.println("FILEWRITER FAILED TO WRITE DATA");
			}
			fw.close();
		} catch (Exception closeE) {
			System.out.println("FILEWRITER CLOSE ERROR");
		}
	}

	private static void createClasses() {
		String input;
		int i = 1;

		System.out.println("Please enter your classes in the order in which you take them throughout the day.");
		System.out.println("Enter \"end\" after you have entered each of your classes.\n");

		do {
			System.out.print("Class " + i + ": ");
			input = Tsc.nextLine();
			Classes[i - 1] = input;
			numClasses = i - 1;
			i++;
		} while ((!input.equals("end")));
	}

	private static void updateHW() {
		System.out.println("\nPlease enter today's homework for each class.");
		System.out.println("Enter \"none\" if you do not have homework for a class.");

		for (int i = 0; i < numClasses; i++) {
			System.out.print(Classes[i] + " homework: ");
			HomeWorks[i] = Tsc.nextLine();
		}
	}

	private static void printHW() {
		System.out.println("\nHere is today's homework.");
		for (int i = 0; i < numClasses; i++) {
			if (!HomeWorks[i].equals("none"))
				System.out.println(String.format("%-10s", Classes[i] + ": ") + HomeWorks[i]);
		}
		System.out.println();
	}

	private static String dateFormatting(String date) {
		int pos = 0;
		for (int spc = 0; spc < 3; spc++) {
			pos = date.indexOf(" ", pos) + 1;
		}
		return date.substring(0, pos - 1);
	}
}