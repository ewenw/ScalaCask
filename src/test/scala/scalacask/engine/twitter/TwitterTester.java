package scalacask.engine.twitter;

import java.util.Date;
public class TwitterTester {

  private static TwitterAPI api = new ScalaCaskTwitterAPI();
  private static TweetGenerator tg = new TweetGenerator("google-10000-english-usa.txt", 50000);
  private static SpeedLog insertFollowersLogger = new SpeedLog("insertFollowers");
  private static SpeedLog insertTweetsLogger = new SpeedLog("insertTweets");
  private static SpeedLog timelineLogger = new SpeedLog("timeline");

  public static void main(String[] args) {
    api.reset();
    insertFollowers(5, api);
    insertTweets(1000000, api);
    retrieveTweets(5000, 10, api);
  }

  // Inserts n random Tweets into database
  public static void insertTweets(long n, TwitterAPI api) {
    for (long i = 0; i < n; i++) {
      long userId = tg.getRandomUser();
      String tweet = tg.generateTweet();
      Tweet t = new Tweet(userId, new Date().toString(), tweet);
      if (i % 1000 == 0) {
        System.out.println(i + " tweets inserted");
        insertTweetsLogger.startRecord();
        api.postTweet(t);
        insertTweetsLogger.endRecord();
      } else {
        api.postTweet(t);
      }
    }
    insertTweetsLogger.dump();
  }

  // expected average number of followings per user
  public static void insertFollowers(long expectedFollowers, TwitterAPI api) {
    long nUsers = tg.users;
    long totalFollowings = nUsers * expectedFollowers;

    for (long i = 0; i < totalFollowings; i++) {
      long user = tg.getRandomUser();
      long follower = tg.getRandomUser();

      if (i % 1000 == 0) {
        System.out.println(i + " followings inserted");
        insertFollowersLogger.startRecord();
        api.addFollower(user, follower);
        insertFollowersLogger.endRecord();
      } else {
        api.addFollower(user, follower);
      }
    }
    insertFollowersLogger.dump();
    System.out.println("Added " + totalFollowings + " total followings for "
        + nUsers + " users.");
  }

  // Retrieves the most recent N posts from users' followers for nUsers
  public static void retrieveTweets(long nUsers, int nPosts, TwitterAPI api) {
    for (long i = 0; i < nUsers; i++) {
      long userId = tg.getRandomUser();
      timelineLogger.startRecord();
      api.getTimeline(userId, nPosts);
      timelineLogger.endRecord();
    }
    timelineLogger.dump();
  }
}
