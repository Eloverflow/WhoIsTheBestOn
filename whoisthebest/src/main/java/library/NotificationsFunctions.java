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

    private static String notifsURL = "http://104.131.185.129/witb/android/notifications.php";

    private static String friendsNotifs_tag = "friendNotifs";
    private static String friendNotifSeen_tag = "friendNotifSeen";

    public NotificationsFunctions() {
        jsonParser = new JSONParser();
    }

    public JSONObject getNotifsFriend(String userId) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", friendsNotifs_tag));
        params.add(new BasicNameValuePair("userId", userId));
        JSONObject json = jsonParser.getJSONFromUrl(notifsURL, params);
        return json;
    }

    public JSONObject friendNotifSeen(String userId, String friendName) {// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", friendNotifSeen_tag));
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("friendName", friendName));
        JSONObject json = jsonParser.getJSONFromUrl(notifsURL, params);
        return json;
    }
}