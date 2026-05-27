/**
 * This package contains client classes for invoking Pipedream workflows.
 *
 * <p>The workflows package provides functionality to:
 * <ul>
 *   <li>Invoke workflows using their HTTP interface URLs or endpoint IDs</li>
 *   <li>Invoke workflows on behalf of external users (Pipedream Connect)</li>
 *   <li>Support both synchronous and asynchronous invocation patterns</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * // Invoke a workflow
 * Object response = client.workflows().invoke("https://your-workflow.m.pipedream.net",
 *     "POST", Map.of("key", "value"), null, HTTPAuthType.OAUTH);
 *
 * // Invoke a workflow for an external user
 * Object response = client.workflows().invokeForExternalUser(
 *     "https://your-workflow.m.pipedream.net",
 *     "external-user-123",
 *     "POST",
 *     Map.of("data", "payload"),
 *     null
 * );
 * </pre>
 */
package com.pipedream.api.resources.workflows;
