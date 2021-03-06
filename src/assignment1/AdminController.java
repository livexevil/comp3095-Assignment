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
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
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
 
		HttpSession session = request.getSession();
		UtilityHelper helper = new UtilityHelper();
		ConnectionUtil util = new ConnectionUtil();
		
		if(session.getAttribute("user") != null)
		{
			if(session.getAttribute("user").equals("admin"))
			{
				try {
					if(util.countRows().equals(true))
					{
						request.setAttribute("noPost", "<h5 id='noPost'>There are no posts. Please create a new post </h5>" + "<a href='/comp3095/assignment2/NewPost.jsp'>Click here.</a>");
					}
					request.setAttribute("createPost", helper.buildAdminDivs());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				RequestDispatcher requestDispatcher;
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/AdminView.jsp");
				requestDispatcher.forward(request, response);
			}
			else
			{
				response.sendRedirect("/comp3095/assignment2/NotAnAdmin.jsp");
			}
		}
		else
		{
			response.sendRedirect("/comp3095/assignment1/login.jsp");
		}
	}

}
