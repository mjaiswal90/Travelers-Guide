package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class StatusForm extends FormBean {
	private String status;
	
	public String getStatus() {
	 return status;
	}


	public void setStatus(String status) {
		this.status = "#TFGAnalytics "+status;
	}


	}
