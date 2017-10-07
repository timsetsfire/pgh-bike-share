import java.io.File
import java.io.{File,PrintWriter}
import scala.util.matching.Regex
import scala.util.{Try, Success, Failure}
import java.text.SimpleDateFormat
import java.sql.Timestamp
import scala.util.{Try, Success, Failure}

object DataPrep extends App {

  val rentalRegex = new Regex("""rental""")
  val parser = new SimpleDateFormat("MM/dd/yyyy hh:mm")

  case class Vertex(
    stationNum: Int,
    stationName: String,
    numOfRacks: Int,
    latitude: Double,
    longitude: Double) {
      override def toString = {
        s"$stationNum,$stationName,$numOfRacks,$latitude,$longitude"
      }
    }
  case class Edge(
    tripId: Long,
    startDate: java.sql.Date,
    startTime: java.sql.Timestamp,
    stopDate: java.sql.Date,
    stopTime: java.sql.Timestamp,
    bikeId: Long,
    tripDuration: Long,
    fromStationId: Int,
    fromStationName: String,
    toStationId: Int,
    toStationName: String) {
      override def toString = s"$fromStationId,$tripId,$bikeId,${startTime.getTime},${stopTime.getTime},$toStationId"
    }


  // val outVertices = new PrintWriter("data/processed/vertices.csv")
  // outVertices.write("stationId:ID(Station),stationName,numberOfRacks:Int,Latitude:Double,Longitude:Double\n")
  // val outEdges = new PrintWriter("data/processed/edges-trips.csv")
  // outEdges.write("tripId:ID,fromStationId:START,bikeId,startTime:long,stopTime:long,toStationId:END\n")

  // process vertices
  val vertexSrc = scala.io.Source.fromFile("../data/2017-Q2/HealthyRideStations2017.csv")
  val vertexData = vertexSrc.getLines
  vertexData.next
  val vertices = vertexData.map{ _.split(",")}.map{
    li =>
      Vertex(
        li(0).toInt,
        li(1),
        li(2).toInt,
        li(3).toDouble,
        li(4).toDouble)
  }.toList
  vertexSrc.close
  val degrees = scala.collection.mutable.Map(vertices.map{ station => (station.stationNum,0)}:_*)

  // process edges - trips
  val edgeFiles = new File("../data").listFiles.flatMap{
     _.listFiles
   }.filter{
     file =>
     rentalRegex.findFirstIn(
       file.toString.toLowerCase
     ).isDefined
   }.map{_.toString}.map{_.toString}

  val edgesStream = edgeFiles.map{ file =>
    val data = scala.io.Source.fromFile(file)("ISO-8859-1").getLines.map{ _.split(",")}
    data.next
    data
  }.foldLeft{
    Iterator[Array[String]]()
  }{
    (x,y) => x ++ y
  }
  val edges = edgesStream.map{ record =>
    val tripId = record(0).toLong
    val start = parser.parse(record(1)).getTime
    val stop = parser.parse(record(2)).getTime
    val startDate = new java.sql.Date( start )
    val startTime = new java.sql.Timestamp( start )
    val stopDate = new java.sql.Date( stop )
    val stopTime = new java.sql.Timestamp( stop )
    val bikeId = record(3).toLong
    val tripDuration = record(4).toLong
    val fromStationId = record(5).toInt
    val fromStationName = record(6)
    val toStationId = record(7).toInt
    val toStationName = record(8)
    Edge(tripId,startDate,startTime,stopDate,stopTime,bikeId,tripDuration,fromStationId,fromStationName,toStationId,toStationName)
  }

  val adjacencySet = scala.collection.mutable.Map[(Int,Int),Int]()

  val out = new PrintWriter("../data/processed/edges.csv")
  out.write(":START_ID(Station),tripId,bikeId,startTime,stopTime,:END_ID(Station)\n")
  edges.foreach{
    edge =>
      val start = edge.fromStationId
      val end = edge.toStationId
      val trip = (start,end)
      if(degrees.contains(start) & degrees.contains(end)) {
        degrees.update( start, degrees.getOrElse(start,0) + 1)
        adjacencySet.update( trip, adjacencySet.getOrElse(trip,0) + 1)
        out.write(s"$edge\n")
      }
  }
  out.close

  val outVertices = new PrintWriter("../data/processed/vertices.csv")
  outVertices.write("stationId:ID(Station),stationName,numRacks:Int,latitude:Double,longitude:Double\n")
  vertices.foreach( vertex => outVertices.write(s"$vertex\n"))
  outVertices.close

}
