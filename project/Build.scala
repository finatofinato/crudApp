import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "crud-application"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    	// Add your project dependencies here,
    	jdbc,
    	anorm,
	"postgresql" % "postgresql" % "8.4-702.jdbc4",
	"com.typesafe.slick" %% "slick" % "1.0.1",
    "com.h2database" % "h2" % "1.3.166"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
