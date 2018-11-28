package scalacask.engine.twitter

import java.lang

import scalacask.api.{ScalaCask, ScalaCaskAPI}

class ScalaCaskTwitterAPI extends TwitterAPI {

  val sc: ScalaCask = new ScalaCaskAPI()
  /**
    * Posts tweet to database
    */
  override def postTweet(t: Tweet): Unit = {
    sc.addToList("user_tweets:" + t.getUserID + "", t.getTweet)
    sc.addToList("user_time_line:" + t.getUserID + "", t.getTweet)
  }

  /**
    * Adds a follower to a user
    *
    * @param user_id     the user being followed
    * @param follower_id the follower's id
    */
  override def addFollower(user_id: String, follower_id: String): Unit = {
    sc.addToList("user_followers:" + user_id, "" + follower_id)
    sc.addToList("user_followees:" + follower_id, "" + user_id)
  }

  /**
    * Retrieves the tweets of a user's timeline based on their followings
    *
    * @param user_id the user
    * @param posts   the number of posts to retrieve
    */
  override def getTimeline(user_id: String, posts: Int): Unit = {
    var i=0
    for(followee <- getFollowees(user_id)){
      getTweets(followee)
      i+=1
    }
    println(i + " tweets retrieved.")
  }

  /**
    * Retrieves the user's followers
    *
    * @param user_id the user
    */
  def getFollowers(user_id: Long): List[String] = {
    sc.getList("user_followers:" + user_id)
  }

  /**
    * Retrieves who the user is following
    *
    * @param user_id the user
    */
  def getFollowees(user_id: String): List[String] = {
    sc.getList("user_followees:" + user_id)
  }

  /**
    * Retrieves the tweets posted by the user
    *
    * @param user_id the user
    * @param posts   the number of posts to retrieve
    * @return
    */
  def getTweets(user_id: String): List[String] = {
    sc.getList("user_tweets:" + user_id)
  }

  /**
    * Clears the content of the database
    */
  override def reset(): Unit = {
    sc.nuke
  }
}
