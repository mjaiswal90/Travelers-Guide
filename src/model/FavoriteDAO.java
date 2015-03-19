/*
 * Aditi Silawat (asilawat)
 * HW#9
 * December 04, 2014
 * Course 08-600
 */

package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Favorite;

public class FavoriteDAO extends GenericDAO<Favorite> {
	public FavoriteDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Favorite.class, tableName, pool);
	}

	public void create(Favorite newfavorite) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(newfavorite);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void delete(int id, String owner) throws RollbackException {
		try {
			Transaction.begin();
			Favorite p = read(id);

			if (p == null) {
				throw new RollbackException("Favorite does not exist: id=" + id);
			}

			if (!owner.equals(p.getOwner())) {
				throw new RollbackException("Favorite not owned by " + owner);
			}

			delete(id);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public Favorite[] getFavorites(String owner) throws RollbackException {
		Favorite[] list = match(MatchArg.equals("owner", owner));
		return list;
	}

	public Favorite getFavorites(int id) throws RollbackException {
		Favorite[] p = match(MatchArg.equals("id", id));
		return p[0];
	}

	public void update(int id, int clickCount) throws RollbackException {
		try {
			Transaction.begin();
			Favorite favorite = read(id);
			if (favorite == null) {
				throw new RollbackException("Favorite with id " + id + " does not exist");
			}
			favorite.setClickCount(clickCount);
			update(favorite);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
