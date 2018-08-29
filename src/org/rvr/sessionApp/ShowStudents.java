package org.rvr.sessionApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;

@WebServlet("/showStudent")
public class ShowStudents extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(false);
		if (session != null) {
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				java.sql.Driver driverRef = new Driver();
				DriverManager.registerDriver(driverRef);

				String dbUrl = "jdbc:mysql://localhost:3306/capsV3_db" + "?user=root&password=root";
				con = DriverManager.getConnection(dbUrl);

				String sql = "select * from students_info";

				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				
				String username=(String) session.getAttribute("username");
				out.println("<h3> Hi "+username+"</h3>");
				
				

				while (rs.next()) {
					int regno = rs.getInt(1);
					String firstname = rs.getString("firstname");
					String lastname = rs.getString("lastname");
					String isAdmin = rs.getString("isadmin");
					String passwd = rs.getString("password");

					out.println("Reg no: " + regno +"<br>");
					out.println("First name: " + firstname+"<br>");
					out.println("Last Name: " + lastname+"<br>");
					out.println("Admin: " + isAdmin+"<br>");
					out.println("Password: " + passwd+"<br>");
					out.println("-------------------"+"<br>");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		} else {
			out.println("<h1>Please Login</h1>");
		}
	}
}
