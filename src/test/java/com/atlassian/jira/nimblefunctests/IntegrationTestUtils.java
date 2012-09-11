package com.atlassian.jira.nimblefunctests;

import com.atlassian.jira.testkit.client.restclient.Comment;
import com.atlassian.jira.testkit.client.restclient.Issue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Integration Tests Utils - contains helper methods that simplify tests.
 *
 * @since v0.2
 */
public class IntegrationTestUtils {

	@SuppressWarnings("unchecked")
	public static String getSummaryFieldValue(Issue.Fields fields) {
		try {
			if (fields.summary.getClass().isAssignableFrom(String.class)) {
				return (String) fields.getClass().getField("summary").get(fields);
			}
			else {
				return (String) fields.summary.getClass().getField("value").get(fields.summary);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@SuppressWarnings("unchecked")
	public static List<Comment> getComments(Issue.Fields fields) {
		try {
			if ("com.atlassian.jira.webtests.ztests.bundledplugins2.rest.client.Issue$IssueField".equals(fields.comment.getClass().getName())) {
				Field valueField = fields.comment.getClass().getField("value");
				return (List<Comment>) valueField.get(fields.comment);
			}
			else {
				Method getComments = fields.comment.getClass().getMethod("getComments");
				return (List<Comment>) getComments.invoke(fields.comment);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
