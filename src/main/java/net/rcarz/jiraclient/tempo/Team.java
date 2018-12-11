package net.rcarz.jiraclient.tempo;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

import java.util.List;
import java.util.stream.Collectors;

public class Team extends AbstractJiraObject {


    public List<Team> getTeams(RestClient restclient) throws JiraException, InstantiationException, IllegalAccessException {
        List<? super AbstractJiraObject> list = TempoResource.getListOfObjects(Team.class, TempoResource.getBaseTempoTeamUri(), restclient);

        return list.stream()
                .map( e -> (Team)e)
                .collect(Collectors.toList());
    }
}