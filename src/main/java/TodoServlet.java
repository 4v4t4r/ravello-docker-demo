import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TodoServlet extends HttpServlet {

	DBparams db;
	TodoListDAO dao;

	public void init() throws ServletException {
		
		try {
			initDBparams();
			dao = new TodoListDAO(db);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void initDBparams(){
		db = new DBparams("admin", "admin", "postgresql", "db", 5432, "todo",
				"org.postgresql.Driver");						
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType("text/html");
			String newitem = request.getParameter("additem");
			String deleteitem = request.getParameter("deleteitem");
			if (newitem != null) {
				dao.addItem(newitem);
				response.sendRedirect(request.getRequestURI());
			}
			if (deleteitem != null) {
				dao.deleteItem(Integer.parseInt(deleteitem));
				response.sendRedirect(request.getRequestURI());
			}
			printItems( request,  response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void printItems(HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<TodoItem> todoList = dao.getAllItems();
		
		PrintWriter out = response.getWriter();

		// Print out HTML response
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Todo List</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Todo List Database</h1>");
		out.println("<table border='2' cellpadding='2'>");
		out.println("<tr>");
		out.println("<th>Item ID</th>");
		out.println("<th>Item Description</th>");
		out.println("</tr>");

		// Add each item into HTML table
		for (TodoItem item : todoList) {
			out.println("<tr>");
			out.println("<td>" + item.getId() + "</td>");
			out.println("<td>" + item.getItem() + "</td>");
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table><p>");

		// Create form for adding new items
		out.println("<form action='" + request.getRequestURI()
				+ "' method=post>");
		out.println("<input type=text size=40 name=additem>  ");
		out.println("<input type=submit value='Add new item'>");
		out.println("</form>");

		// Create form for deleting an item by ID
		out.println("<form action='" + request.getRequestURI()
				+ "' method=post>");
		out.println("<input type=text size=10 name=deleteitem>  ");
		out.println("<input type=submit value='Delete by ID'>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");

	}

}
