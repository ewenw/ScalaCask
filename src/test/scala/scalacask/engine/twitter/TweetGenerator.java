package scalacask.engine.twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TweetGenerator {
  List<String> words = new ArrayList<String>();
  Random rand;
  long users;

  public TweetGenerator(String corpusPath, long users) {
    // Load word corpus into List
    try {
      Scanner corpus = new Scanner(new File(corpusPath)).useDelimiter(",\\s*");
      while(corpus.hasNext()){
        String line = corpus.nextLine();
        words.add(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("Loaded corpus with " + words.size() + " words.");
    rand = new Random();
    this.users = users;
  }

  // Generates a Tweet of less than 140 characters
  public String generateTweet() {
    String tweet = "";
    int numWords = rand.nextInt(20) + 4;
    while (tweet.length() < 120 && numWords > 1) {
      int seed = rand.nextInt(words.size());
      String newWord = words.get(seed);
      if (seed % 25 == 0)
        newWord = "#" + newWord;
      tweet += newWord + " ";
      numWords--;
    }
    tweet += words.get(rand.nextInt(words.size())) + ".";
    if (tweet.length() > 140)
      tweet = tweet.substring(tweet.length() - 140);
    return tweet;
  }

  // Selects a random user_id
  public long getRandomUser() {
    return (long) (rand.nextFloat() * this.users);
  }

}
