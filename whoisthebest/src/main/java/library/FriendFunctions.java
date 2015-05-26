package library;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 2015-05-07.
 */
public class FriendFunctions {

    private JSONParser jsonParser;

    private static String friendsURL = "http://104.131.185.129/witb/android/friends.php";

    private static String friendsList_tag = "friendsList";
    private static String target_tag = "target";
    private static String addFriends_tag = "addFriend";

    // constructor
    public FriendFunctions() {
        jsonParser = new JSONParser();
    }

    public JSONObject getFriendList(String userId) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", friendsList_tag));
        params.add(new BasicNameValuePair("userId", userId));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }

    public JSONObject addFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", addFriends_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        Log.d("test3", json.toString());
        return json;
    }

    public JSONObject getTargetList(String listType){

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", target_tag));
        params.add(new BasicNameValuePair("listType", listType));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }
}