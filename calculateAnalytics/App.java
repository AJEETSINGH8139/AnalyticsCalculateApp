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
			
			if(num==1) {
				resultSet=statement.executeQuery("select MAX(Hour(Start_time)) from calldetails Group by Start_time order by count(Hour(Start_time)) limit 1 ");
				while(resultSet.next()) {
					System.out.println(resultSet.getInt(1));
//					System.out.println(resultSet.getInt(1));
				}
			}
			else if(num==2) {
				resultSet=statement.executeQuery("select Hour(Start_time) from calldetails where Start_time = (Select SUM(Duration) from calldetails)");
				while(resultSet.next()) {
					System.out.println(resultSet.getInt(1));
//					System.out.println(resultSet.getInt(1));
				}
			}
			else if(num==3) {
				resultSet=statement.executeQuery("select Hour(Start_time) from calldetails where Start_time = (Select MAX(Start_time) from calldetails)");
				while(resultSet.next()) {
					System.out.println(resultSet.getInt(1));
//					System.out.println(resultSet.getInt(1));
				}
			}
			else if(num==4) {
				resultSet=statement.executeQuery("select Hour(Start_time) from calldetails where Start_time = (Select MAX(Start_time) from calldetails)");
				while(resultSet.next()) {
					System.out.println(resultSet.getInt(1));
//					System.out.println(resultSet.getInt(1));
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
