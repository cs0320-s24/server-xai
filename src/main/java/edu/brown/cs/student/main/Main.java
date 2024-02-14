package edu.brown.cs.student.main;

import edu.brown.cs.student.main.from_row.StarCreatorFromRow;
import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.input.StringInput;
import edu.brown.cs.student.main.parser.CSVParser;
import java.io.IOException;

/** The Main class of our project. This is where execution begins. */
public final class Main {
  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {}

  private void run() {
    // dear student: you can remove this. you can remove anything. you're in cs32. you're free!
    //    System.out.println(
    //        "Your horoscope for this project:\n"
    //            + "Entrust in the Strategy pattern, and it shall give thee the sovereignty to "
    //            + "decide and the dexterity to change direction in the realm of thy code.");
    user_story_2();
  }

  private void user_story_1() {
    /**
     * As a user of your command-line program, I should be able to search a CSV file for rows that
     * contain a value I provide in a given column, or all columns.
     */
    String file_name = "malformed_signs.csv";
    String value_of_interest = "Gemini";
    try {
      // find the absolute path of the file and access its content in FileInoput
      FileInput file_input = new FileInput(file_name);
      // parse the csv file using CSVParser
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      parser.search(value_of_interest);
    } catch (IOException e) {
      System.err.println("Unable to fine/open the file");
    }
  }

  private void user_story_2() {
    /**
     * As a developer using your CSV library, I am able to parse CSV data from any Reader object I
     * provide—not just from a file.
     */
    String input =
        "RI,White,\" $1,058.47 \",395773.6521, $1.00 ,75%\n"
            + "RI,Black, $770.26 ,30424.80376, $0.73 ,6%\n"
            + "RI,Native American/American Indian, $471.07 ,2315.505646, $0.45 ,0%";
    String value_of_interest = "Black";
    try {
      // find the absolute path of the file and access its content in FileInoput
      StringInput str_input = new StringInput();
      str_input.get_input(input);
      str_input.create_reader();
      // parse the csv file using CSVParser
      CSVParser parser = new CSVParser(str_input.reader, false);
      parser.parse();
      parser.search(value_of_interest);
    } catch (IOException e) {
      System.err.println("Unable to find/open the file");
    }
  }

  public void user_story_3() {
    /**
     * As a developer using your CSV library, I am able to use a strategy-based interface to dictate
     * which type of object your CSV parser will convert each row into, along with how to do that
     * conversion.
     */
    String file_name = "stardata.csv";
    try {
      // find the absolute path of the file and access its content in FileInoput
      FileInput file_input = new FileInput(file_name);
      // parse the csv file using CSVParser
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      // create the creator_from_row
      StarCreatorFromRow star_creator = new StarCreatorFromRow();
      parser.create_from_row(star_creator, 10);
      for (int i = 0; i < parser.ConvertedContent.size(); ++i) {
        System.out.println(parser.ConvertedContent.get(i));
      }
    } catch (IOException e) {
      System.err.println("Unable to fine/open the file");
    }
  }
}
