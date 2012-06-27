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
import com.atlassian.jira.webtests.ztests.bundledplugins2.rest.client.IssueClient;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.junit.Assert;
import org.junit.Test;

import static com.atlassian.jira.nimblefunctests.IntegrationTestUtils.getSummaryFieldValue;

/**
 * Testing if overriding restore configuration works fine if there is @Restore on method
 * that uses other XML than the one specified for class.
 */
@Restore("jira-dump1.xml")
public class OverrideRestoreTest extends NimbleFuncTestCase {
	
	@Test
	public void getIssueTest() {
		IssueClient ic = new IssueClient(environmentData);
		Assert.assertEquals("My First Task", getSummaryFieldValue(ic.get("FTC-1").fields));
		try {
			ic.get("FTC-1");
		}
		catch (UniformInterfaceException e) {
			// it's ok, issue doesn't exists
			Assert.assertEquals(404, e.getResponse().getStatus());
		}
	}

	@Restore("jira-dump2.xml")
	@Test
	public void getIssueWithOverrideRestoreTest() {
		IssueClient ic = new IssueClient(environmentData);
		Assert.assertEquals("My First Task", getSummaryFieldValue(ic.get("FTC-1").fields));
		Assert.assertEquals("My Second Issue", getSummaryFieldValue(ic.get("FTC-2").fields));
	}

}
