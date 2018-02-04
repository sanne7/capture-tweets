package com.test.tweets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterReads {
	public static void main(String[] args) throws TwitterException, IOException {
		List<Status> results = search(args[1]);
		File file = new File("./results.csv");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		for (Status s : results) {
			pw.println(s.getId() +", "+ s.getText().replaceAll("\n", " ").replaceAll(",", "|")); //User ID & Tweet
		}
		pw.close();

	}

	public static List<Status> search(String keyword) throws TwitterException {

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey("<OAuth Consumer key>") // OAuth Consumer key
			  .setOAuthConsumerSecret("< OAuth Consumer secret>") // OAuth Consumer secret
			  .setOAuthAccessToken("<OAuth Access token>") // OAuth Access token
			  .setOAuthAccessTokenSecret("<OAuth Access Token>"); // OAuth Access Token
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
			//query.setSince("2018-01-24");
			//query.setUntil("2018-01-25");
			query.setCount(50);
			query.setLocale("en");
			query.setLang("en");
			QueryResult queryResult = twitter.search(query);
			return queryResult.getTweets();


	}

}
