package net.rcarz.jiraclient.tempo;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class TeamsTest {


    @Test
    public void checkTeamsSizeMoreThenZero() throws URISyntaxException, JiraException, IllegalAccessException, InstantiationException, IOException {
        AbstractJiraObject team = new Team();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            List<Team> teamList = ((Team) team).getTeams(new RestClient(httpClient, new URI("")));
            Assert.assertFalse(teamList.isEmpty());
        }
    }
}
