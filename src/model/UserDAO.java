/*
 * Aditi Silawat (asilawat)
 * HW#9
 * December 04, 2014
 * Course 08-600
 */

package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.User;

public class UserDAO extends GenericDAO<User> {

	public UserDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(User.class, tableName, pool);
	}

	public User[] getUsers() throws RollbackException {
		User[] users = match();
		return users;
	}

	public void setPassword(String userName, String password) throws RollbackException {
		try {
			Transaction.begin();
			User dbUser = read(userName);

			if (dbUser == null) {
				throw new RollbackException("User " + userName + " no longer exists");
			}

			dbUser.setPassword(password);

			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
