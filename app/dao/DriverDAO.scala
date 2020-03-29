package dao

import java.util.concurrent.CompletionStage

import javax.inject.{Inject, Singleton}
import models.{Driver, Status}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.FutureConverters._

@Singleton
class DriverDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  /**
   * Here we define the table. It will have a name of people
   */
  private class DriverTable(tag: Tag) extends Table[Driver](tag, "driver") {

    /** The ID column, which is the primary key, and auto incremented */
    def driverId = column[Long]("driverId", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def driverName = column[String]("driverName")

    /** The location columns */
    def longitude = column[Double]("longitude")
    def latitude = column[Double]("latitude")

    /** The status column */
    def status = column[String]("status")

    def * = (driverId, driverName, longitude, latitude, status) <> ((Driver.apply _).tupled, Driver.unapply)
  }

  private val driverTable = TableQuery[DriverTable]

  def list(): CompletionStage[Seq[Driver]] = db.run {
    driverTable.result
  }.toJava

  def markDispatched(driverId: Long): CompletionStage[java.lang.Integer] = db.run {
    val q = for { c <- driverTable if c.driverId === driverId } yield c.status
    val updateAction = q.update(Status.BUSY.toString)
    updateAction.map(x => new Integer(x))
  }.toJava

}
