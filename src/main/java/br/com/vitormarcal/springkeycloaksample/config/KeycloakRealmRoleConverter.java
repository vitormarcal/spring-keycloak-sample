package br.com.vitormarcal.springkeycloaksample.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Recupera as roles do claim "realm_access" e "scope".
 * Diferente do converter padrão do Spring pois ele só retorna o claim "scope".
 */
public final class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String SCOPE_PREFIX = "SCOPE_";
    private final String ROLE_PREFIX = "ROLE_";

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(final Jwt jwt) {

        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        Collection<GrantedAuthority> authorities = ((List<String>) realmAccess.get("roles")).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

            authorities.addAll(getScopes(jwt));

        return authorities;
    }

    private Collection<GrantedAuthority> getScopes(final Jwt jwt) {

        Object scope = jwt.getClaim("scope");
        if (scope instanceof String) {
            Collection<String> scopes = StringUtils.hasText((String)scope) ? Arrays.asList(((String)scope).split(" ")) : Collections.emptyList();
            return scopes
                    .stream()
                    .map(roleName -> SCOPE_PREFIX + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

        }

        return Collections.emptyList();
    }
}
