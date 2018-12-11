package net.rcarz.jiraclient.tempo;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Sergey Nekhviadovich on 12/4/2018.
 */
public class TempoResource {
    public static final String HOST = "http://localhost:8090/jira";
    private static final String TEAM_BASE_URI = "/rest/tempo-teams/1/team";
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private TempoResource() {
    }

    private static String getApiRev() {
        return "1";
    }

    public static String getBaseTempoUri() {
        return "rest/tempo-accounts/" + getApiRev();
    }

    public static String getTeamBaseUri() {
        return TEAM_BASE_URI;
    }

    public static List<AbstractJiraObject> getListOfObjects(RestClient restClient, String query,
                                                            Class<? extends AbstractJiraObject> aClass)
            throws JiraException, IllegalAccessException, InstantiationException {
        JSON jsonResponse;
        String objectClass = aClass.getSimpleName();
        try {
            jsonResponse = restClient.get(query);
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


    public static JSONObject asJsonObject(AbstractJiraObject object) {
        JSONObject result = new JSONObject();
        Map<String, String> map = asMapRepresentation(object);
        map.forEach(result::put);
        return result;
    }

    private static Map<String, String> asMapRepresentation(AbstractJiraObject object) {
        Field[] fields = object.getClass().getFields();
        Function<Field, String> function = field -> {
            field.setAccessible(true);
            String value = null;
            try {
                value = field.get(object).toString();
            } catch (IllegalAccessException e) {
               LOGGER.warning(e.getMessage());
            }

            return value;
        };
        return Arrays.stream(fields)
                .collect(Collectors.toMap(Field::getName, function, (oldValue, newValue) -> oldValue));
    }

    public static void createObject(RestClient restClient, AbstractJiraObject abstractJiraObject, String query) throws JiraException {

        JSON result;
        try {
            result = restClient.post(query,
                    TempoResource.asJsonObject(abstractJiraObject));
        } catch (Exception ex) {
            throw new JiraException("Failed to create " + abstractJiraObject.getClass().getSimpleName(), ex);
        }
        if (!(result instanceof JSONObject)) {
            throw new JiraException("Unexpected result creation");
        }

    }

}

