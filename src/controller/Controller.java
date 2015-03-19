package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;

import model.Model;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());

		Action.add(new LoginAction(model));
		Action.add(new Extractor(model));
		Action.add(new TweetStatus(model));
		Action.add(new LogoutAction(model));

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = performTheAction(request,response);
		sendToNextPage(nextPage, request, response);
	}

	private String performTheAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);

		if (action.equals("login.do")  || action.equals("extractor.do") || action.equals("logout.do")) {
			return Action.perform(action, request);
		}

		if (action.indexOf("ajax_") != -1) {
			return Action.perform(action, request, response);
		}
		return Action.perform(action, request);
	}

	private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
			return;
		}
		
		if (nextPage.isEmpty())
			return;

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
			d.forward(request, response);
			return;
			
		}

		if (nextPage.equals("image")) {
			RequestDispatcher d = request.getRequestDispatcher(nextPage);
			d.forward(request, response);
			return;
		}
		
		if (nextPage.startsWith("http://") || nextPage.startsWith("https://")) {
			response.sendRedirect(nextPage);
			return;
		}

		throw new ServletException(Controller.class.getName() + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	private String getActionName(String path) {
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
