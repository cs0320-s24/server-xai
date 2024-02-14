package edu.brown.cs.student.main.object_classes;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Star {
  public int id;
  public String name;
  public List<Float> coordinate;

  public Star(int id, String name, float x, float y, float z) {
    this.id = id;
    this.name = name;
    this.coordinate = List.of(x, y, z);
  }

  public String toString() {
    return "ID:" + id + " Name:" + name + " Coordinate:" + coordinate;
  }

  // star iterator
  // this is to be used with CSVParsers with specific classes like StarCSVParser
  // to implement the Iterator interface, one has to override the hasNext function and next function
  // similarly in Python, to customize an iterable, one has to implement __iter__ and __next__,
  // where
  // __next__ combines hasNext() and next()
  public static class StarIterator implements Iterator<Star> {
    private List<Star> list_of_stars;
    private int num_lines;
    private int index;

    public StarIterator(List<Star> list_of_stars) {
      this.index = 0;
      this.num_lines = list_of_stars.size();
      this.list_of_stars = list_of_stars;
    }

    @Override
    public boolean hasNext() {
      return this.index < this.num_lines;
    }

    @Override
    public Star next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return this.list_of_stars.get(index++);
    }
  }
}
