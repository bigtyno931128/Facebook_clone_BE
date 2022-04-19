package com.best2team.facebook_clone_be.security;

import com.best2team.facebook_clone_be.security.jwt.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        try {
            response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
            //response.getWriter().write("로그인이 완료되었습니다.");
            String data ="로그인이 완료되었습니다.";
            String msg = new String (objectMapper.writeValueAsString(data).getBytes("UTF-8"), "ISO-8859-1");
            response.getOutputStream()
                    .println(msg);
        }catch (Exception e){

        }

    }

}
