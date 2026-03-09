package lk.taskmanager.api.config.security.custom;

import lk.taskmanager.api.model.User;
import lk.taskmanager.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("userId", user.getId());
            additionalInfo.put("email", user.getEmail());
            additionalInfo.put("firstName", user.getFirstName());
            additionalInfo.put("lastName", user.getLastName());
            additionalInfo.put("role", user.getRole().name());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        });
        return accessToken;
    }
}
