
package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public Model(ServletConfig config) throws ServletException {
		/*try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");

			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			userDAO = new UserDAO("asilawat_user", pool);
			favoriteDAO = new FavoriteDAO("asilawat_favorite", pool);
		} catch (DAOException e) {
			throw new ServletException(e);
		}*/
	}

	/*public FavoriteDAO getfavoriteDAO() {
		return favoriteDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}*/
}
