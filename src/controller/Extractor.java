package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.*;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.mybeans.form.FormBeanFactory;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import formbeans.AuthForm;

public class Extractor extends Action {
	 private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";

	private FormBeanFactory<AuthForm> formBeanFactory = FormBeanFactory
			.getInstance(AuthForm.class);

	public Extractor(Model model) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return "extractor.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		String token;
		String verifier;
		
		try {
			AuthForm form = formBeanFactory.create(request);

			token = form.getOauth_token();
			verifier = form.getOauth_verifier();
			
			
			HttpSession session = request.getSession();
			session.setAttribute("token", token);
			session.setAttribute("verifier", verifier);

						
			OAuthService service = (OAuthService) session.getAttribute("service");
		    Token requestToken = (Token) session.getAttribute("requestToken");
		    Verifier verify = new Verifier(form.getOauth_verifier());
		    Token accessToken= service.getAccessToken(requestToken, verify);
		    session.setAttribute("accessToken", accessToken);
		  /*  try {
		    	

		 
				prop.setProperty("oauth.accessToken", accessToken.getToken());
				prop.setProperty("oauth.accessTokenSecret", accessToken.getSecret());
				System.out.println("Inside extractor oauth.accessToken:"+accessToken.getToken());
				System.out.println("Inside Extractor oauth.accessTokenSecret:"+accessToken.getSecret());

				os = new FileOutputStream("twitter4j.properties");
				prop.store(os,"sample ");
				os.close();
				
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(-1);
			} */
//File file = new File("twitter4j.properties");
			
		    try {
				Properties properties = new Properties();
				properties.setProperty("oauth.accessToken",accessToken.getToken() );
				properties.setProperty("oauth.accessTokenSecret", accessToken.getSecret() );
				properties.setProperty("favoritePerson", "Nicole");

				File file = new File("twitter4j.properties");
				FileOutputStream fileOut = new FileOutputStream(file);
				properties.store(fileOut, "Favorite Things");
				fileOut.close();
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		    
			System.out.println("Now we're going to access a protected resource...");
			    OAuthRequest request1 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			    service.signRequest(accessToken, request1);
			    Response response = request1.send();
			    System.out.println("Got it! Lets see what we found...");
			    System.out.println();
			    System.out.println(response.getBody());

			    JSONObject obj = new JSONObject(response.getBody());
			    String userName = obj.getString("name");
			    String screenName= obj.getString("screen_name");
			    String name[]=userName.split("\t");
			    session.setAttribute("name",screenName);
			    
			    session.setAttribute("screenName", screenName);
			    System.out.println("NAme="+userName);

			    
			    
			System.out.println(form.getOauth_token());
			System.out.println(form.getOauth_verifier());

		} catch (Exception e) {
			e.printStackTrace();
		} 		return "tweet.jsp";
	}

}
