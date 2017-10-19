package com.rz.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import scala.Tuple2;

public class SparkApp
{
	
	public static void main(String[] args) throws Exception
	{
		t1();
	}
	
	public static void t1() throws Exception
	{
		System.setProperty("hadoop.home.dir",
				"D:/hadoop-common-2.2.0-bin-master");
		SparkConf conf = new SparkConf().setAppName("Spakr Trans").setMaster(
				"local");
		conf.set("spark.testing.memory", "546870912");
		SparkSession spark = SparkSession
				  .builder()
				  .appName("Spakr Trans").master("local")
				  .config("spark.testing.memory", "546870912")
				  .getOrCreate();
		JavaRDD<String> all0 = spark.sparkContext().textFile("d:/sss/log2.txt", 1).toJavaRDD();
		JavaRDD<String> all = all0.filter(new Function<String, Boolean>() {

			private static final long serialVersionUID = 1L;

			public Boolean call(String v1) throws Exception
			{
				String[] arr = v1.split("#");
				String city = arr[3];
				List<String> list = Arrays.asList("shanghai", "hubei", "hunan", "sichuan");
				return list.contains(city);
			}
		});
		JavaPairRDD<String, String> dp = all
				.mapToPair(new PairFunction<String, String, String>() {
					private static final long serialVersionUID = 1L;

					public Tuple2<String, String> call(String v1)
							throws Exception
					{
						String[] arr = v1.split("#");
						String ip = arr[0];
						String date = arr[1];
						String page = arr[2];
						return new Tuple2<String, String>(date + "_" + page, ip);
					}
				});

		JavaPairRDD<String, Iterable<String>> da = dp.groupByKey();
		JavaPairRDD<String, Long> d2 = da.mapToPair(new PairFunction<Tuple2<String,Iterable<String>>, String, Long>() {

			private static final long serialVersionUID = 1L;

			public Tuple2<String, Long> call(Tuple2<String, Iterable<String>> t)
					throws Exception
			{
				String datepage = t._1();
				Iterable<String> list = t._2();
				Set<String> set = new HashSet<String>();
				for (String s : list)
				{
					set.add(s);
				}
				return new Tuple2<String, Long>(datepage, Long.valueOf(set.size()));
			}
		});
		List<StructField> fields = new ArrayList<StructField>();
		StructField date = DataTypes.createStructField("date", DataTypes.StringType, true);
		fields.add(date);
		StructField page = DataTypes.createStructField("page", DataTypes.StringType, true);
		fields.add(page);
		StructField uv = DataTypes.createStructField("uv", DataTypes.LongType, true);
		fields.add(uv);
		StructType schema = DataTypes.createStructType(fields);
		JavaRDD<Row> rowRDD = d2.map(new Function<Tuple2<String, Long>, Row>() {

			private static final long serialVersionUID = 1L;

			public Row call(Tuple2<String, Long> v1) throws Exception
			{
				String[] datepage = v1._1().split("_");
				String date = datepage[0];
				String page = datepage[1];
				Long uv = v1._2();
				return RowFactory.create(date, page, uv);
			}});
		
		
		Dataset<Row> df = spark.createDataFrame(rowRDD, schema);
		df.createOrReplaceTempView("a");
		//df.show(65);
		String sql = "select * FROM (select *,row_number() over (partition by date order by uv desc)rn FROM a)temp where rn<=3";
		Dataset<Row> r = spark.sql(sql);
		r.show(65);
		JavaRDD<Row> r2 = r.javaRDD();
		JavaPairRDD<String, String> r3 = r2.mapToPair(new PairFunction<Row, String, String>() {

			private static final long serialVersionUID = 1L;

			public Tuple2<String, String> call(Row v1) throws Exception
			{
				String date = v1.getString(0);
				String page = v1.getString(1);
				long uv = v1.getLong(2);
				return new Tuple2<String, String>(date, page + "_" + uv);
			}
			
		});
		JavaPairRDD<String, Iterable<String>> d3 = r3.groupByKey();
		JavaPairRDD<Long, String> d4 = d3.mapToPair(new PairFunction<Tuple2<String,Iterable<String>>, Long, String>() {

			private static final long serialVersionUID = 1L;

			public Tuple2<Long, String> call(Tuple2<String, Iterable<String>> t)
					throws Exception
			{
				String date = t._1();
				Iterable<String> list = t._2();
				Long sum = 0L;
				StringBuffer sb = new StringBuffer(date);
				for (String s : list)
				{
					String[] arr = s.split("_");
					Long uv = Long.valueOf(arr[1]);
					sum += uv;
					sb.append(",").append(s);
				}
				return new Tuple2<Long, String>(sum, sb.toString());
			}
		});
		JavaPairRDD<Long, String> d5 = d4.sortByKey(false);
		d5.foreach(new VoidFunction<Tuple2<Long,String>>() {
			
			private static final long serialVersionUID = 1L;

			public void call(Tuple2<Long, String> t) throws Exception
			{
				System.out.println(t._1() + "=" + t._2());
				
			}
		});
		JavaRDD<Row> d6 = d5.flatMap(new FlatMapFunction<Tuple2<Long,String>, Row>() {

			private static final long serialVersionUID = 1L;

			public Iterator<Row> call(Tuple2<Long, String> v1) throws Exception
			{
				List<Row> list = new ArrayList<Row>();
				String[] arr = v1._2.split(",");
				String date = arr[0];
				Row r = null;
				for (int i = 1; i < 4; i++)
				{
					String[] arr2 = arr[i].split("_");
					String page = arr2[0];
					Long uv = Long.valueOf(arr2[1]);
					r = RowFactory.create(date, page, uv);
					list.add(r);
				}
				return list.iterator();
			}
		});
		Dataset<Row> d7 = spark.createDataFrame(d6, schema);
		d7.show();
	}
	
	public static void t2() throws Exception
	{
		System.setProperty("hadoop.home.dir",
				"D:/hadoop-common-2.2.0-bin-master");
		SparkConf conf = new SparkConf().setAppName("Spakr Trans").setMaster(
				"local");
		conf.set("spark.testing.memory", "546870912");
		SparkSession spark = SparkSession
				  .builder()
				  .appName("Spakr Trans").master("local")
				  .config("spark.testing.memory", "546870912")
				  .getOrCreate();
		String sqlUrl = "jdbc:oracle:thin:@//10.68.64.154:1521/orcl";  
		Properties properties = new Properties();
        properties.put("user", "yceess");
        properties.put("password", "yceess");
        Dataset<Row> r = spark.sqlContext().read().jdbc(sqlUrl, "eb01_arcustom", properties);
        r.show();
	}

}
