package ilcarro_tests.httpClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginHttpClientTests {
    private static final String LOGIN_URL = "https://ilcarro-backend.herokuapp.com/v1/user/login/usernamepassword";
    private static final Gson GSON = new Gson();

    @Test
    public void LoginSuccessTest() throws IOException {
        String jsonBody = """
                    {
                    "username": "test_qa@qa.com",
                        "password": "Password@1"
                }""";
        Response response = Request.Post(LOGIN_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        String responseJson = response.returnContent().asString();
        System.out.println(responseJson);

        //parse JSON, чтобы извлечь из него нужные значения
        JsonElement element = JsonParser.parseString(responseJson);
        System.out.println(element);
        JsonElement accessToken = element.getAsJsonObject().get("accessToken");
        System.out.println("Extracted Access Token: " + accessToken);
        Assert.assertNotNull(accessToken,"Токен отсутствует");
    }

    @Test
    public void LoginStatusCodeTest() throws IOException {
        String jsonBody = """
                {
                    "username": "test_qa@qa.com",
                    "password": "Password@1"
                }""";
        Response response = Request.Post(LOGIN_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        int statusCode = response.returnResponse().getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "Статус код не равен 200");
    }

    @Test
    public void LoginSuccessWithDtoTest() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .username("test_qa@qa.com")
                .password("Password@1")
                .build();

        Response response = Request.Post(LOGIN_URL)
                .bodyString(GSON.toJson(requestDto), ContentType.APPLICATION_JSON)
                .execute();
        String responseJson = response.returnContent().asString();
        System.out.println(responseJson);

        AuthResponseDto authResponseDto = GSON.fromJson(responseJson, AuthResponseDto.class);
        System.out.println(authResponseDto);

        String token = authResponseDto.getAccessToken();
        System.out.println(token);
        Assert.assertNotNull(token, "Токен отсутствует");

    }

}

