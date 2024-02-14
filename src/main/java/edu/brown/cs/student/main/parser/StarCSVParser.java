package edu.brown.cs.student.main.parser;

import edu.brown.cs.student.main.object_classes.Star;
import edu.brown.cs.student.main.object_classes.Star.StarIterator;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.function.Consumer;

public class StarCSVParser extends CSVParser<Star> implements Iterable<Star> {
  public StarCSVParser(BufferedReader file_reader, boolean has_header) {
    super(file_reader, has_header);
  }

  @Override
  public Iterator<Star> iterator() {
    return new StarIterator(super.ConvertedContent);
  }

  // we want to implement the iterator feature of the CSVParser s.t. we can use for_each loops on it
  // the following methods are for this purpose
  public void forEach(Consumer<? super Star> action) {
    assert (this.ConvertedContent != null);
    for (Star t : this.ConvertedContent) {
      action.accept(t);
    }
  }
}
