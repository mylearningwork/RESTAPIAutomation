/**
 * 
 */
package com.testPages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.meta.When;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given; // user can directly use given() if imported like this. 

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

import utilities.TestBase;

import static com.jayway.restassured.RestAssured.get;

/**
 * @author Alok
 *
 */

@SuppressWarnings("unused")
public class JsonPlaceHolderAPIex extends TestBase {

	/*
	 * @BeforeClass public void setUp() throws Exception { RestAssured.port = 8081;
	 * }
	 */

	@Test(enabled = true)
	public void photos() {

		Response resp = given().get("https://jsonplaceholder.typicode.com/photos");
		String respAsString = RestAssured.given().get("https://jsonplaceholder.typicode.com/photos").then().extract()
				.asString();
		// System.out.println(resp.asString());
		// System.out.println("Headers are"+resp.getHeaders());
		// System.out.println(resp.getHeader("Content-Type"));
		// System.out.println("Body is "+resp.getBody());
		// System.out.println("content type is :"+resp.getContentType());
		// resp.then().contentType(ContentType.JSON).header("Content-Encoding",
		// "gzip").statusCode(200);
		// resp.then().assertThat().statusCode(200).log().all();
		// .log().all();
		// System.out.println(resp);

		RestAssured.given().get("https://jsonplaceholder.typicode.com/photos").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON).assertThat().statusCode(200).cookie("__cfduid")
				.header("Content-Encoding", "gzip").log().ifValidationFails();// logs only if validation fails.
		// https://stackoverflow.com/questions/43699258/restassured-cannot-master-post-method

		// System.out.println(resp.getHeaders());

		System.out.println(resp.getHeader("Content-Encoding").equals("gzip"));

		// to print all headers one by one.
		Headers headers = resp.getHeaders();

		for (Header h : headers) {

			if (h.getName().equals("Pragma")) {
				System.out.println(h.getName() + "....... " + h.getValue());
				System.out.println(" header exists ");
			}
		}

	}

	@Test(enabled = true)

	public void getCookies() {

		Response resp = RestAssured.get("https://jsonplaceholder.typicode.com/photos");
		Map<String, String> cookies = resp.getCookies();
		cookies.toString();
		// System.out.println(cookies);
		// print all cookies
		for (Entry<String, String> c : cookies.entrySet()) {

			// System.out.println(c.getKey());
			// System.out.println(c.getValue());
		}
		// System.out.println(resp.getDetailedCookies());
		System.out.println(resp.getDetailedCookie("__cfduid"));
		System.out.println(resp.getDetailedCookie("__cfduid").hasExpiryDate());// cookie related methods to test them.
		System.out.println("Expiry date of cookie is : " + resp.getDetailedCookie("__cfduid").getExpiryDate()
				+ resp.getDetailedCookie("__cfduid").getDomain() + resp.getDetailedCookie("__cfduid").getName());

		resp.getDetailedCookie("__cfduid").getVersion();
	}

	@Test(enabled = false)

	public void testQueryParam() {

		given().when().request();

	}

	// cdata? par n convert to xml . get set params in cdata req resp.
	@Test(enabled = false)
	public void logtypes() {

		/*
		 * Log all requests: given().log().all() Log all responses: `when`().log().all()
		 * Or just when validations fail: `when`().log().ifValidationFails()
		 */
		given().log().all().get("https://jsonplaceholder.typicode.com/photos");
		given().get("https://jsonplaceholder.typicode.com/photos").then().log().ifStatusCodeIsEqualTo(200);

	}

	@Test(enabled = true)
	public void testPathParam() {
		// https://www.youtube.com/watch?v=4haz0QRTYXw&index=6&list=PLEiBaBxmVLi-hoi61aX-2agQb8EXSCT5f
		String strUser = "users";
		int intI = 1;

		given().log().all().pathParam("strUser", strUser).pathParam("id", intI)
				.get("https://jsonplaceholder.typicode.com/{strUser}/{id}").then().log().all();

	}

	@Test(enabled = true)
	public void setCookie() {

		given().when().cookie("CookieName", "cookieAlok").get("http://www.webservicex.net/price/MSNUM");

		given().when().log().all().cookie("newCookie", "value1", "value2").get("http://www.webservicex.net/price/MSNUM")
				.then().statusCode(200);

		// given().when().cookies(cookies)

	}

	@Test(enabled = false)
	public void testBodyParamsInResponse() {

		// with java 8 lambda expression. not working properly.
		RestAssured.given().get("http://jsonplaceholder.typicode.com/photos/1").then().log().all();

		/*
		 * RestAssured.given().get("http://jsonplaceholder.typicode.com/photos/1").then(
		 * ).body("thumbnailUrl", Response ->
		 * equalsTo("https://via.placeholder.com/150/92c952") );
		 */
		// given().get("http://jsonplaceholder.typicode.com/photos/1").then().body("thumbnailUrl",is(true));

	}

	// http://toolsqa.com/rest-assured/read-response-body-using-rest-assured/
	@Test(enabled = true)
	public void jsonPathEx() {

		Response resp = given().get("http://jsonplaceholder.typicode.com/photos");

		resp.then().log().all();
		JsonPath jpath = resp.jsonPath();// used JSON.to fetch value of keys using

		System.out.println("Title is : " + jpath.get("title"));
		System.out.println("url is : " + jpath.get("url"));

	}

	@Test(enabled = true)
	public void testPathParameter() {

		String photosFolder = "photos";
		String ids = "22";

		Response resp = given().pathParam("photos", photosFolder).pathParam("id", ids)
				.get("http://jsonplaceholder.typicode.com/{photos}/{id}");

		given().log().all().pathParam("param1", photosFolder).pathParam("param2", ids)
				.get("http://jsonplaceholder.typicode.com/{param1}/{param2}").then().log().all();

		resp.then().log().all();
		resp.jsonPath().get("title");

		System.out.println(resp.jsonPath().get("title").toString());

	}

	@Test(enabled = true)
	public void testJsonPath() {

		// http://toolsqa.com/rest-assured/what-is-jsonpath-and-how-to-query-jsonpath/
		// url used is http://jsonplaceholder.typicode.com/users
		String resp = given().get("http://jsonplaceholder.typicode.com/users").jsonPath().get("address.geo.lat")
				.toString();

		String resp1 = given().get("http://jsonplaceholder.typicode.com/users").jsonPath().get("address.geo.lat[0]")
				.toString();
		String resplast = given().get("http://jsonplaceholder.typicode.com/users").jsonPath().get("address.geo.lat[-1]")
				.toString();// -1 will bring last element

		String respwildcard = given().get("http://jsonplaceholder.typicode.com/users").jsonPath().get("address[1]")
				.toString();

		String respMultipleEle = given().get("http://jsonplaceholder.typicode.com/users").jsonPath()
				.get("address.geo.lat[0,1]").toString();// will bring 2 elements.

		System.out.println("All latitudes are " + resp);
		System.out.println("Latitude at position 0 is : " + resp1);
		System.out.println("last latitude is : " + resplast);
		System.out.println("Resp wild card : " + respwildcard);
		System.out.println("Multiple Elements : " + respMultipleEle);
		given().get("http://jsonplaceholder.typicode.com/users").jsonPath().getList("address.geo.lat");
	}

	@Test(enabled = false)
	public void testJsonPathExpressions() {

		// given().get("http://jsonplaceholder.typicode.com/users").then().log().all();
		Response resp = given().get("http://jsonplaceholder.typicode.com/users");
		// $.books[?(@.pages > 460)]
		System.out.println(resp.jsonPath().get("address[?(@.zipcode > 9)]").toString()); // not working
		// [?(@.city==McKenziehaven)]
	}

	@Test(enabled = true)

	public void testPracticeEx() {

		// Response resp=
		// given().then().log().all().get("http://jsonplaceholder.typicode.com/users");
		System.out.println(getClass().getSimpleName());
		Response resp = given().get("http://jsonplaceholder.typicode.com/users");

		// resp.then().log().all();

		System.out.println("Content Type is  " + resp.getContentType());
		// System.out.println("Resp as string is "+resp.asString());
		// System.out.println("body is .."+resp.getBody().asString());

		System.out.println("Headers are..." + resp.getHeaders());

		System.out.println(" selected header is.." + resp.getHeader("Via"));

		System.out.println(resp.header("via"));

		System.out.println(resp.getSessionId());
		System.out.println(resp.getStatusLine());

		String header = resp.getHeader("Connection");
		resp.then().assertThat().contentType(ContentType.JSON);
		System.out.println(resp.then().assertThat().contentType(ContentType.JSON));
		
		//System.out.println(resp.jsonPath().prettyPeek().get("id[1]").toString());
		
		System.out.println(resp.path("address[0].geo.lng").toString());
		System.out.println(resp.jsonPath().get("address[0].geo.lng").toString());
		ArrayList<Object> lng= resp.path("address.geo.lng");
		
		System.out.println(lng.get(2));
		System.out.println(lng.toString());
		System.out.println("list is ..  "+resp.jsonPath().getList("address"));
	//	System.out.println(resp.path("address.geo.lng").);
		/*
		 * System.out.println(header);
		 * 
		 * System.out.println(resp.then().contentType(ContentType.JSON));
		 * 
		 * System.out.println(resp.contentType());
		 * 
		 * System.out.println(resp.getStatusCode());
		 * 
		 * resp.then().assertThat().contentType(ContentType.JSON);
		 * 
		 * System.out.println(resp.jsonPath().get("id").toString());
		 * 
		 * System.out.println(resp.jsonPath().get("address[1]").toString());
		 * System.out.println(resp.jsonPath().get("address[1].zipcode").toString());
		 * 
		 * System.out.println(resp.jsonPath().get("address[1].geo").toString());
		 * System.out.println(resp.jsonPath().get("address[1].geo.lat").toString()); //
		 * will print lat of 1st address // entry.
		 * 
		 * System.out.println(resp.jsonPath().getList("address"));
		 * 
		 * List<Object> list = resp.jsonPath().getList("address");
		 * 
		 * System.out.println(list.get(0)); // will print 1st element.
		 */
	}

}