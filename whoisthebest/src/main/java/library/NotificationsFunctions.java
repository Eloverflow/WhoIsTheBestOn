package library;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 2015-05-07.
 */
public class NotificationsFunctions {

    private JSONParser jsonParser;

    private static String friendsURL = "http://104.131.185.129/witb/android/notifications.php";

    private static String friendsNotif_tag = "friendsNotif";

    // constructor
    public NotificationsFunctions() {
        jsonParser = new JSONParser();
    }

    public JSONObject getFriendList(String userId) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", friendsNotif_tag));
        params.add(new BasicNameValuePair("userId", userId));
        JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
        return json;
    }
}