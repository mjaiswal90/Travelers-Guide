package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {
	// Returns the name of the action, used to match the request in the hash table
	public abstract String getName();

	// Returns the name of the jsp used to render the output.
	public abstract String perform(HttpServletRequest request);

	public void performAjax(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Calling perform AJAX from action");
	}

	//
	// Class methods to manage dispatching to Actions
	//
	private static Map<String, Action> hash = new HashMap<String, Action>();

	public static void add(Action a) {
		synchronized (hash) {
			hash.put(a.getName(), a);
		}
	}

	public static String perform(String path, HttpServletRequest request) {
		return Action.perform(path, request, null);
	}

	public static String perform(String path, HttpServletRequest request, HttpServletResponse response) {
		String[] paths = path.split("\\?");
		if (paths == null || paths.length == 0)
			return null;
		String actionName = paths[0];
		Action a;
		synchronized (hash) {
			a = hash.get(actionName);
		}

		if (a == null)
			return null;

		if (paths.length == 2) {
			String[] paramPairs = paths[1].split("&");
			for (String pair : paramPairs) {
				String[] kv = pair.split("=");
				request.setAttribute(kv[0], kv[1]);
			}
		}
		if (path.indexOf("ajax_") != -1 && response != null) {
			a.performAjax(request, response);
			return "";
		}
		return a.perform(request);
	}

}
