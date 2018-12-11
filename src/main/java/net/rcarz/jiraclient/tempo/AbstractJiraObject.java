package net.rcarz.jiraclient.tempo;

import net.sf.json.JSONObject;

public abstract class AbstractJiraObject {
    protected String id;
    protected String name;
    protected JSONObject jsonObject;


    public AbstractJiraObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractJiraObject() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
