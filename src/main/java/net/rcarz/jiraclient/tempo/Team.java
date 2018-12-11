package net.rcarz.jiraclient.tempo;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

import java.util.List;
import java.util.stream.Collectors;

public class Team extends AbstractJiraObject {
    private static final String GET_ALL_TEAMS_QUERY = "";
    private static final String CREATE_NEW_TEAM_QUERY = "";


    public List<Team> getTeams(RestClient restclient) throws JiraException, InstantiationException, IllegalAccessException {
        List<? super AbstractJiraObject> list = TempoResource.getListOfObjects(restclient, TempoResource.getTeamBaseUri()+GET_ALL_TEAMS_QUERY, Team.class);

        return list.stream()
                .map(e -> (Team) e)
                .collect(Collectors.toList());
    }

    public void createTeam(RestClient restClient, AbstractJiraObject abstractJiraObject) throws JiraException {
        TempoResource.createObject(restClient, abstractJiraObject, TempoResource.getTeamBaseUri()+CREATE_NEW_TEAM_QUERY);
    }




}