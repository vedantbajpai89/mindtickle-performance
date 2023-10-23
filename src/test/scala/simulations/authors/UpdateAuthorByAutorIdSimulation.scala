package simulations.authors

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.ConfigFactoryManager
import play.api.libs.json.Json
import simulations.testdata.TestDataGenerator

import scala.concurrent.duration._
import scala.language.postfixOps

class UpdateAuthorByAutorIdSimulation extends Simulation{

  val getConfig = new ConfigFactoryManager;
  val getTestData = new TestDataGenerator;
  val headers = Map("Content-Type" -> "application/json")

  val httpProtocol = http
    .baseUrl(getConfig.baseUrl) // Replace with your API base URL
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val authors = scenario("Update Author by AuthorId endpoint Tests")
    .exec {
      session =>
        val jsonPath = getConfig.updateAuthorJSONPath
        val jsonContent = scala.io.Source.fromFile(jsonPath).mkString
        val parsedJson =  Json.parse(jsonContent)
        val modifiedJson = parsedJson.as[play.api.libs.json.JsObject]
          .deepMerge(Json.obj("id" -> getTestData.authorId, "idBook" -> getTestData.bookId, "firstName" -> getTestData.firstName, "lastName" -> getTestData.lastName))
        // Convert the modified JSON back to a string
        val modifiedJsonString = modifiedJson.toString()
        val newSession = session.set("jsonBody", modifiedJsonString)
        newSession
    }
    .exec(http("Update Author using Author Id")
      .put(getConfig.updateAuthorUsingId+"/"+getTestData.authorId)
      .headers(headers)
      .body(StringBody("${jsonBody}"))
      .asJson
      .check(status is 200))

  setUp(
    authors.inject(
      atOnceUsers(1),   // 1 user
      nothingFor(2 seconds),
      rampUsers(9).during(2 seconds),  // 10 users (total users: 1 + 9 = 10)
      nothingFor(3 seconds),
      rampUsers(15).during(5 seconds), // 25 users (total users: 10 + 15 = 25)
      nothingFor(3 seconds),
      rampUsers(25).during(10 seconds), // 50 users (total users: 25 + 25 = 50)
      nothingFor(3 seconds),
      rampUsers(50).during(20 seconds)  // 100 users (total users: 50 + 50 = 100)
    ).protocols(httpProtocol)
  )

}
