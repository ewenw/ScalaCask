package scalacask.engine.twitter;

public class Tweet implements Comparable<Tweet> {

  private long userID;
  private String creationDate;
  private String tweet;

  public Tweet(long userID, String creationDate, String tweet) {
    this.userID = userID;
    this.creationDate = creationDate;
    this.tweet = tweet;
  }

  public long getUserID() {
    return userID;
  }

  public String getTweet() {
    return tweet;
  }

  public String toString() {
    return userID + "|" + creationDate + "|" + tweet;
  }

  public static Tweet fromString(String str) {
    String[] strArray = str.split("|");
    return new Tweet(Long.valueOf(strArray[0]), strArray[1], strArray[2]);
  }

  public int compareTo(Tweet other) {
    return creationDate.compareTo(other.creationDate);
  }
}
