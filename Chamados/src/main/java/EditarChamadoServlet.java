

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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class NovoChamado
 */
public class EditarChamadoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "");
			
		
			String SQL = "select * from chamados where id = ?";
			
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, Integer.parseInt(request.getParameter("id")));
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Editar Chamado</title>");
			out.println("<meta charset='utf-8'");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Novo Chamado</h1>");
			out.println("<hr>");
			out.println("<form method='POST'>");
			out.print("ID do Chamado: <br> <input type='text' name='txtId' readonly='readonly' value='" + rs.getString("id") +"'>");
			out.println("<p>Titulo</p>");
			out.println("<br>");
			out.println("<input type='text' name='txtTitulo' value='" + rs.getString("titulo") + "'>");
			out.println("<br>");
			out.println("<p>Descricao:</p>");
			out.println("<textarea name='txtDescricao' rows= 10 cols= 40>"+ rs.getString("descricao")+ "</textarea>");
			out.println("<br>");
			out.println("<input type='submit' value='Atualizar chamado'>");
			out.println("</form>");
			out.println("<hr>");
			out.println("<a href='localhost:8080/Chamado/index' > Voltar ao inicio </a>");
			out.println("<br>");
			out.println("<a href='localhost:8080/Chamado/logoff' > Sair </a>");
			out.println("<br>");
			out.println("<a href='#' > Sair </a>");
			out.println("<br>");
			out.println("</body>");
			out.println("</html>");
			} else {
				out.println("Este chamado não existe");
			}
			
			pstm.close();
			conn.close();
		
		}catch(SQLException e) {
			out.println("Deu pau em: " + e.getMessage());
		}
		
		}catch(ClassNotFoundException e) {
			out.println("Driver não encontrado: " + e.getMessage());
		}
		
		}

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("txtId");
		String titulo = request.getParameter("txtTitulo");
		String descricao = request.getParameter("txtDescricao");
		
		if(titulo.trim().length() < 4) {
			out.println("<p style='color: #FF0000'>Preencha o titulo</p>");
		} else if (descricao.trim().length() < 4) {
			out.println("<p style='color: #FF0000'>Preencha a descrição</p>");
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String SQL = "update chamados set titulo = ?, descricao  = ? where id = ?";
				
				try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "");
				PreparedStatement prs = conn.prepareStatement(SQL);
				prs.setString(1, titulo);
				prs.setString(2, descricao);
				prs.setInt(3, Integer.parseInt(id));
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
