package se.lnu.c1dv008.timeline;

import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DB {
	private static Dao<Timeline, Long> timelineDao = null;
	private static Dao<Event, Long> eventDao = null;

	private static ConnectionSource connectionSource = null;

	private static void openIfClosed() {
		try {
			Class.forName("org.sqlite.JDBC");
			String databaseUrl = "jdbc:sqlite:sample.db";
			connectionSource = new JdbcConnectionSource(databaseUrl);

			TableUtils.createTableIfNotExists(connectionSource, Timeline.class);
			TableUtils.createTableIfNotExists(connectionSource, Event.class);

			timelineDao = DaoManager.createDao(connectionSource, Timeline.class);
			eventDao = DaoManager.createDao(connectionSource, Event.class);
		} catch (Exception e) {
			throw new RuntimeException("Can't open DB");
		}
	}

	public static void close() {
		if (connectionSource != null)
			connectionSource.closeQuietly();

		connectionSource = null;
		timelineDao = null;
		eventDao = null;
	}

	public static Dao<Timeline, Long> timelines() {
		openIfClosed();
		return timelineDao;
	}

	public static Dao<Event, Long> events() {
		openIfClosed();
		return eventDao;
	}
}
