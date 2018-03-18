package ru.hh.school.adaptation.services.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.HHApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import ru.hh.school.adaptation.dto.HhUserInfoDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

class HhApiService {
  private static final String PROTECTED_RESOURCE_URL = "https://api.hh.ru/me";
  private final OAuth20Service oauthService;

  HhApiService(String clientId, String clientSecret, String redirectUri) {
    oauthService = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback(redirectUri)
            .build(HHApi.instance());
  }

  OAuth20Service getOauthService() {
    return oauthService;
  }

  String getAuthorizationUrl() {
    return oauthService.getAuthorizationUrl();
  }

  HhUserInfoDto getUserInfo(String code) throws InterruptedException,
          ExecutionException, IOException {
    OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
    String userInfoJson = requestUserInfo(accessToken);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode tree = mapper.readTree(userInfoJson);

    return mapper.treeToValue(tree, HhUserInfoDto.class);
  }

  private String requestUserInfo(OAuth2AccessToken accessToken) throws InterruptedException,
          ExecutionException, IOException {
    final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    oauthService.signRequest(accessToken, oauthRequest);
    final Response response = oauthService.execute(oauthRequest);
    // todo: response code not equals 200.
    return response.getCode() == 200 ? response.getBody() : null;
  }


}
