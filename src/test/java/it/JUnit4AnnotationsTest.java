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

import com.atlassian.jira.nimblefunctests.framework.NimbleFuncTestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This test just test if JUnit4 annotations (@Test, @Ignore) works fine.
 */
public class JUnit4AnnotationsTest extends NimbleFuncTestCase {

	@Test
	public void emptyTest() {
	}

	@SuppressWarnings("unused")
	public void notTest() {
		Assert.fail("This function is not a test, shouldn't be executed");
	}

	@Ignore
	@Test
	public void ignoredTest() {
		Assert.fail("This test is ignored, shouldn't be executed");
	}
}
