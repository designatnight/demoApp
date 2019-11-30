package demoApp.controller;


import demoApp.domain.DemoObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoObjectControllerIntegreationTest {
    private URL baseUrl;
    private static String NAME_1 = "testName1";
    private static Date DATE = new Date();
    private static String ADDRESS = "test address";
    private static String ADDRESS2 = "test address2";

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testTemplate;

    @Before
    public void setup() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/demo");
    }

    @Test
    public void crudTestForDemoObject() {
        ResponseEntity<DemoObject> createRes = testTemplate.postForEntity(baseUrl.toString(), createTestObject(), DemoObject.class);
        DemoObject createObject = createRes.getBody();
        assertThat(createRes.getStatusCode(), equalTo(HttpStatus.OK));

        createObject.setAddress(ADDRESS2);
        ResponseEntity<DemoObject> updateRes = testTemplate.postForEntity(baseUrl.toString(), createObject, DemoObject.class);
        DemoObject updateObject = updateRes.getBody();
        assertThat(updateRes.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(ADDRESS2, equalTo(updateObject.address));

        ResponseEntity<DemoObject> readRes = testTemplate.getForEntity(baseUrl.toString() + "/" + createObject.getId().toString(), DemoObject.class);
        DemoObject readObject = readRes.getBody();
        assertThat(readRes.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(ADDRESS2, equalTo(readObject.address));

        testTemplate.delete(baseUrl.toString() + "/" + readObject.getId().toString());
        ResponseEntity<DemoObject> readRes2 = testTemplate.getForEntity(baseUrl.toString() + "/" + readObject.getId().toString(), DemoObject.class);
        assertThat(readRes2.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void uniqueNameRequired() {
        ResponseEntity<DemoObject> createRes = testTemplate.postForEntity(baseUrl.toString(), createTestObject(), DemoObject.class);
        DemoObject createObject = createRes.getBody();
        assertThat(createRes.getStatusCode(), equalTo(HttpStatus.OK));

        ResponseEntity<DemoObject> createRes2 = testTemplate.postForEntity(baseUrl.toString(), createTestObject(), DemoObject.class);
        assertThat(createRes2.getStatusCode(), equalTo(HttpStatus.CONFLICT));

        //clean up teat to avoid conflicts with other tests
        testTemplate.delete(baseUrl.toString() + "/" + createObject.getId().toString());
    }

    private DemoObject createTestObject(){
        DemoObject demoObject = new DemoObject();
        demoObject.setName(NAME_1);
        demoObject.setAddress(ADDRESS);
        demoObject.setDate(DATE);
        return demoObject;
    }
}
