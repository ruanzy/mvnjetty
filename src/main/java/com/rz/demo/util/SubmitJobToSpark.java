package com.rz.demo.util;

import org.apache.spark.deploy.SparkSubmit;

public class SubmitJobToSpark
{
	public static void submitJob()
	{
		String[] args = new String[] { "--master", "local", "--name",
				"test java submit job to spark", "--class",
				"com.dt.spark.SparkApps.Trans",
				"file:/D:/workspaces/SparkApps/target/spark.SimpleApp-0.0.1-SNAPSHOT.jar" };
		SparkSubmit.main(args);
	}

	public static void main(String[] args)
	{
		submitJob();
	}
}
