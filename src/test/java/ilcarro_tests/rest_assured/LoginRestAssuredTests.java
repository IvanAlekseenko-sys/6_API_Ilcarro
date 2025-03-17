package ilcarro_tests.rest_assured;

import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginRestAssuredTests extends TestBase {
    SoftAssert softAssert = new SoftAssert();
    AuthRequestDto body = AuthRequestDto.builder()
            .username("test_qa@qa.com")
            .password("Password@1")
            .build();

    AuthRequestDto errorBody = AuthRequestDto.builder()
            .username("test_qa@qa.com")
            .password("Password1")
            .build();

    @Test
    public void loginSuccessTest() {
        AuthResponseDto dto = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(loginDto)
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(dto);
    }

    @Test
    public void loginSuccessTest2() {
        String responseToken = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(loginDto)
                .then()
                .assertThat().statusCode(200)
                .body(containsString("accessToken"))
                .extract().path("accessToken");
        System.out.println();
        System.out.println(responseToken);
    }

    @Test
    public void loginWrongPassword() {
        ErrorDto errorDto = given()
                .contentType(ContentType.JSON)
                .body(errorBody)
                .when()
                .post(loginDto)
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println();
        System.out.println(errorDto);

        String error = errorDto.getError();
        System.out.println(error);
        softAssert.assertEquals(error, "Unauthorized");

        int status = errorDto.getStatus();
        System.out.println(status);
        softAssert.assertEquals(status, 401);

        String message = (String) errorDto.getMessage();
        System.out.println(message);
        softAssert.assertEquals(message, "Login or Password incorrect");

        String path = errorDto.getPath();
        System.out.println(path);
        softAssert.assertEquals(path, "/v1/user/login/usernamepassword");

        softAssert.assertAll();
    }

    @Test
    public void loginWrongPasswordTest2() {
        given()
                .contentType(ContentType.JSON)
                .body(errorBody)
                .when()
                .post(loginDto)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", equalTo("Unauthorized"))
                .assertThat().body("path", equalTo("/v1/user/login/usernamepassword"))
                .assertThat().body("message", equalTo("Login or Password incorrect"))
                .assertThat().body("status", equalTo(401));

    }
}
