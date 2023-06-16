package first.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/log")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static PreparedStatement pst;
	public void init(ServletConfig config) throws ServletException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:8080/login","root","root");
			
			pst=con.prepareStatement("select * from users where username=? and password=?");
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("uid");
		String pd=request.getParameter("pwd");
		try {
			pst.setString(1,id);
			pst.setString(2, pd);
			ResultSet rs =pst.executeQuery();
			
			if(rs.next()) {
				RequestDispatcher rf=request.getRequestDispatcher("welcome");
				rf.forward(request,response);
			}
			else {
				PrintWriter out=response.getWriter();
				
//				<h2 style="color:red"></h2>
				out.println("<h1 style=\"color: red; text-align: center;\">Incorrect Details please enter valid username and password</h1>");
				RequestDispatcher rf=request.getRequestDispatcher("login.html");
				rf.include(request,response);
			}
		}
		catch(Exception ex) {
			
			System.out.println(ex);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
