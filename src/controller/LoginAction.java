package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import databeans.User;
import formbeans.LoginForm;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	  private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";


	public LoginAction(Model model) {
	}

	public String getName() {
		return "login.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return "login.jsp";
			}
			  
			  System.out.println("in login page");
				// --------If you choose to use a callback, "oauth_verifier" will be the return value by Twitter (request param)----------
			    OAuthService service = new ServiceBuilder()
			                                .provider(TwitterApi.SSL.class)
			                                .callback("http://localhost:8080/asilawat_1_hw9/extractor.do")
			                                .apiKey("bhR3WeOJWN1GLDN3J8pEHt0tC")
			                                .apiSecret("b49fnxofHPR95Bjoh6reVfBpEN22VwKOOAIE3Fng5HEnyBMEIA")
			                                .build();
			    Scanner in = new Scanner(System.in);

			    System.out.println("=== Twitter's OAuth Workflow ===");
			    System.out.println();
			    HttpSession session= request.getSession();

			    //-------------------------Obtain the Request Token------------------------------------------------------------------------
			    System.out.println("Fetching the Request Token...");
			    Token requestToken = service.getRequestToken();
			   session.setAttribute("requestToken", requestToken);
			    System.out.println("Got the Request Token!");
			    System.out.println();

			      
			    System.out.println("Now go and authorize Scribe here:");
			    System.out.println(service.getAuthorizationUrl(requestToken));
			   
			  //--------------------------Opening the Authentication Page------------------------------------------------------------------  
			    String link=service.getAuthorizationUrl(requestToken);
			    session.setAttribute("service",service);

			    return link;
			    
			   /* try {
			        Desktop.getDesktop().browse(new URI(service.getAuthorizationUrl(requestToken)));
			    } catch (UnsupportedOperationException ignore) {
			    } catch (IOException ignore) {
			    } catch (URISyntaxException e) {
			        throw new AssertionError(e);
			    }
*/
			     
			   /* System.out.println("And paste the verifier here");
			    System.out.print(">>");
			    Verifier verifier = new Verifier(in.nextLine());
			   
			    System.out.println();
			    

			    //-----------------Trade the Request Token and Verfier for the Access Token----------------------------------------------
			    System.out.println("Trading the Request Token for an Access Token...");
			    Token accessToken = service.getAccessToken(requestToken, verifier);
			    System.out.println("Got the Access Token!");
			    System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
			    System.out.println();

			    //---------------------Now let's go and ask for a protected resource!-----------------------------------------------------
			    System.out.println("Now we're going to access a protected resource...");
			    OAuthRequest request1 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			    service.signRequest(accessToken, request1);
			    Response response = request1.send();
			    System.out.println("Got it! Lets see what we found...");
			    System.out.println();
			    System.out.println(response.getBody());
			

			return "login.jsp";*/
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
