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
    private static String acceptFriend_tag = "acceptFriend";
    private static String deleteFriend_tag = "deleteFriend";
    private static String blockFriend_tag = "blockFriend";
    private static String cancelFriendReq_tag = "cancelFriendReq";
    private static String addFavFriend_tag = "addFavFriend";
    private static String permaDeleteFriend_tag = "permaDeleteFriend";

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
        return json;
    }

    public JSONObject acceptFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", acceptFriend_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }

    public JSONObject deleteFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", deleteFriend_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }

    public JSONObject permaDeleteFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", permaDeleteFriend_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }
    public JSONObject cancelRequest(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", cancelFriendReq_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }

    public JSONObject blockFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", blockFriend_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }

    public JSONObject addFavFriend(String userId, String friendUsername) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", addFavFriend_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendUsername", friendUsername));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
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