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
  void addFollower(long user_id, long follower_id);

  /**
   * Retrieves the tweets of a user's timeline based on their followings
   *
   * @param user_id the user
   * @param posts the number of posts to retrieve
   */
  List<Tweet> getTimeline(long user_id, int posts);

  /**
   * Retrieves the user's followers
   *
   * @param user_id the user
   */
  List<Long> getFollowers(long user_id);

  /**
   * Retrieves who the user is following
   *
   * @param user_id the user
   */
  List<Long> getFollowees(long user_id);

  /**
   * Retrieves the tweets posted by the user
   * @param user_id the user
   * @param posts the number of posts to retrieve
   * @return
   */
  List<Tweet> getTweets(long user_id, int posts);

  /**
   * Clears the content of the database
   */
  void reset();
}
