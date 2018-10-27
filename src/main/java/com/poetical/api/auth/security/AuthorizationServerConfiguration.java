package com.poetical.api.auth.security;

import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Value("${poetical.username}")
    private String id;

    @Value("${poetical.password}")
    private String password;

    @Value("${poetical.resource-id}")
    private String resourceId;

    private final int access_token_validity_seconds = 90 * 120 * 100;
    
    private final int refresh_token_validity_seconds = 100 * 120 * 140;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
        .inMemory()
        .withClient(id)
        .secret(encoder.encode(password))
        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
        .authorities("user", "admin")
        .scopes("read", "write", "trust")
        .resourceIds(resourceId)
        .accessTokenValiditySeconds(access_token_validity_seconds)
        .refreshTokenValiditySeconds(refresh_token_validity_seconds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .tokenStore(tokenStore)
        .authenticationManager(authenticationManager);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);

        return tokenServices;
    }
}