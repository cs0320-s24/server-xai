package edu.brown.cs.student.main.parser;

import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.from_row.CreatorFromRow;
import edu.brown.cs.student.main.input.FileInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CSVParser<T> {
  private boolean has_header;
  public BufferedReader reader;
  public String file_name;
  public FileInput file_cache;
  public int num_lines;
  public int num_cols;
  public List<String> Header;
  public List<List<String>> Content;
  public List<T> ConvertedContent;
  private int index;

  //  private List<List<String>> csv_content;
  // we stopped supporting initializing a CSVParser using file names
  @Deprecated
  public CSVParser(String file_name, boolean has_header) {
    this.reader = null;
    this.file_name = file_name;
    this.has_header = has_header;
    this.index = 0;
    this.num_lines = 0;
    this.num_cols = 0;
  }

  public CSVParser(BufferedReader reader, boolean has_header) {
    this.reader = reader;
    this.file_name = null;
    this.has_header = has_header;
    this.index = 0;
    this.num_lines = 0;
    this.num_cols = 0;
  }

  public List<List<String>> search(String value) {
    /**
     * param: value is a string value to search for param: col_id is an optional string name of the
     * column
     */
    List<List<String>> result = new ArrayList<>();
    for (int i = 0; i < this.num_lines; ++i) {
      List<String> line = this.Content.get(i);
      for (int j = 0; j < line.size(); ++j) {
        // if a line contain the provided value, add the line to the result
        // but if a line contain multiple provided value, don't add it repetitively
        if (line.get(j).equals(value)) {
          result.add(new ArrayList<String>(line));
          // if the current line is added to the result,
          // it makes no sense to keep searching on this line
          break;
        }
      }
    }
    if (result.isEmpty()) {
      System.out.println("No matches found");
    } else {
      print_result(result);
    }
    return result;
  }

  public List<List<String>> search(String value, String col_name) {
    /**
     * param: value is a string value to search for param: col_id is an optional string name of the
     * column
     */
    // DONE: support invalid col_names - if it's invalid, just act as if it's not provided
    int col_idx = -1;
    if (col_name != null && this.has_header) {
      assert (this.Header != null);
      col_idx = this.Header.indexOf(col_name);
    }
    List<List<String>> result = new ArrayList<>();
    for (int i = 0; i < this.num_lines; ++i) {
      // if the column name is provided, don't bother to search all columns for each row
      if (col_idx != -1) {
        if (this.Content.get(i).get(col_idx).equals(value)) {
          result.add(this.Content.get(i));
        }
      }
      // if the column name does not match any of the column names, we have to search the entire
      // csv file for potential matches
      else {
        List<String> line = this.Content.get(i);
        for (int j = 0; j < line.size(); ++j) {
          // if a line contain the provided value, add the line to the result
          // but if a line contain multiple provided value, don't add it repetitively
          if (line.get(j).equals(value)) {
            if (result.isEmpty() || !result.get(result.size()).equals(line)) {
              result.add(line);
            }
          }
        }
      }
    }
    if (result.isEmpty()) {
      System.out.println("No matches found");
    } else {
      print_result(result);
    }
    return result;
  }

  public List<List<String>> search(String value, int col_idx) {
    /**
     * param: value is a string value to search for param: col_idx is the index of the column where
     * we would like to search for a given value
     */
    // TODO: support negative indices for indexing into the list from end
    // TODO: support multiple indices
    assert (col_idx < this.num_cols && col_idx >= 0);
    List<List<String>> result = new ArrayList<>();
    for (int i = 0; i < this.num_lines; ++i) {
      // only probe entries of column with provided col_idx
      if (this.Content.get(i).get(col_idx).equals(value)) {
        result.add(this.Content.get(i));
      }
    }
    if (result.isEmpty()) {
      System.out.println("No matches found");
    } else {
      print_result(result);
    }
    return result;
  }

  // this is helper function used to print out the search result
  public void print_result(@NotNull List<List<String>> result) {
    // if the csv file being searched has a header, print the header for better UX
    System.out.println(result.size() + " match(es) found!");
    if (this.has_header) {
      for (int j = 0; j < this.Header.size(); ++j) {
        System.out.print(this.Header.get(j) + " | ");
      }
      System.out.println();
    }
    for (int i = 0; i < result.size(); ++i) {
      List<String> line = result.get(i);
      for (int j = 0; j < line.size(); ++j) {
        System.out.print(line.get(j) + " | ");
      }
      System.out.println();
    }
  }

  public void parse() throws IOException {
    //    assert (this.file_name != null);
    //    BufferedReader reader = new BufferedReader(new FileReader(new File(this.file_name)));
    // if the csv file contains a header, parse the header first
    // the reader is ready when parser was initialized
    if (this.has_header) {
      String[] tokens = this.reader.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      this.Header = List.of(tokens);
      this.num_cols = this.Header.size();
    }
    this.Content = new ArrayList<List<String>>();
    String line = reader.readLine();
    while (line != null) {
      String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      // removing additional spaces and double quotes around each string token
      for (int i = 0; i < tokens.length; ++i) {
        tokens[i] = tokens[i].replace("\"", "").trim();
      }
      // update the num_cols in case previous lines are incomplete
      this.num_cols = (tokens.length > this.num_cols ? tokens.length : this.num_cols);
      this.Content.add(List.of(tokens));
      this.num_lines += 1;
      line = reader.readLine();
    }
    //    if (this.has_header) {
    //      // the number of columns in data should not exceed the number of columns in the header
    //      // but for malformed files or string inputs, this assertion fails
    //      // TODO: figure out a better way to do this
    //      assert (this.num_cols <= this.Header.size());
    //    }
  }

  public void create_from_row(CreatorFromRow<T> creator) {
    this.ConvertedContent = new LinkedList<T>();
    for (List<String> line : this.Content) {
      try {
        this.ConvertedContent.add(creator.create(line));
      } catch (FactoryFailureException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void create_from_row(CreatorFromRow<T> creator, int num_lines) {
    this.ConvertedContent = new LinkedList<T>();
    num_lines = (Math.min(num_lines, this.num_lines));
    for (int i = 0; i < num_lines; ++i) {
      List<String> line = this.Content.get(i);
      try {
        this.ConvertedContent.add(creator.create(line));
      } catch (FactoryFailureException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
