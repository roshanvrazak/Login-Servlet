package org.rvr.sessionApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServ extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String id = req.getParameter("regno");
		String passwd = req.getParameter("passwd");
		int sid = Integer.parseInt(id);

		// System.out.println(sid+" "+passwd);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// Student ls = null;
		String dbUrl = "jdbc:mysql://localhost:3306/capsV3_db?user=root&password=root";
		String qry = "select * from students_info where sid=? and password=?";
		try {
			/*
			 * FileReader fr = new FileReader("lib/db.properties"); Properties prop = new
			 * Properties(); prop.load(fr);
			 */

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(dbUrl);

			pstmt = con.prepareStatement(qry);
			pstmt.setInt(1, sid);
			pstmt.setString(2, passwd);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				HttpSession session = req.getSession();
				session.setAttribute("username", rs.getString("firstname"));
				
				int regno = rs.getInt(1);
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String isAdmin = rs.getString("isadmin");
				String pass = rs.getString("password");

				out.println("Reg no: " + regno);
				out.println("First name: " + firstname);
				out.println("Last Name: " + lastname);
				out.println("Admin: " + isAdmin);
				out.println("Password: " + pass);
				out.println("-------------------");
				
				
				// out.println("Session created");

			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
