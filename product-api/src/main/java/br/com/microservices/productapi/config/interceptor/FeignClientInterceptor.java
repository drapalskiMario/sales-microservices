package br.com.microservices.productapi.config.interceptor;

import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest currentRequest = this.getCurrentRequest();
        requestTemplate
                .header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes())
                    .getRequest();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DomainApplicationException("The current request could be not processed");
        }
    }
}
