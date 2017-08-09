package com.rz.demo.service;

import jone.data.db.DB;
import jone.data.db.DBs;

public class Test {

	static DB db = DBs.getDB("h2");
	public static void main(String[] args) {
		for (int i = 11; i < 3001; i++) {
			
			String sql = "insert into users values('a" + i + "', 'e739279cb28cdafd7373618313803524', 1, '2016-09-15', 1, 'email', '888888', 1, '2016-08-08 00:00:00', 'a" + i + "')";
			db.update(sql);
		}
	}

}
