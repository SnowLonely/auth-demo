package cn.lonelysnow.auth.config.oauth;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * @author LonelySnow
 * @classname AuthorizationConfig
 * @description 授权服务配置
 * @date 2022/10/10 09:39
 */
@EnableWebSecurity
public class AuthorizationServiceConfig extends OAuth2AuthorizationServerConfiguration {

    // @Bean
    // @Order(1)
    // public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    //     OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    //     http
    //             .exceptionHandling((exceptions) -> exceptions
    //                     .authenticationEntryPoint(
    //                             // 未通过身份验证时重定向到登录页面授权端点
    //                             new LoginUrlAuthenticationEntryPoint("/login"))
    //             );
    //
    //     return http.build();
    // }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).csrf().disable().build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // 客户端标识
                .clientId("userinfo")
                // 应用客户端凭证
                .clientSecret("{noop}123456")
                // 通过Basic Auth方式
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 授权类型
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 认证通过之后redirect的uri，接收授权码使用
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code")
                .redirectUri("http://localhost:8080/login/oauth2/code")
                // 重定向地址：便于调试授权码流程
                // .redirectUri("https://www.baidu.com")
                // 授权范围
                .scope(OidcScopes.OPENID)
                .scope("userinfo")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * JWKSource 用于签名访问令牌。
     *
     * @return
     * @see com.nimbusds.jose.jwk.source.JWKSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 带有启动生成的密钥的密钥对，用于创建上面的 {@code com.nimbusds.jose.jwk.source.JWKSource }。
     *
     * @see java.security.KeyPair
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * 用于配置授权服务器的授权端点
     * @see ProviderSettings#builder()
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().build();
    }

}
