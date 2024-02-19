package edu.brown.cs.student.main.handler;

import edu.brown.cs.student.main.object_classes.County;
import edu.brown.cs.student.main.object_classes.County.State;
import edu.brown.cs.student.main.object_classes.CountyBroadbandAccess;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;

public class BroadbandHandler implements spark.Route{
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String state_name = request.queryParams("state").replace("%20", " ");
    String county_name = request.queryParams("county").replace("%20", " ");
    String full_county_name = county_name + " County, " + state_name;
    System.out.println(full_county_name);
    Map<String, Object> responseMap = new HashMap<>();
    try {
      // fetch the state codes to convert state names to an ordinal number
      String StateNameCodeJson = this.sendRequest("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*");
      List<State> state_list = State.deserializeStates(StateNameCodeJson);
      // find the state code for the given state name
      int state_code = -1;
      for (State s: state_list) {
        if (s.state_name.equals(state_name)) {
          state_code = s.code;
          break;
        }
      }
      if (state_code == -1) {
        response.status(400);
        responseMap.put("error", "Invalid state name");
        return responseMap;
      }
      System.out.println(state_code);
      // fetch the county codes to convert county names to an ordinal number
      String CountyNameCodeJson = this.sendRequest("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=county:*&in=state:" + state_code);
      List<County> county_list = County.deserializeCounties(CountyNameCodeJson);
      // find the county code for the given county name
      int county_code = -1;
      for (County c: county_list) {
        if (c.full_name.equals(full_county_name)) {
          county_code = c.county_code;
          break;
        }
      }
      if (county_code == -1) {
        response.status(400);
        responseMap.put("error", "Invalid county name");
        return responseMap;
      }
      System.out.println(county_code);
      // fetch the data for the given county and state
      String responseJson = this.sendRequest("https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E&for=county:"+county_code+"&in=state:"+state_code);
      List<CountyBroadbandAccess> broadband_list = CountyBroadbandAccess.deserializeCountyBroadbandAccessList(responseJson);
      String data = "";
      for (CountyBroadbandAccess c: broadband_list) {
        data += c.broadband_access + " ";
      }
      responseMap.put("state", state_name);
      responseMap.put("county", county_name);
      responseMap.put("status", "success");
      responseMap.put("data", data);
      return responseMap;
    } catch (IOException e) {
      response.status(500);
      responseMap.put("error", "Error fetching state codes");
      return responseMap;
    }
  }

  private String sendRequest(String url)
    throws URISyntaxException, IOException, InterruptedException {
    // send request to the ACS Census API
    HttpRequest buildACSApiRequest = HttpRequest.newBuilder()
        .uri(new URI(url))
      .GET()
      .build();
    // send the request and get the response
    HttpResponse<String> sentACSApiResponse = HttpClient.newBuilder().build().send(buildACSApiRequest, HttpResponse.BodyHandlers.ofString());
    System.out.println(sentACSApiResponse.body());
    return sentACSApiResponse.body();
  }
}
