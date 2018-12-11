package net.rcarz.jiraclient.tempo;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Sergey Nekhviadovich on 12/4/2018.
 */
public class TempoResource {

    private TempoResource() {
    }

    private static String getApiRev() {
        return "1";
    }

    public static String getBaseTempoUri() {
        return "rest/tempo-accounts/" + getApiRev();
    }

    public static String getBaseTempoTeamUri() {
        return "/rest/tempo-teams/1/team";
    }

    public static List<AbstractJiraObject> getListOfObjects(Class<? extends AbstractJiraObject> aClass,
                                                            String query, RestClient restclient)
            throws JiraException, IllegalAccessException, InstantiationException {
        JSON jsonResponse;
        String objectClass = aClass.getSimpleName();
        try {
            jsonResponse = restclient.get(query);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve " + objectClass, ex);
        }
        if (!(jsonResponse instanceof JSONArray)) {
            throw new JiraException("JSON payload is malformed");
        }
        List<AbstractJiraObject> result = new ArrayList<>();
        Iterator it = ((JSONArray) jsonResponse).iterator();
        while (it.hasNext()) {
            AbstractJiraObject object = aClass.newInstance();
            object.setJsonObject((JSONObject) it.next());
            result.add(object);

        }

        return result;
    }

}

