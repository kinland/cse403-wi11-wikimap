
package communication;

/*
 * DatabaseUpdater - class with a collection of functions that will
 * interact with the database to update/insert/remove data.
 * 
 *  Note: Each method takes a Connection _con object to know where 
 *  execute the query/statement
 */

import java.sql.*;
import java.util.*;

public class DatabaseUpdater {

	// updateRelevantNodes, will update/insert the ArticleRelations table 
	// with a map of the related articles and their stengths.
	public static void updateRelevantNodes(Connection _con, String article, Map<String, Integer> relatedArticles)
	{	
		//System.out.println("updating: " + article);
		if (article == null || relatedArticles == null) {
			return;
		}

		try 
		{
			Statement st = _con.createStatement();
			for(String key : relatedArticles.keySet())
			{
				int strength = relatedArticles.get(key); 
				st.executeUpdate("INSERT INTO ArticleRelations (article, relatedArticle, strength) " 
						+ "VALUES ('" + article + "', '" + key + "', " + strength + ") "
						+ "ON DUPLICATE KEY UPDATE strength = " + strength);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	// updatePreviewText, will update/insert the preview text for a given 
	// article.  
	public static void updatePreviewText(Connection _con, String article, String summary, boolean redirect)
	{	
		//System.out.println("preview text: (" + article + ", " + summary + ", " + (redirect ? "true" : "false") + ")");
		if (article == null || summary == null) {
			return;
		}

		try 
		{
			//EnsureConnection();	
			Statement st = _con.createStatement();
			st.executeUpdate("INSERT INTO ArticleSummary (article, summary, redirect) " 
					+ "VALUES ('" + article + "', '" + (redirect ? " " : summary) + "', " + (redirect ? "TRUE" : "FALSE") + ") "
					+ "ON DUPLICATE KEY UPDATE summary = '" + summary + "'");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	// updateImageURL, will update/insert the url for an image for a given 
	// article.
	public static void updateImageURL(Connection _con, String article, String articleURL)
	{
		//System.out.println("image url: (" + article + ", " + articleURL + ")");
		if (article == null || articleURL == null) {
			return;
		}

		try 
		{
			Statement st = _con.createStatement();
			st.executeUpdate("INSERT INTO ArticleImages (article, articleURL) " 
					+ "VALUES ('" + article + "', '" + articleURL + "') "
					+ "ON DUPLICATE KEY UPDATE articleURL = '" + articleURL + "'");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public static void updateVector(Connection _con, String article, String vector, boolean redirect){
		if (article == null || vector == null) {
			return;
		}
		
		int bool;
		if(redirect){
			bool = 1;
		}
		else{
			bool = 0;
		}
		try
		{
			//EnsureConnection();
			Statement st = _con.createStatement();
			st.executeUpdate("INSERT INTO ArticleVector (Article, Links, Redirect) "
					+ "VALUES ('" + article + "', '" + vector + "', '" + bool + "') "
					+ "ON DUPLICATE KEY UPDATE Links = '" + vector + "', Redirect = '" + bool + "'");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	// Removes the article from the database.  
	public static void removeArticle(Connection _con, String article)
	{
		if (article == null) {
			return;
		}
		// Cannot assume on Cascade Delete.  Database was not designed that way.
		// Must remove from each table. 
		try
		{
			Statement st = _con.createStatement();
			st.executeUpdate("DELETE FROM articlesummary " + 
					"WHERE Article = '" + article + "'" );
			st.clearBatch();
			st.executeUpdate("DELETE FROM articlerelations" + 
					"WHERE Article = '" + article + "'" );
			st.clearBatch();
			st.executeUpdate("DELETE FROM articleimages" + 
					"WHERE Article = '" + article + "'" );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
