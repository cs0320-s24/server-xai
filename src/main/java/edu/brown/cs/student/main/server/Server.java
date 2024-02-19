package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.handler.BroadbandHandler;
import edu.brown.cs.student.main.handler.LoadCSVHandler;
import edu.brown.cs.student.main.handler.BroadbandHandler;
import edu.brown.cs.student.main.handler.SearchCSVHandler;
import edu.brown.cs.student.main.handler.ViewCSVHandler;
import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.object_classes.County.State;
import edu.brown.cs.student.main.parser.CSVParser;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import spark.Spark;

public class Server {
  public static void main(String[] args) {
    // test whether the mochi adapter is working correctly
    String json_list = "[[\"NAME\",\"state\"],\n"
        + "[\"Alabama\",\"01\"],\n"
        + "[\"Alaska\",\"02\"],\n"
        + "[\"Arizona\",\"04\"],\n"
        + "[\"Arkansas\",\"05\"],\n"
        + "[\"California\",\"06\"],\n"
        + "[\"Louisiana\",\"22\"],\n"
        + "[\"Kentucky\",\"21\"],\n"
        + "[\"Colorado\",\"08\"],\n"
        + "[\"Connecticut\",\"09\"],\n"
        + "[\"Delaware\",\"10\"],\n"
        + "[\"District of Columbia\",\"11\"],\n"
        + "[\"Florida\",\"12\"],\n"
        + "[\"Georgia\",\"13\"],\n"
        + "[\"Hawaii\",\"15\"],\n"
        + "[\"Idaho\",\"16\"],\n"
        + "[\"Illinois\",\"17\"],\n"
        + "[\"Indiana\",\"18\"],\n"
        + "[\"Iowa\",\"19\"],\n"
        + "[\"Kansas\",\"20\"],\n"
        + "[\"Maine\",\"23\"],\n"
        + "[\"Maryland\",\"24\"],\n"
        + "[\"Massachusetts\",\"25\"],\n"
        + "[\"Michigan\",\"26\"],\n"
        + "[\"Minnesota\",\"27\"],\n"
        + "[\"Mississippi\",\"28\"],\n"
        + "[\"Missouri\",\"29\"],\n"
        + "[\"Montana\",\"30\"],\n"
        + "[\"Nebraska\",\"31\"],\n"
        + "[\"Nevada\",\"32\"],\n"
        + "[\"New Hampshire\",\"33\"],\n"
        + "[\"New Jersey\",\"34\"],\n"
        + "[\"New Mexico\",\"35\"],\n"
        + "[\"New York\",\"36\"],\n"
        + "[\"North Carolina\",\"37\"],\n"
        + "[\"North Dakota\",\"38\"],\n"
        + "[\"Ohio\",\"39\"],\n"
        + "[\"Oklahoma\",\"40\"],\n"
        + "[\"Oregon\",\"41\"],\n"
        + "[\"Pennsylvania\",\"42\"],\n"
        + "[\"Rhode Island\",\"44\"],\n"
        + "[\"South Carolina\",\"45\"],\n"
        + "[\"South Dakota\",\"46\"],\n"
        + "[\"Tennessee\",\"47\"],\n"
        + "[\"Texas\",\"48\"],\n"
        + "[\"Utah\",\"49\"],\n"
        + "[\"Vermont\",\"50\"],\n"
        + "[\"Virginia\",\"51\"],\n"
        + "[\"Washington\",\"53\"],\n"
        + "[\"West Virginia\",\"54\"],\n"
        + "[\"Wisconsin\",\"55\"],\n"
        + "[\"Wyoming\",\"56\"],\n"
        + "[\"Puerto Rico\",\"72\"]]";
    String json = "[[\"NAME\",\"state\"],\n"
        + "[\"Alabama\",\"01\"]]";
//    System.out.println(json_list);
//    System.exit(0);
    Moshi moshi = new Moshi.Builder().build();
    Type listType = Types.newParameterizedType(List.class, State.class);
    JsonAdapter<List<State>> adapter = moshi.adapter(listType);
    JsonAdapter<State> adapter_state = moshi.adapter(State.class);
    try {
      State state = adapter_state.fromJson(json);
      System.out.println(state);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      List<State> states = adapter.fromJson(json_list);
      System.out.println(states);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.exit(0);
    // create a new server
    int port = 3232;
    Spark.port(port);
    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });
    // create a static list of file inputs to keep track of all files loaded
    List<FileInput> file_list = new ArrayList<>();
    Spark.get("loadcsv", new LoadCSVHandler(file_list));
    Spark.get("viewcsv", new ViewCSVHandler(file_list));
    Spark.get("searchcsv", new SearchCSVHandler(file_list));
    Spark.get("broadband", new BroadbandHandler());
    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
