package ru.vtarasov.java.student.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.vtarasov.java.student.ServerRunner;

/**
 * @author vtarasov
 * @since 27.09.2019
 */
public class StudentHandlerTest {
    private static final String URL = "http://localhost:8080";
    private static final HttpClientBuilder BUILDER = HttpClientBuilder.create();
    private static final ObjectMapper JSON = new ObjectMapper();

    private static Student notRegisteredStudent;
    private static Student registeredStudent;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ServerRunner.run(new StudentServer());

        notRegisteredStudent = Student.of("Student", 16);
        registeredStudent = registerStudentAndReturn(notRegisteredStudent);
    }

    private static void runServer() throws Exception {
        new Thread(() -> new StudentServer().run()).start();
        Thread.sleep(1000);
    }

    @Test
    public void shouldNotFoundStudentIfNotRegistered() throws Exception {
        HttpGet request = new HttpGet(URL + "/student/id-not-registered");
        HttpResponse response = BUILDER.build().execute(request);

        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void shouldFoundStudentIfRegistered() throws Exception {
        HttpGet request = new HttpGet(URL + "/student/" + registeredStudent.getId());
        HttpResponse response = BUILDER.build().execute(request);

        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_OK));
        Assert.assertThat(headerValue(response, HttpHeaders.CONTENT_TYPE), Matchers.equalTo("application/json"));

        Student registered = JSON.readValue(response.getEntity().getContent(), Student.class);

        Assert.assertThat(registered, Matchers.equalTo(registeredStudent));
    }

    /*@Test
    public void shouldReturnBadRequestWhenTryingToRegisterStudentWithNonNullId() throws Exception {
        HttpResponse response = registerStudentAndReturnResponse(registeredStudent);
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void shouldReturnBadRequestWhenTryingToRegisterStudentWithEmptyNullOrNotPresentedName() throws Exception {
        Student emptyNameStudent = Student.of("", 16);
        Student nullNameStudent = Student.of(null, 16);

        HttpResponse response = registerStudentAndReturnResponse(emptyNameStudent);
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));

        response = registerStudentAndReturnResponse(nullNameStudent);
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));

        response = registerStudentAndReturnResponse("{\"age\": 16}");
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void shouldReturnBadRequestWhenTryingToRegisterStudentWithNullLessThanSixteenOrNotPresentedAge() throws Exception {
        Student nullAgeStudent = Student.of("Student", null);
        Student lessThanSixteenAgeStudent = Student.of("Student", 15);

        HttpResponse response = registerStudentAndReturnResponse(nullAgeStudent);
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));

        response = registerStudentAndReturnResponse(lessThanSixteenAgeStudent);
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));

        response = registerStudentAndReturnResponse("{\"name\": \"Student\"}");
        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }*/

    @Test
    public void shouldUnregisterStudentIfRegistered() throws Exception {
        Student forRemoving = registerStudentAndReturn(Student.of("Vova", 16));
        HttpDelete delete = new HttpDelete(URL + "/student/" + forRemoving.getId());
        HttpResponse response = BUILDER.build().execute(delete);

        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotFoundStudentWhenUnregisteringOfNotRegistered() throws Exception {
        HttpDelete delete = new HttpDelete(URL + "/student/id-not-registered");
        HttpResponse response = BUILDER.build().execute(delete);

        Assert.assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_NOT_FOUND));
    }

    /*private static HttpResponse registerStudentAndReturnResponse(Student student) throws Exception {
        return registerStudentAndReturnResponse(JSON.writeValueAsString(student));
    }*/

    private static HttpResponse registerStudentAndReturnResponse(String studentJson) throws Exception {
        HttpPost post = new HttpPost(URL + "/student");
        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        post.setEntity(new StringEntity(studentJson, ContentType.APPLICATION_JSON));

        return BUILDER.build().execute(post);
    }

    private static Student registerStudentAndReturn(Student student) throws Exception {
        HttpResponse response = registerStudentAndReturnResponse(JSON.writeValueAsString(student));

        String location = headerValue(response, HttpHeaders.LOCATION);
        String id = location.substring(location.lastIndexOf("/") + 1);

        return Student.of(student.getName(), student.getAge()).toBuilder().id(id).build();
    }

    private static String headerValue(HttpResponse response, String headerName) {
        return response.getHeaders(headerName)[0].getValue();
    }
}
