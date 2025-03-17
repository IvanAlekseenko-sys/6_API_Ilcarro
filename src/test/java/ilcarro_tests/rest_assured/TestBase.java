package ilcarro_tests.rest_assured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestBase{

    public final String loginDto = "user/login/usernamepassword";

    @BeforeMethod
    public void init(){
        RestAssured.baseURI="https://ilcarro-backend.herokuapp.com";
        RestAssured.basePath = "v1";

    }
}
