package net.rcarz.jiraclient.tempo;

import net.sf.json.JSONObject;

public abstract class AbstractJiraObject {
    protected JSONObject jsonObject;

    public AbstractJiraObject() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
