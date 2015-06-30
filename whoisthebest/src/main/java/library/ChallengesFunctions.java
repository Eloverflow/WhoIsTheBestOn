package library;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 2015-02-22.
 */
public class ChallengesFunctions {

    private JSONParser jsonParser;

    private static String challengesURL = "http://104.131.185.129/witb/android/challenges.php";

    private static String challenges_tag = "challenges";
    private static String searchChallenge_tag = "searchChallenge";
    private static String target_tag = "target";

    // constructor
    public ChallengesFunctions(){
        jsonParser = new JSONParser();
    }

    public JSONObject getChallengesList(String listType){
        
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", challenges_tag));
        params.add(new BasicNameValuePair("listType", listType));
        JSONObject json = jsonParser.getJSONFromUrl(challengesURL, params);
        return json;
    }

    public JSONObject searchChallenge(String searchedName){

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", searchChallenge_tag));
        params.add(new BasicNameValuePair("searchedName", searchedName));
        JSONObject json = jsonParser.getJSONFromUrl(challengesURL, params);
        return json;
    }

}
