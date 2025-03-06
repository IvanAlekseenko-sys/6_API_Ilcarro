package ilcarro_tests.httpClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationHttpClientTests {
    private static final String REGISTRATION_URL = "https://ilcarro-backend.herokuapp.com/v1/user/registration/usernamepassword";

    @Test
    public void RegistrationStatusCodeTest() throws IOException {
        String uniqueUsername = "user_" + System.currentTimeMillis() + "@qa.com";
        String jsonBody = """
                {
                    "username": "%s",
                    "password": "Password@1",
                    "firstName": "Vasiliy",
                    "lastName": "Vasin"
                }
                """.formatted(uniqueUsername);

        Response response = Request.Post(REGISTRATION_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        int statusCode = response.returnResponse().getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "Статус код отличается от 200");
    }

    @Test
    public void RegistrationResponseFieldsTest() throws IOException {
        String uniqueUsername = "user_" + System.currentTimeMillis() + "@qa.com";
        String jsonBody = """
                {
                    "username": "%s",
                    "password": "Password@1",
                    "firstName": "Vasiliy",
                    "lastName": "Vasin"
                }
                """.formatted(uniqueUsername);

        Response response = Request.Post(REGISTRATION_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        String responseJson = response.returnContent().asString();
        JsonElement element = JsonParser.parseString(responseJson);

        Assert.assertTrue(element.getAsJsonObject().has("accessToken"), "Поле accessToken отсутствует в ответе");

        String token = element.getAsJsonObject().get("accessToken").getAsString();
        Assert.assertNotNull(token, "Значение accessToken null");
        Assert.assertFalse(token.trim().isEmpty(), "Значение accessToken пустое");
    }
}