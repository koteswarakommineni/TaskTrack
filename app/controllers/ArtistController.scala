package controllers

import javax.inject.Inject
import play.api.mvc._
import models.Artist

class ArtistController @Inject() extends Controller {

  def listArtist = Action {
    Ok(views.html.home(Artist.fetch))
  }

  def fetchByName(name: String) = Action {
    Ok(views.html.home(Artist.fetchByName(name)))
  }

  private def actionResult[T <: Artist](result: Seq[T]) = {
    if (result.isEmpty)
      NoContent
    else
      Ok(views.html.home(result))
  }

  def search(name: String, country: String) = Action {
    val result = Artist.fetchByNameOrCountry(name, country)
    actionResult(result)
  }

  def search2(name: Option[String], country: String) = Action {
    val result = name match {
      case Some(n) =>  Artist.fetchByNameOrCountry(n, country)
      case None => Artist.fetchByCountry(country)
    }
    actionResult(result)
  }
}
