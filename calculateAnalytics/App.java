package calculateAnalytics;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.*;

public class App {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3307/callcenter";
		String username ="root";
		String password ="";
		Scanner s = new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(url,username,password);
			
			java.sql.Statement statement = connection.createStatement();
			
			ResultSet resultSet;
			
			System.out.println("Enter 1 for get data of Hour of the day when the call volume is highest.");
			System.out.println("Enter 2 for get data of Hour of the day when the calls are longest.");
			System.out.println("Enter 3 for get data of Day of the week when the call volume is highest.");
			System.out.println("Enter 4 for get data of Day of the week when the calls are longest.");
			
			int num = s.nextInt();
			String str = "AM";
			Integer n;
			
			if(num==1) {
				resultSet=statement.executeQuery("Select hr from (SELECT Hour(Start_time) as hr, COUNT(hour(Start_time)) AS CountOftime\r\n"
						+ "FROM calldetails\r\n"
						+ "GROUP BY Hour(Start_time) ORDER BY CountOftime DESC) a\r\n"
						+ "LIMIT 1;");
				while(resultSet.next()) {
					n = Integer.valueOf(resultSet.getInt(1))+1;
					if(n-1 >= 12) {
						str = "PM";
					}
					System.out.println("Hour of the day when the call volume is highest is "+resultSet.getInt(1)+" - "+n+" "+str);
				}
			}
			else if(num==2) {
				resultSet=statement.executeQuery("Select hr from (SELECT Hour(Start_time) as hr, sum(Duration) AS CountOftime\r\n"
						+ "FROM calldetails\r\n"
						+ "GROUP BY Hour(Start_time) ORDER BY CountOftime DESC) a\r\n"
						+ "LIMIT 1;");
				while(resultSet.next()) {
					n = Integer.valueOf(resultSet.getInt(1))+1;
					if(n-1 >= 12) {
						str = "PM";
					}
					System.out.println("Hour of the day when the calls are longest is "+resultSet.getInt(1)+" - "+n+" "+str);
				}
			}
			else if(num==3) {
				resultSet=statement.executeQuery("Select DAYNAME(day) from (SELECT Date(Start_time) as day, count(Date(Start_time)) AS CountOftime\r\n"
						+ "FROM calldetails\r\n"
						+ "GROUP BY Date(Start_time) ORDER BY CountOftime DESC) a\r\n"
						+ "LIMIT 1;");
				while(resultSet.next()) {
					
					System.out.println(resultSet.getString(1)+" is the day of the week when the call volume highest.");
				}
			}
			else if(num==4) {
				resultSet=statement.executeQuery("Select DAYNAME(day) from (SELECT Date(Start_time) as day, sum(Duration) AS CountOftime\r\n"
						+ "FROM calldetails\r\n"
						+ "GROUP BY Date(Start_time) ORDER BY CountOftime DESC) a\r\n"
						+ "LIMIT 1;");
                while(resultSet.next()) {
					
					System.out.println(resultSet.getString(1)+" is the day of the week when the calls are longest.");
				}
			}
			else {
				System.out.println("You are entering wrong digit.");
			}
			
			
			
			connection.close();
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
	}

}
