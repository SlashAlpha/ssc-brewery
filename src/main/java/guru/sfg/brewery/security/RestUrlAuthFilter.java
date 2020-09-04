package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestUrlAuthFilter extends AbstractClassAuthFilter {


    public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getCredentials(HttpServletRequest request, int choice) {
        return choice == 1 ? request.getParameter("ApiKey") : request.getParameter("ApiSecret");
    }
}
