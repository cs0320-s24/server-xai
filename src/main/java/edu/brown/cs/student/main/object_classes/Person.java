package edu.brown.cs.student.main.object_classes;

import java.util.Iterator;
import java.util.List;

public class Person {
  public String name;
  public String address;

  // this is to be used with a CSV Parser for a specific class (subclass of the CSVParser)
  public class PersonIterator implements Iterator<Person> {
    private int num_lines;
    private int index;
    private List<Person> list_of_persons;

    public PersonIterator(List<Person> list_of_persons) {
      this.index = 0;
      this.num_lines = list_of_persons.size();
      this.list_of_persons = list_of_persons;
    }

    @Override
    public boolean hasNext() {
      return this.index < this.num_lines;
    }

    @Override
    public Person next() {
      return this.list_of_persons.get(this.index++);
    }
  }
}
