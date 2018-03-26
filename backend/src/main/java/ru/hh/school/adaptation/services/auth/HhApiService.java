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
import ru.hh.nab.core.util.FileSettings;
import ru.hh.school.adaptation.dto.HhUserInfoDto;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HhApiService {
  private static final String PROTECTED_RESOURCE_URL = "https://api.hh.ru/me";
  private final OAuth20Service oauthService;

  @Inject
  HhApiService(FileSettings fileSettings) {
    FileSettings oauthSettings = fileSettings.getSubSettings("oauth");
    String clientId = oauthSettings.getString("client.id");
    String clientSecret = oauthSettings.getString("client.secret");
    String redirectUri = oauthSettings.getString("redirect-uri");

    oauthService = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback(redirectUri)
            .build(HHApi.instance());
  }

  String getAuthorizationUrl() {
    return oauthService.getAuthorizationUrl();
  }

  HhUserInfoDto getUserInfo(String code) throws InterruptedException, ExecutionException, IOException {
    OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
    String userInfoJson = requestUserInfo(accessToken);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode tree = mapper.readTree(userInfoJson);

    return mapper.treeToValue(tree, HhUserInfoDto.class);
  }

  private String requestUserInfo(OAuth2AccessToken accessToken) throws InterruptedException, ExecutionException, IOException {
    OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    oauthService.signRequest(accessToken, oauthRequest);
    Response response = oauthService.execute(oauthRequest);
    // todo: response code not equals 200.
    return response.getCode() == 200 ? response.getBody() : null;
  }


}
