package scalacask.engine.twitter
import java.{lang, util}

import scalacask.api.{ScalaCask, ScalaCaskAPI}

class ScalaCaskTwitterAPI extends TwitterAPI {

  val sc:ScalaCask = new ScalaCaskAPI()
  /**
    * Posts tweet to database
    */
  override def postTweet(t: Tweet): Unit = {
    sc.setString("tweet:"+t.getUserID+"", t.getTweet)
  }

  /**
    * Adds a follower to a user
    *
    * @param user_id     the user being followed
    * @param follower_id the follower's id
    */
  override def addFollower(user_id: Long, follower_id: Long): Unit = {

  }

  /**
    * Retrieves the tweets of a user's timeline based on their followings
    *
    * @param user_id the user
    * @param posts   the number of posts to retrieve
    */
  override def getTimeline(user_id: Long, posts: Int): util.List[Tweet] = ???

  /**
    * Retrieves the user's followers
    *
    * @param user_id the user
    */
  override def getFollowers(user_id: Long): util.List[lang.Long] = ???

  /**
    * Retrieves who the user is following
    *
    * @param user_id the user
    */
  override def getFollowees(user_id: Long): util.List[lang.Long] = ???

  /**
    * Retrieves the tweets posted by the user
    *
    * @param user_id the user
    * @param posts   the number of posts to retrieve
    * @return
    */
  override def getTweets(user_id: Long, posts: Int): util.List[Tweet] = ???

  /**
    * Clears the content of the database
    */
  override def reset(): Unit = {

  }
}
