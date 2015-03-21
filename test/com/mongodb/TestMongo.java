package com.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * MongoDB 连接插入查询
 * @author Jude
 *
 */
public class TestMongo {

	private static final String DB_NAME = "judedbtest";
	private static final String COLLECTION_NAME = "names";
	private static MongoClient mongoClient = null;
	private static MongoDatabase database;
	private static MongoCollection<Document> collection;

	@BeforeClass
	public static void setUpClass() throws UnknownHostException {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		// mongoClient = new MongoClient("localhost");
		database = mongoClient.getDatabase(DB_NAME);
	}

	@Before
	public void setUpMeathod() throws UnknownHostException {

		collection = database.getCollection(COLLECTION_NAME);
	}

	@Test
	public void testDBName() {
		assertEquals(DB_NAME, database.getName());
	}

	/**
	 * 单个插入和查询
	 */
	@Test
	public void testInsertAndFind() {

		Document doc = new Document("name", "MongoDB")
				.append("type", "database").append("count", 1) 
				.append("info", new Document("x", 203).append("y", 102));
		collection.insertOne(doc);

		Document myDoc = collection.find(eq("name", "MongoDB")).first();

		assertEquals(doc.get("name"), myDoc.get("name"));
		assertEquals(1, collection.count());
	}

	/**
	 * 批量插入
	 * 
	 * @param size
	 */
	public void testMultiInsert(int size) {
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < size; i++) {
			documents.add(new Document("i", i));
		}
		collection.insertMany(documents);
		assertEquals(size, collection.count());
	}

	/**
	 * 使用 MongoCursor 来遍历对象
	 */
	@Test
	public void testIterator() {
		int size = 100;
		testMultiInsert(size);
		MongoCursor<Document> cursor = collection.find().iterator();
		int i = 0;
		try {
			while (cursor.hasNext()) {
				cursor.next();
				i++;
			}
		} finally {
			cursor.close();
		}
		assertEquals(size, i);
	}

	/**
	 * 查询
	 */
	@Test
	public void testSearch() {

		Document doc = new Document("name", "MongoDB")
				.append("type", "database").append("count", 1)
				.append("info", new Document("x", 203).append("y", 102));
		collection.insertOne(doc);

		Document doc2 = new Document("name", "MongoDB")
				.append("type", "database").append("count", 1)
				.append("info", new Document("x", 203).append("y", 102));
		collection.insertOne(doc2);
		
		MongoCursor<Document> cursor = collection.find(eq("name", "MongoDB"))
				.iterator();
		int i = 0;
		try {
			while (cursor.hasNext()) {
				i++;
				assertEquals(doc.get("name"), cursor.next().get("name"));
			}
		} finally {
			cursor.close();
		}
		assertEquals(2, i);
	}

	@After
	public void tearDownMethod() {
		if (collection != null) {
			try {
				collection.drop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@AfterClass
	public static void tearDownClass() {
		if (mongoClient != null) {
			if (database != null) {
				try {
					database.drop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				mongoClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mongoClient = null;
		}
	}
}