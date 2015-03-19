package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import databeans.User;
import formbeans.StatusForm;

public class TweetStatus extends Action {
	private FormBeanFactory<StatusForm> fbFactory = FormBeanFactory.getInstance(StatusForm.class);


	public TweetStatus(Model model) {
	}

	public String getName() {
		return "ajax_tweet.do";
	}

	public void performAjax(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			HttpSession session= request.getSession();
			StatusForm form = fbFactory.create(request);
		
			
			   
			   
			  try{OAuthService service= (OAuthService) session.getAttribute("service");
			   Token accessToken = (Token) session.getAttribute("accessToken");
			   String tweet = URLEncoder.encode(form.getStatus(),"UTF-8");
			   String urlTweet="https://api.twitter.com/1.1/statuses/update.json?status="+tweet;
			   System.out.println("request: "+urlTweet);
			   OAuthRequest request2 = new OAuthRequest(Verb.POST, urlTweet);
			   service.signRequest(accessToken, request2);
			   System.out.println("REQUEST: " + request2.getUrl());
			   Response response2 = request2.send();
			   System.out.println("Response body:"+response2.getBody());
			  }catch(Exception e)
			  {e.getMessage();}
			   
		} catch (FormBeanException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("inside perform");
		return null;
	}
	}
