/*
 * Aditi Silawat (asilawat)
 * HW#9
 * December 04, 2014
 * Course 08-600
 */

package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Favorite {

	private int id = -1;
	private String comment = null;
	private String url = null;
	private String owner = null;
	private int clickCount = 0;

	public String getComment() {
		return comment;
	}

	public String getUrl() {
		return url;
	}

	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(int x) {
		id = x;
	}

	public void setOwner(String userName) {
		owner = userName;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public String toString() {
		return "favorite(" + id + ")";
	}
}
