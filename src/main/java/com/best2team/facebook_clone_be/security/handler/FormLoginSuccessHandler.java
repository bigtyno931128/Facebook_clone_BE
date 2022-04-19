package com.best2team.facebook_clone_be.security.handler;

import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.security.jwt.JwtTokenUtils;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);

        chatRoomRepository.enterChatRoom(Long.toString(userDetails.getUser().getUserId()));
        try {
            response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
            response.addHeader("RoomId",Long.toString(userDetails.getUser().getUserId()));
            response.setStatus(HttpServletResponse.SC_OK);
        }catch (Exception e){

        }

    }

}
