package hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.catalina.ssi.ByteArrayServletOutputStream;

//@Component
public class SmartFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest syntheticRequest = new SyntheticRequest((HttpServletRequest) request);
		HttpServletResponse syntheticResponse = new SyntheticResponse((HttpServletResponse) response);

		HttpServletRequest syntheticRequest2 = new SyntheticRequest((HttpServletRequest) request);
		HttpServletResponse syntheticResponse2 = new SyntheticResponse((HttpServletResponse) response);

		chain.doFilter(syntheticRequest, syntheticResponse);
		chain.doFilter(syntheticRequest2, syntheticResponse2);
		
	}

	@Override
	public void destroy() {
	}

	
	private class SyntheticRequest extends HttpServletRequestWrapper {

		public SyntheticRequest(HttpServletRequest request) {
			super(request);
		}
		
		@Override
		public String getMethod() {
			System.out.println("METHOD:  " + super.getMethod());
			return super.getMethod();
		}
		
		@Override
		public String getPathTranslated() {
			System.out.println("PATH TRANSLATED:  " + super.getPathTranslated());
			return super.getPathTranslated();
		}
		
		@Override
		public String getPathInfo() {
			System.out.println("PATH INFO:  " + super.getPathInfo());
			return super.getPathInfo();
		}
		
		@Override
		public String getRequestURI() {
			System.out.println("REQUEST URI:  " + super.getRequestURI());
			return super.getRequestURI();
		}
		
		@Override
		public StringBuffer getRequestURL() {
			System.out.println("REQUEST URL:  " + super.getRequestURL());
			return super.getRequestURL();
		}
	}
	
	private class SyntheticResponse extends HttpServletResponseWrapper {

		private ServletOutputStream os;

		public SyntheticResponse(HttpServletResponse response) {
			super(response);
			this.os = new ByteArrayServletOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return os;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(getOutputStream());
		}
		
	}
	
}
