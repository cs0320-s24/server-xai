package edu.brown.cs.student.main.object_classes;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class County {
  /**
   * This represents the web search result with the granularity of a county.
   * @param: full_name is the name of the county in the format of "CountyName County, StateName"
   * @param: state_code is the code for the state that the county is in
   * @param: county_code is the code for the county
   */
  public String full_name;
  public int state_code, county_code;
  // the default constructor such that this class can be easily inherited
  public County() {
    this.full_name = "";
    this.state_code = -1;
    this.county_code = -1;
  }
  public County(String full_name, int state_code, int county_code) {
    this.full_name = full_name;
    this.state_code = state_code;
    this.county_code = county_code;
  }
  public class State {
    public String state_name;
    public int code;
    public State(String state_name, int code) {
      this.state_name = state_name;
      this.code = code;
    }
    public static List<State> deserializeStates(String json_list) throws IOException {
      try {
        Moshi moshi = new Moshi.Builder().build();
        Type listType = Types.newParameterizedType(List.class, State.class);
        JsonAdapter<List<State>> adapter = moshi.adapter(listType);
        List<State> deserializedStates = adapter.fromJson(json_list);
        return deserializedStates;
      } catch (IOException e) {
        System.err.println("LocationHandler: Error deserializing states");
        throw e;
      } catch (JsonDataException e) {
        System.err.println("LocationHandler: Json was not in the correct format");
        throw e;
      }
    }

    public static String serializeStates(List<State> states) {
      Moshi moshi = new Moshi.Builder().build();
      Type listType = Types.newParameterizedType(List.class, State.class);
      JsonAdapter<List<State>> adapter = moshi.adapter(listType);
      return adapter.toJson(states);
    }

    public static State deserializeState(String json) throws IOException {
      try {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<State> adapter = moshi.adapter(State.class);
        return adapter.fromJson(json);
      } catch (IOException e) {
        System.err.println("LocationHandler: Error deserializing a single state");
        throw e;
      } catch (JsonDataException e) {
        System.err.println("LocationHandler: Json was not in the correct format");
        throw e;
      }
    }
    public static String serializeState(State state) {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<State> adapter = moshi.adapter(State.class);
      return adapter.toJson(state);
    }
  }

  public static List<County> deserializeCounties(String json_list) throws IOException {
    try {
      Moshi moshi = new Moshi.Builder().build();
      Type listType = Types.newParameterizedType(List.class, County.class);
      JsonAdapter<List<County>> adapter = moshi.adapter(listType);
      List<County> deserializedCounties = adapter.fromJson(json_list);
      return deserializedCounties;
    } catch (IOException e) {
      System.err.println("LocationHandler: Error deserializing counties");
      throw e;
    } catch (JsonDataException e) {
      System.err.println("LocationHandler: Json was not in the correct format");
      throw e;
    }
  }

//  public static List<Location> deserializeLocations(String json_list) throws IOException {
//    try {
//      Moshi moshi = new Moshi.Builder().build();
//      Type listType = Types.newParameterizedType(List.class, Location.class);
//      JsonAdapter<List<Location>> adapter = moshi.adapter(listType);
//      List<Location> deserializedLocations = adapter.fromJson(json_list);
//      return deserializedLocations;
//    } catch (IOException e) {
//      System.err.println("LocationHandler: Error deserializing locations");
//      throw e;
//    } catch (JsonDataException e) {
//      System.err.println("LocationHandler: Json was not in the correct format");
//      throw e;
//    }
//  }
//
//  public static String serializeLocations(List<Location> locations) {
//    Moshi moshi = new Moshi.Builder().build();
//    Type listType = Types.newParameterizedType(List.class, Location.class);
//    JsonAdapter<List<Location>> adapter = moshi.adapter(listType);
//    return adapter.toJson(locations);
//  }
//
//  public static Location deserializeLocation(String json) throws IOException {
//    try {
//      Moshi moshi = new Moshi.Builder().build();
//      JsonAdapter<Location> adapter = moshi.adapter(Location.class);
//      Location deserializedLocation = adapter.fromJson(json);
//      return deserializedLocation;
//    } catch (IOException e) {
//      System.err.println("LocationHandler: Error deserializing a single location");
//      throw e;
//    } catch (JsonDataException e) {
//      System.err.println("LocationHandler: Json was not in the correct format");
//      throw e;
//    }
//  }
//
//  public static String serializeLocation(Location location) {
//    Moshi moshi = new Moshi.Builder().build();
//    JsonAdapter<Location> adapter = moshi.adapter(Location.class);
//    return adapter.toJson(location);
//  }
}
