package simulations.authors.testdata

import com.github.javafaker.Faker

case class TestDataGenerator() {
  val faker = new Faker()
  val authorId = faker.number().numberBetween(1, 100)
  val bookId = faker.number().numberBetween(1, 50)
  val firstName = faker.name().firstName()
  val lastName = faker.name().lastName()

}
