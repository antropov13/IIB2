package setup;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// Any Classes that extends AbstractAnnotationConfigDispatcherServletInitializer will automatically be used to configure DispatscherServlet 
public class FrontController extends AbstractAnnotationConfigDispatcherServletInitializer { // same function as web.xml

	// AbstractAnnotationConfigDispatcherServletInitializer creates: a
	// DispatcherServlet and a ContextLoaderListener

	// When DispatcherServlet starts up, it creates a Spring Application context and
	// starts loading it with beans declared in the
	// configuration files or classes that its given

	@Override
	protected String[] getServletMappings() {

		return new String[] { "/" }; // Map DispatcherServlet to "/" (indicating that this is the applications'
										// default servlet. It will
		// handle all the requests coming into the application)
	}

	@Override
	protected Class<?>[] getRootConfigClasses() { // configure the application context created by ContextloaderListener
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub

		return new Class[] { WebConfig.class };// Specify configuration class;
	}
}
