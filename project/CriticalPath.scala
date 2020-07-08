import java.nio.file.Files

import sbt._
import sbt.Keys._
import sbt.internal.inc.Analysis

import scala.meta.internal.{semanticdb => s}
import scala.collection.JavaConverters._

object CriticalPath extends AutoPlugin {

  object autoImport {
    val unusedProjectDependencies = taskKey[Set[String]]("todi")

  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  override def requires: Plugins = empty

  override def projectSettings: Seq[Def.Setting[_]] = super.projectSettings ++ Seq(
    unusedProjectDependencies := unusedProjectDependenciesTask.value
  )

  lazy val unusedProjectDependenciesTask = Def.task {
    val analysis: Analysis = compile.in(Compile).value.asInstanceOf[Analysis]
    val logger = streams.value.log
    val project = thisProjectRef.value

    val files = analysis.readSourceInfos().getAllSourceInfos.asScala.map {
      case (file, _) =>
        file.getAbsolutePath
    }

    val classes = analysis.apis.allInternalClasses

    /*val whatev =
      classes.map { thisClass =>
        val api = analysis.apis.internalAPI(thisClass)
        logger.info(
          s"""
             |${api.name()}
             |${api.api().classApi()}
             |""".stripMargin
        )

      }*/

    Files.walk(
      semanticdbTargetRoot.in(Compile).value.toPath
    )
      .iterator()
      .asScala
      .filter(_.getFileName.toString.endsWith(".semanticdb"))
      .toList
      .map { db =>
        for {
          document <- s.TextDocuments
            .parseFrom(Files.newInputStream(db))
            .documents
        } {
          logger.info(document.uri)
          logger.info(document.occurrences.mkString("\n"))
        }
      }
    //s.TextDocuments.parseFrom()
    /*logger.info(
      s"""
         |=================================
         |In project ${project.project}
         |
         |Files
         |${files.mkString("\n")}
         |
         |Classes
         |${classes.mkString("\n")}
         |
         |${whatev.mkString("\n")}
         |=================================
         |
         |
         |""".stripMargin)*/

    Set(" ")
  }
}
