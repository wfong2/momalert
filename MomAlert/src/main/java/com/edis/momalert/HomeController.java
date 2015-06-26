package com.edis.momalert;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edis.momalert.form.FitbitForm;
import com.edis.momalert.form.UserForm;
import com.edis.momalert.model.PCUser;
import com.fitbit.api.FitbitAPIException;
import com.fitbit.api.client.FitbitAPIEntityCache;
import com.fitbit.api.client.FitbitApiClientAgent;
import com.fitbit.api.client.FitbitApiCredentialsCache;
import com.fitbit.api.client.FitbitApiSubscriptionStorage;
import com.fitbit.api.client.LocalUserDetail;
import com.fitbit.api.client.service.FitbitAPIClientService;
import com.fitbit.api.model.APIResourceCredentials;
import com.fitbit.api.model.ApiSubscription;
import com.fitbit.web.context.RequestContext;






import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_VERIFIER = "oauth_verifier";

    private FitbitAPIClientService<FitbitApiClientAgent> apiClientService;
    @Value("#{config['apiBaseUrl']}")
    private String apiBaseUrl;
	
    @Value("#{config['exampleBaseUrl']}")
    private String exampleBaseUrl;

    @Value("#{config['clientConsumerKey']}")
    private String clientConsumerKey;
    @Value("#{config['clientSecret']}")
    private String clientSecret;
    @Value("#{config['fitbitSiteBaseUrl']}")
    private String fitbitSiteBaseUrl;
    
    @Resource
    private FitbitAPIEntityCache entityCache;
    @Resource
    private FitbitApiCredentialsCache credentialsCache;
    @Resource
    private FitbitApiSubscriptionStorage subscriptionStore;
    
    
    static SessionFactory sessionFactory;

	/**
	 * Simply selects the home view to render by returning its name.
	 */

    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.info("Welcome index");
		
		return "index";
	}
    
    
    
    @RequestMapping(value = "/home", method = RequestMethod.POST)
	public String home(UserForm myForm) {
		logger.info("Welcome home! ");
		
	    logger.info("mmy name   "+myForm.getName());
	    logger.info("mmy email   "+myForm.getEmail());
	    logger.info("mmy first_name   "+myForm.getFirst_name());
	    logger.info("mmy gender   "+myForm.getGender());
	    logger.info("mmy id   "+myForm.getId());
	    logger.info("mmy last_name   "+myForm.getLast_name());
	    logger.info("mmy link   "+myForm.getLink());
	    logger.info("mmy locale   "+myForm.getLocale());
	    logger.info("mmy timezone   "+myForm.getTimezone());
	    
	    PCUser user = new PCUser();
	    user.setEmail(myForm.getEmail());
	    user.setName(myForm.getName());
	    user.setFirst_name(myForm.getFirst_name());
	    user.setGender(myForm.getGender());
	    user.setFacebookId(myForm.getId());
	    user.setLast_name(myForm.getLast_name());
	    user.setLink(myForm.getLink());
	    user.setLocale(myForm.getLocale());
	    user.setTimezone(myForm.getTimezone());
	    
        Configuration config = new Configuration();
        config.configure();

        ServiceRegistry  serviceRegistry = (ServiceRegistry) new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(serviceRegistry);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
 
		
		session.save(user);
 
		session.getTransaction().commit();
		session.close();
	    
		
		return "home";
	}
	
	@RequestMapping(value="/fitbitHome")
	public String fitbitHome(){
		logger.info("fitbit form page");
		return "fitbitHome";
	}
	
	
	@RequestMapping(value="/addFitbit", method = RequestMethod.POST)
	public String addFitbit(FitbitForm myForm, Model model, HttpServletRequest request, HttpServletResponse response){
		
		
		
		logger.info("wwwwwwwww "+myForm.getName());
		
		 RequestContext context = new RequestContext();
	        
		 context.setOurUser(new LocalUserDetail(myForm.getName()));
		 populate(context, request, response);
		 try {
	            // Redirect to page where user can authorize the application:
	            return "redirect:" + context.getApiClientService().getResourceOwnerAuthorizationURL(context.getOurUser(), getExampleBaseUrl() + "/completeAuthorization");
	        } catch (FitbitAPIException e) {
	           e.printStackTrace();
	           return "fitbitHome";
	        }
	}

	
	 @RequestMapping("/completeAuthorization")
	    public String showCompleteAuthorization(HttpServletRequest request, HttpServletResponse response) {
	        RequestContext context = new RequestContext();
	        populate(context, request, response);
	        String tempTokenReceived = request.getParameter(OAUTH_TOKEN);
	        String tempTokenVerifier = request.getParameter(OAUTH_VERIFIER);
	        APIResourceCredentials resourceCredentials = context.getApiClientService().getResourceCredentialsByTempToken(tempTokenReceived);

	        if (resourceCredentials == null) {
	            logger.error("Unrecognized temporary token when attempting to complete authorization: " + tempTokenReceived);
	            request.setAttribute("errors", "Unrecognized temporary token when attempting to complete authorization: " + tempTokenReceived);
	        } else {
	            // Get token credentials only if necessary:
	            if (!resourceCredentials.isAuthorized()) {
	                // The verifier is required in the request to get token credentials:
	                resourceCredentials.setTempTokenVerifier(tempTokenVerifier);
	                try {
	                    // Get token credentials for user:
	                    context.getApiClientService().getTokenCredentials(new LocalUserDetail(resourceCredentials.getLocalUserId()));
	                } catch (FitbitAPIException e) {
	                    logger.error("Unable to finish authorization with Fitbit.", e);
	                    request.setAttribute("errors", Collections.singletonList(e.getMessage()));
	                }
	            }
	        }

	      
	        return "fitbitDetail";
	    }
	@RequestMapping(value="/fitbitDetail")
	public String fitbitDetail(){
		
		logger.info("wwwwwwwww fitbit/detail");
		return "fitbitDetail";
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
        apiClientService = new FitbitAPIClientService<FitbitApiClientAgent>(
                new FitbitApiClientAgent(getApiBaseUrl(), getFitbitSiteBaseUrl(), credentialsCache),
                clientConsumerKey,
                clientSecret,
                credentialsCache,
                entityCache,
                subscriptionStore
        );
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    
    public String getExampleBaseUrl() {
        return exampleBaseUrl;
    }

    public String getClientConsumerKey() {
        return clientConsumerKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    public String getFitbitSiteBaseUrl() {
        return fitbitSiteBaseUrl;
    }

    public void populate(RequestContext context, HttpServletRequest request, HttpServletResponse response) {
        context.setApiClientService(apiClientService);

       
        APIResourceCredentials resourceCredentials = context.getApiClientService().getResourceCredentialsByUser(context.getOurUser());
        boolean isAuthorized = resourceCredentials != null && resourceCredentials.isAuthorized();
        boolean isSubscribed = false;
        if (isAuthorized) {
            List<ApiSubscription> subscriptions = Collections.emptyList();
            try {
                subscriptions = apiClientService.getClient().getSubscriptions(context.getOurUser());
            } catch (FitbitAPIException e) {
                logger.error("Subscription error: " + e, e);
            }
            if (null != context.getOurUser() && subscriptions.size() > 0) {
                isSubscribed = true;
            }
        }
        request.setAttribute("actionBean", context);
        request.setAttribute("isSubscribed", isSubscribed);
        request.setAttribute("exampleBaseUrl", getExampleBaseUrl());
    }
}
