/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it;

import com.atlassian.jira.nimblefunctests.annotation.Restore;
import com.atlassian.jira.nimblefunctests.framework.NimbleFuncTestCase;
import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClient;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Testing if overriding restore configuration works fine if there is @Restore on method
 * that uses other XML than the one specified for class.
 */
@Restore("jira-dump1.xml")
public class OverrideRestoreTest extends NimbleFuncTestCase {

    private final ProgressMonitor pm = new NullProgressMonitor();
    private JerseyJiraRestClient restClient;

    @Override
    public void beforeMethod()
    {
		super.beforeMethod();
        try
        {
            final URI jiraUri = UriBuilder.fromUri(environmentData.getBaseUrl().toURI()).build();
            restClient = new JerseyJiraRestClient(jiraUri, new BasicHttpAuthenticationHandler("admin", "admin"));
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

	@Test
	public void getIssueTest() {
        final IssueRestClient issueClient = restClient.getIssueClient();
        Assert.assertEquals("My First Task", issueClient.getIssue("FTC-1", pm).getSummary());
		try {
			issueClient.getIssue("FTC-1", pm);
		}
		catch (UniformInterfaceException e) {
			// it's ok, issue doesn't exists
			Assert.assertEquals(404, e.getResponse().getStatus());
		}
	}

	@Restore("jira-dump2.xml")
	@Test
	public void getIssueWithOverrideRestoreTest() {
        final IssueRestClient issueClient = restClient.getIssueClient();
		Assert.assertEquals("My First Task", issueClient.getIssue("FTC-1", pm).getSummary());
		Assert.assertEquals("My Second Issue", issueClient.getIssue("FTC-2", pm).getSummary());
	}

}
