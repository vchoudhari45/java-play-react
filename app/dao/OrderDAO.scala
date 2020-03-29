package dao

import java.util.concurrent.CompletionStage

import javax.inject.{Inject, Singleton}
import models.Order
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.compat.java8.FutureConverters._

@Singleton
class OrderDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  /**
   * Here we define the table. It will have a name of people
   */
  private class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {

    /** The ID column, which is the primary key, and auto incremented */
    def orderId = column[Long]("orderId", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def customerName = column[String]("customerName")

    /** The location columns */
    def longitude = column[Double]("longitude")
    def latitude = column[Double]("latitude")

    def * = (orderId, customerName, longitude, latitude) <> ((Order.apply _).tupled, Order.unapply)
  }

  private val orderTable = TableQuery[OrderTable]

  def list(): CompletionStage[Seq[Order]] = db.run {
    orderTable.result
  }.toJava

  def create(order: Order): CompletionStage[java.lang.Boolean] = {
    db.run(orderTable += order).map(_ => java.lang.Boolean.TRUE).recover {
      case ex: Exception => {
        println("Failed: "+ex.getMessage);
        java.lang.Boolean.FALSE
      }
    }.toJava
  }

}
