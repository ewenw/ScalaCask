package scalacask.engine.twitter;

import java.util.List;

public interface TwitterAPI {

  /**
   * Posts tweet to database
   */
  void postTweet(Tweet t);

  /**
   * Adds a follower to a user
   *
   * @param user_id the user being followed
   * @param follower_id the follower's id
   */
  void addFollower(String user_id, String follower_id);

  /**
   * Retrieves the tweets of a user's timeline based on their followings
   *
   * @param user_id the user
   * @param posts the number of posts to retrieve
   */
  void getTimeline(String user_id, int posts);



  /**
   * Clears the content of the database
   */
  void reset();
}
