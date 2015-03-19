/*
 * Aditi Silawat (asilawat)
 * HW#9
 * December 04, 2014
 * Course 08-600
 */

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.UserDAO;

public class LogoutAction extends Action {

	private UserDAO userDAO;

	public LogoutAction(Model model) {
		//userDAO = model.getUserDAO();
	}

	public String getName() {
		return "logout.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.setAttribute("accessToken", null);
		session.setAttribute("verifier", null);
		session.setAttribute("token", null);
		session.setAttribute("requestToken", null);

		request.setAttribute("message", "You are now logged out");
		return "login.jsp";
	}
}
