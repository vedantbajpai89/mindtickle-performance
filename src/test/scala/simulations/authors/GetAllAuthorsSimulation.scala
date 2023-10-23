package simulations.authors

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.ConfigFactoryManager
import simulations.authors.testdata.TestDataGenerator

import scala.concurrent.duration._
import scala.language.postfixOps

class GetAllAuthorsSimulation extends Simulation{

  val getConfig = new ConfigFactoryManager;
  val headers = Map("Content-Type" -> "application/json")

  val httpProtocol = http
    .baseUrl(getConfig.baseUrl) // Replace with your API base URL
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val authors = scenario("Gel All Authors Endpoint Tests")
    .exec(http("Get All Authors")
      .get(getConfig.getAllAuthors)
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
  ).assertions(global.failedRequests.count.is(0))

}
