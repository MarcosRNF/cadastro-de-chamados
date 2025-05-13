

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ListarChamadosServlet
 */
public class ListarChamadosServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		HttpSession sessao = request.getSession();
		
		
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Listar chamados</title>");
		out.println("</head>");
		out.println("<body>");		
		if(sessao.getAttribute("login") == null) {
			response.sendRedirect("http://localhost:8080/Chamados/login");
			System.out.println("Caiu aqui. Login inválido!");
		}
		
		out.println(sessao.getAttribute("info"));
		out.println("<br><a href='http://localhost:8080/Chamados/login?msg=logoff'>Sair</a> ");
		
		out.println("<div style='background-color: #00FFFF; border-bottom:1px solid grey'>");
		out.println("<h1>Seção Listar Chamados</h1>");
		out.println("</div>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root","");
			
			// DELETE
			// Quando existir uma query string
			if(request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			String delete = "DELETE FROM chamados where id = ?";
			PreparedStatement ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			ps.execute();
			}
			
			String SQL = "SELECT * FROM chamados";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(SQL);
			out.println("<table width='100%'>");
			out.println("<tr>");
			out.println("<td>ID </td>");
			out.println("<td>Titulo</td>");
			out.println("<td>Editar</td>");
			out.println("<td>Deletar</td>");
			out.println("<tr>");
			
			while(rs.next()) {
			out.println("<tr>");
			out.println("<td>"+ rs.getInt("id") +"</td>");
			out.println("<td>"+ rs.getString("titulo")+"</td>");
			out.println("<td><a href='http://localhost:8080/Chamados/editarchamado?id=" + rs.getInt("id") + "'>Editar</a></td>");
			out.println("<td><a href='http://localhost:8080/Chamados/listarchamados?id=" + rs.getInt("id") + "'>Deletar</a></td>");
			out.println("</tr>");
			}
			out.println("</table>");
			stm.close();
			conn.close();
			
		} catch(SQLException e) {
			out.println("Erro na consulta" + e.getMessage());
		}
			
		}catch (ClassNotFoundException e) {
			out.println("Erro ao se conectar com o BD: " + e.getMessage());
		}
		out.println("</body>");
		out.println("</html>");
	}
	
}
		



