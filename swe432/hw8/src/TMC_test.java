import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author session
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TMC_test extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		res.setContentType("text/html");
		ServletOutputStream out = res.getOutputStream();

		Enumeration keys = req.getParameterNames();
		while (keys.hasMoreElements()) {
			String k = (String)keys.nextElement();

			out.println(k + " = " + req.getParameter(k) + "<br>");
		}
	}
}
