package config

import com.github.javafaker.Faker
import com.typesafe.config.{Config, ConfigFactory}

case class ConfigFactoryManager() {

  val config: Config = ConfigFactory.load("gatling.conf")
  val baseUrl: String = config.getString("gatling.baseUrls.test") // Change 'dev' to your desired environment
  val getAllAuthors: String = config.getString("gatling.endpoints.getAllAuthors")
  val createAuthor: String = config.getString("gatling.endpoints.createAuthor")
  val getBooksUsingId: String = config.getString("gatling.endpoints.getBooksUsingId")
  val getAuthorUsingId: String = config.getString("gatling.endpoints.getAuthorUsingId")
  val updateAuthorUsingId: String = config.getString("gatling.endpoints.updateAuthorUsingId")
  val deleteAuthorUsingId: String = config.getString("gatling.endpoints.deleteAuthorUsingId")
  val minimumTimeToThink = 1
  val maximumTimeToThink = 5
  val createAuthorJSONPath = "/Users/vedantbajpai/Desktop/codebase/mindtickle-performance/src/test/scala/simulations/authors/testdata/request_json/authors-create.json"
  val updateAuthorJSONPath = "/Users/vedantbajpai/Desktop/codebase/mindtickle-performance/src/test/scala/simulations/authors/testdata/request_json/authors-put.json"

}
