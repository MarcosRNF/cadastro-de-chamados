

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class NovoChamado
 */
public class NovoChamadoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Cadastrar novo Chamado</title>");
		out.println("<meta charset='utf-8'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Novo Chamado</h1>");
		out.println("<hr>");
		out.println("<form method='POST'>");
		out.println("<p>Titulo</p>");
		out.println("<br>");
		out.println("<input type='text' name='titulo'>");
		out.println("<br>");
		out.println("<p>Descricao:</p>");
		out.println("<textarea name='descricao' rows= 10 cols= 40></textarea>");
		out.println("<br>");
		out.println("<input type='submit' value='Enviar'>");
		out.println("</form>");
		out.println("<hr>");
		out.println("<a href='localhost:8080/Chamado/index' > Voltar ao inicio </a>");
		out.println("<br>");
		out.println("<a href='localhost:8080/Chamado/listar-chamados' > Listar chamados </a>");
		out.println("<br>");
		out.println("<a href='#' > Sair </a>");
		out.println("<br>");
		out.println("</body>");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		
		if(titulo.trim().length() < 4) {
			out.println("<p style='color: #FF0000'>Preencha o titulo</p>");
		} else if (descricao.trim().length() < 4) {
			out.println("<p style='color: #FF0000'>Preencha a descrição</p>");
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String SQL = "INSERT INTO chamados (titulo, descricao) values (?,?)";
				
				try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "");
				PreparedStatement prs = conn.prepareStatement(SQL);
				prs.setString(1, titulo);
				prs.setString(2, descricao);
				prs.execute();
				prs.close();
				conn.close();
				
				response.sendRedirect("http://localhost:8080/Chamados/listarchamados");

				} catch(SQLException e) {
					out.println("Problema no banco de dados: " + e.getMessage());
				}
				
				
			} catch (ClassNotFoundException e) {
				out.println("Driver não encontrado!");
			}
		}
		
	}

}
