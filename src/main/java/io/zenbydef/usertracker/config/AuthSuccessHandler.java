package io.zenbydef.usertracker.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                httpServletResponse.sendRedirect("/admin/list");
                break;
            } else {
                httpServletResponse.sendRedirect("/user");
            }
        }
    }

    private void setAttributes(Authentication authentication, HttpSession session) {
        User user = (User) authentication.getPrincipal();
        session.setAttribute("user", user);
        session.setAttribute("userName", user.getUsername());
        session.setAttribute("Authorities", user.getAuthorities());
    }
}
