package ilcarro_tests.httpClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationHttpClientTests {
    private static final String REGISTRATION_URL = "https://ilcarro-backend.herokuapp.com/v1/user/registration/usernamepassword";
    private static final Gson GSON = new Gson();

    @Test
    public void RegistrationSuccessTest() throws IOException {
        String uniqueUsername = "user_" + System.currentTimeMillis() + "@qa.com";

        String jsonBody = """
                {
                    "username": "%s",
                    "password": "Password@1",
                    "firstName": "Test",
                    "lastName": "User"
                }
                """.formatted(uniqueUsername);

        // Получаем HttpResponse один раз
        HttpResponse httpResponse = Request.Post(REGISTRATION_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();

        // Проверка, что статус код равен 200
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "Статус код не равен 200");

        // Считываем тело ответа в виде строки
        String responseJson = EntityUtils.toString(httpResponse.getEntity());
        JsonElement element = JsonParser.parseString(responseJson);
        JsonElement accessToken = element.getAsJsonObject().get("accessToken");

        // Проверка наличия токена и его значения
        Assert.assertNotNull(accessToken, "Токен отсутствует в ответе");
        Assert.assertFalse(accessToken.getAsString().isEmpty(), "Значение токена пустое");
    }
}