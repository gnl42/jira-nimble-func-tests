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
import org.junit.Assert;
import org.junit.Test;

import static com.atlassian.jira.nimblefunctests.IntegrationTestUtils.getComments;

/**
 * Testing if JIRA data is restored each test execution when @Restore is used on class.
 */
@Restore("jira-dump1.xml")
public class RestoreTest extends NimbleFuncTestCase {

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
		// check if there is no comments
		IssueClient ic = new IssueClient(environmentData);
		int commentsLength = getComments(ic.get("FTC-1").fields).size();
		Assert.assertEquals(0, commentsLength);

		// add new comment
		navigation.issue().addComment("FTC-1", "Comment from test method: " + runningTestMethod.getMethodName());

		// check if successfully added new comment
		int commentsLengthAfterAdd = getComments(ic.get("FTC-1").fields).size();
		Assert.assertEquals(1, commentsLengthAfterAdd);
	}

}
