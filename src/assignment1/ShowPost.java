package assignment1;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowPost
 */
@WebServlet("/ShowPost")
public class ShowPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UtilityHelper helper = new UtilityHelper();
		HttpSession session = request.getSession();
		ConnectionUtil util = new ConnectionUtil();
		
		
		
		if(session.getAttribute("user") != null)
		{
			try {
				if(util.countRows().equals(true))
				{
					request.setAttribute("noPost", "<h5 id='noPost'>There are no posts. Please create a new post </h5>" + "<a href='/comp3095/assignment2/NewPost.jsp'>Click here.</a>");
				}
				request.setAttribute("createPost", helper.buildDivs(session.getAttribute("user")));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequestDispatcher requestDispatcher;
			requestDispatcher = request.getRequestDispatcher("/assignment2/Posts.jsp");
			requestDispatcher.forward(request, response);
		}
		else
		{
			response.sendRedirect("/comp3095/assignment1/Home.html");
		}
	
	}

}
