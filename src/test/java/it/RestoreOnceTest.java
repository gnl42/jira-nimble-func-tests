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

import com.atlassian.jira.nimblefunctests.annotation.RestoreOnce;
import com.atlassian.jira.nimblefunctests.framework.NimbleFuncTestCase;
import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClient;
import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Testing if JIRA data is restored only once if @RestoreOnce is used.
 */
@RestoreOnce("jira-dump1.xml")
public class RestoreOnceTest extends NimbleFuncTestCase {

	private static int executedTests = 0;

	private final ProgressMonitor pm = new NullProgressMonitor();
	private JerseyJiraRestClient restClient;

	@Override
	public void beforeMethod() {
		super.beforeMethod();
		try {
			final URI jiraUri = UriBuilder.fromUri(environmentData.getBaseUrl().toURI()).build();
			restClient = new JerseyJiraRestClient(jiraUri, new BasicHttpAuthenticationHandler("admin", "admin"));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void firstTest() {
		testImpl();
	}

	@Test
	public void secondTest() {
		testImpl();
	}

	@Test
	public void thirdTest() {
		testImpl();
	}

	private void testImpl() {
		// check if there is exactly same comments count as already executed test count
		final IssueRestClient issueClient = restClient.getIssueClient();
		final int commentsLength = Iterables.size(issueClient.getIssue("FTC-1", pm).getComments());
		Assert.assertEquals(executedTests, commentsLength);

		// add new comment
		navigation.issue().addComment("FTC-1", "Comment from test method: " + runningTestMethod.getMethodName());
		executedTests++;

		// check if successfully added new comment
		final int commentsLengthAfterAdd = Iterables.size(issueClient.getIssue("FTC-1", pm).getComments());
		Assert.assertEquals(executedTests, commentsLengthAfterAdd);
	}

}
