package models

import salactx._
import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import com.google.common.base._

case class User(_id: ObjectId = new ObjectId,
                username: String,
                password: String,
                email: String,
                admin: Boolean = false,
                firstName: Option[String] = None,
                lastName: Option[String] = None,
                bio: Option[String] = None) {
  def fullName(firstName: Option[String], lastName: Option[String]) = {
    val fullname = List(firstName.getOrElse(""), lastName.getOrElse("")).filter(!"".equals(_)).map(_.capitalize)

    fullname.isEmpty match {
      case false => fullname.mkString(" ").trim()
      case _     => ""
    }
  }
}

object User extends SalatDAO[User, ObjectId](collection = DB.connection("User")) with Model[User] {

  def authenticate(username: String, password: String): Option[User] = {
    this.findOne(MongoDBObject("username" -> username, "username" -> username))
  }
}