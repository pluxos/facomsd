package br.com.jvitoraa.repository;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DatabaseRepository {

	private Map<BigInteger, String> database = new ConcurrentHashMap<BigInteger, String>();

	public boolean create(BigInteger id, String value) {

		if (database.containsKey(id)) {
			return false;
		}

		database.put(id, value);
		return true;
	}

	public boolean update(BigInteger id, String value) {

		if (!database.containsKey(id)) {
			return false;
		}

		database.put(id, value);
		return true;
	}

	public boolean delete(BigInteger id) {

		if (database.containsKey(id)) {
			database.remove(id);
			return true;
		}

		return false;
	}

	public String read(BigInteger id) {

		if (database.containsKey(id)) {
			return database.get(id);
		}

		return StringUtils.EMPTY;
	}

	public Map<BigInteger, String> getDatabase() {
		return database;
	}
}
