package com.rz.demo.util;

import static org.rrd4j.ConsolFun.AVERAGE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jone.R;
import jone.util.RrdUtils;

import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;

import com.alibaba.fastjson.JSON;

public class Test {

	/**
	 * @param args
	 */
	static String RRDDIR = "e:/myrrd";
	static String fileName = "192168001001_hmuused.rrd";
	static String ds = "hmuused";

	public static void main(String[] args) {
		//MonitorUtil.start();
//		long t1 = 1506129960;
//		long t2 = 1506130133;
//		Map<String, Object> m = readRrd(t2-5, t2);
//		System.out.println(JSON.toJSONString(m));
		double[] r = RrdUtils.readLastValues(fileName, ds, 5, 1);

		System.out.println("=============");
		System.out.println(JSON.toJSONString(r));
	}

	public static Map<String, Object> readRrd(long start, long end) {
		Map<String, Object> m = new HashMap<String, Object>();
		RrdDb rrdDb = null;
		try {
			String rrdPath = new File(RRDDIR, fileName).getPath();
			rrdDb = new RrdDb(rrdPath);
			FetchRequest request = rrdDb
					.createFetchRequest(AVERAGE, start, end, 1);
			System.out.println(request.getResolution());
			FetchData fetchData = request.fetchData();
			long[] timestamps = fetchData.getTimestamps();
			System.out.println("arc==" + fetchData.getArcStep());
			double[] values = fetchData.getValues(ds);
			m.put("timestamps", timestamps);
			m.put("values", values);
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
