package demoApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demoApp.domain.DemoObject;
import demoApp.exception.DemoObjectNotFoundException;
import demoApp.exception.NonUniqueNameException;
import demoApp.service.DemoObjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DemoObjectControllerTest {

    private static String NAME_1 = "testName1";
    private static String NAME_2 = "testName2";
    private static Date DATE = new Date();
    private static String ADDRESS = "test address";
    private static UUID VALID_ID = UUID.randomUUID();
    private static UUID INVALID_ID = UUID.randomUUID();
    private static final String baseUrl = "/demo";


    private DemoObjectService mockService =  mock(DemoObjectService.class);
    private DemoObject testObject;
    private ObjectMapper objectMapper;

    private DemoObjectController demoObjectController;


    @Before
    public void doSetup(){
        objectMapper = new ObjectMapper();
        testObject =  createTestObject();
        testObject.setId(VALID_ID);

        when(mockService.createDemoObject(any())).thenReturn(testObject);
        when(mockService.updateDemoObject(any())).thenReturn(testObject);
        when(mockService.getDemoObjectById(eq(VALID_ID))).thenReturn(testObject);
        when(mockService.getAllDemoObjects()).thenReturn(Arrays.asList(testObject));
        when(mockService.getDemoObjectById(eq(INVALID_ID))).thenThrow(new DemoObjectNotFoundException("Object not found"));

        demoObjectController = new DemoObjectController((mockService));
    }

    @Test
    public void createDemoObject() {
        DemoObject obj = demoObjectController.createDemoObject(testObject);
        assertThat(testObject, equalTo(obj));
    }

    @Test(expected = NonUniqueNameException.class)
    public void createDemoObjectNotUniqueName() {
        when(mockService.createDemoObject(any())).thenThrow(NonUniqueNameException.class);
        demoObjectController.createDemoObject(testObject);
    }

    @Test
    public void getDemoObjectById() {
        DemoObject obj = demoObjectController.getDemoObject(VALID_ID);
        assertThat(testObject, equalTo(obj));
    }

    @Test(expected = DemoObjectNotFoundException.class)
    public void getDemoObjectByIdNotFound() {
        demoObjectController.getDemoObject(INVALID_ID);
    }

    @Test
    public void getAllDemoObjects() {
        List<DemoObject> obj = demoObjectController.getAllDemoObjects();
        assertThat(testObject, equalTo(obj.get(0)));
    }

    @Test
    public void updateDemoObject() {
        DemoObject obj = demoObjectController.updateDemoObject(testObject);
        assertThat(testObject, equalTo(obj));
    }

    @Test
    public void deleteDemoObject() {
        demoObjectController.deleteDemoObject(VALID_ID);
    }

    private DemoObject createTestObject(){
        DemoObject demoObject = new DemoObject();
        demoObject.setName(NAME_1);
        demoObject.setAddress(ADDRESS);
        demoObject.setDate(DATE);
        return demoObject;
    }
}