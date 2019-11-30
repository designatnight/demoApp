package demoApp.service;

import demoApp.dao.DemoObjectRepository;
import demoApp.domain.DemoObject;
import demoApp.exception.DemoObjectNotFoundException;
import demoApp.exception.NonUniqueNameException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DemoObjectServiceTest {

    private static final String NAME_1 = "testName1";
    private static final String NAME_2 = "testName2";
    private static final Date DATE = new Date();
    private static final String ADDRESS = "test address";
    private  UUID id1 = UUID.randomUUID();
    private UUID id2 = UUID.randomUUID();


    private DemoObjectService demoObjectService;
    private DemoObjectRepository mockRepository;
    private DemoObject testDemoObject1;
    private DemoObject testDemoObject2;
    private List<DemoObject> testDemoObjects;

    @Before
    public void doSetup(){

        mockRepository = mock(DemoObjectRepository.class);

        testDemoObject1 = createTestObject(NAME_1);
        testDemoObject2 = createTestObject(NAME_2);
        testDemoObjects = new ArrayList<>();



        when(mockRepository.save(any())).thenReturn(testDemoObject1);
        when(mockRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(testDemoObject1));
        testDemoObjects.add(testDemoObject1);
        testDemoObjects.add(testDemoObject2);
        when(mockRepository.findAll()).thenReturn(testDemoObjects);
        demoObjectService = new DemoObjectService(mockRepository);
    }


    @Test
    public void createDemoObjectTest(){
        DemoObject responseObj = demoObjectService.createDemoObject(createTestObject(NAME_1));
        assertEquals(responseObj, testDemoObject1);
        verify(mockRepository, times(1)).save(any());
    }

    @Test(expected = NonUniqueNameException.class)
    public void createDemoObjectNonUniqueName(){
        when(mockRepository.save(any())).thenThrow(new DataIntegrityViolationException("test error"));
        demoObjectService.createDemoObject(createTestObject(NAME_1));
        verify(mockRepository, times(1)).save(any());

    }

    @Test
    public void getDemoObjectByIdTest(){
        DemoObject responseObj = demoObjectService.getDemoObjectById(id1);
        assertEquals(responseObj, testDemoObject1);
        verify(mockRepository, times(1)).findById(any());

    }

    @Test(expected = DemoObjectNotFoundException.class)
    public void getDemoObjectByIdNotFoundTest(){
        when(mockRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));

        DemoObject responseObj = demoObjectService.getDemoObjectById(id1);
        assertEquals(responseObj, testDemoObject1);
        verify(mockRepository, times(1)).findById(any());

    }

    @Test
    public void getAllDemoObjectsTest() {
        List<DemoObject> response = demoObjectService.getAllDemoObjects();
        assertEquals(2, response.size());
        assertEquals(testDemoObject1, response.get(0));
        assertEquals(testDemoObject2, response.get(1));
        verify(mockRepository, times(1)).findAll();

    }

    @Test
    public void updateDemoObjectTest() {
        DemoObject responseObj = demoObjectService.updateDemoObject(createTestObject(NAME_1));
        assertEquals(responseObj, testDemoObject1);
        verify(mockRepository, times(1)).save(any());

    }

    @Test
    public void deleteDemoObjectTest() {
        demoObjectService.deleteDemoObject(id1);
        verify(mockRepository, times(1)).deleteById(any());
    }

    private DemoObject createTestObject(String name){
        DemoObject demoObject = new DemoObject();
        demoObject.setName(name);
        demoObject.setAddress(ADDRESS);
        demoObject.setDate(DATE);
        return demoObject;
    }

    private void saveDemoObjectToRepository() {

    }
}