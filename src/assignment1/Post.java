package assignment1;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Post
 */
@WebServlet("/Post")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Request NewPost parameters
		request.getParameter("posttitle");
		request.getParameter("postbody");
		
		//Cookies for sticky pages
		//Cookies for sticky form
				Cookie postTitleCookie = new Cookie("posttitle", request.getParameter("posttitle"));
				postTitleCookie.setMaxAge(60*60*60);
				response.addCookie(postTitleCookie);
				request.setAttribute("postTitleCookie", postTitleCookie.getValue());
				
				Cookie postBodyCookie = new Cookie("postbody", request.getParameter("postbody"));
				postBodyCookie.setMaxAge(60*60*60);
				response.addCookie(postBodyCookie);
				request.setAttribute("postBodyCookie", postBodyCookie.getValue());
		
		Validations validator = new Validations();
		
		validator.checkPostTitle(request.getParameter("posttitle"));
		validator.checkPostBody(request.getParameter("postbody"));
		

		//Validate parameters to check for errors then display it to the user 
		request.setAttribute("errorTitle", validator.checkPostTitle(request.getParameter("posttitle")));
		request.setAttribute("errorBody", validator.checkPostBody(request.getParameter("postbody")));
			
		//Enter in the database
		
		HttpSession session = request.getSession(true);
		String user = session.getAttribute("user").toString();
		if(validator.validatePost() == true)
		{
			try {
				ConnectionUtil util = new ConnectionUtil();
				util.insertPost(request.getParameter("posttitle"), request.getParameter("postbody"), session.getAttribute("user"));
				
				UtilityHelper helper = new UtilityHelper();
				
				try {
					request.setAttribute("createPost", helper.buildDivs(user));
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

			catch (Exception exc){
				exc.printStackTrace();
			}
		}
		else{
			RequestDispatcher requestDispatcher;
			requestDispatcher = request.getRequestDispatcher("assignment2/NewPost.jsp");
			requestDispatcher.forward(request, response);
		}
	
		
		
	}

}
