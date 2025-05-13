

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

/**
 * Servlet implementation class NovoChamado
 */
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login</title>");
		out.println("<meta charset='utf-8'>");
		out.println("</head>");
		out.println("<body>");
		
		// Sair
		if(request.getParameter("msg") != null) {
			if(request.getParameter("msg").equals("logoff")) {
				request.getSession().invalidate();
				out.println("<p style='color: #FF0000; font-size: 18px;'>Deslogado com sucesso!</p>");
			}
		}
		
		// Validação de usuário e senha imprimindo msg na tela
		if(request.getParameter("msg") != null) {
			if(request.getParameter("msg").equals("error")) {
				out.println("<p style='color: #FF0000; font-size: 18px;'>LOGIN E/OU SENHA INVÁLIDOS!</p>");
			}
		}
		out.println("<h1>Preencha seu login e senha</h1>");
		out.println("<hr>");
		out.println("<form method='POST'>");
		out.println("<p>Login</p>");
		out.println("<br>");
		out.println("<input type='text' name='txtLogin'>");
		out.println("<br>");
		out.println("<p>Senha:</p>");
		out.println("<input type='password' name='txtPassword'>");
		out.println("<br>");
		out.println("<input type='submit' value='Enviar'>");
		out.println("</form>");
		out.println("<hr>");
		out.println("<a href='http://localhost:8080/Chamados/index' > Voltar ao inicio </a>");
		out.println("<br>");
		out.println("<a href='http://localhost:8080/Chamados/listarchamados' > Listar chamados </a>");
		out.println("<br>");
		out.println("<a href='#' > Sair </a>");
		out.println("<br>");
		out.println("</body>");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
				
		String login = request.getParameter("txtLogin");
		String senha = request.getParameter("txtPassword");
		
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String SQL = "SELECT * FROM usuarios where login = ? and senha = ? ";
				
				try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "");
				PreparedStatement prs = conn.prepareStatement(SQL);
				prs.setString(1, login);
				prs.setString(2, senha);
				
				
				ResultSet rs = prs.executeQuery();
				if(rs.next()) {
					prs.close();
					conn.close();
					HttpSession sessao = request.getSession();
					sessao.setAttribute("login", login);
					sessao.setAttribute("info", request.getRemoteAddr());
					System.out.println("Login válido! Redirecionando...");

					response.sendRedirect("http://localhost:8080/Chamados/listarchamados");
				} else {
					prs.close();
					conn.close();
					System.out.println("Login Inválido! Redirecionando...");

					
					response.sendRedirect("http://localhost:8080/Chamados/login?msg=error");
				}
				
				} catch(SQLException e) {
					out.println("Problema no banco de dados: " + e.getMessage());
				}
				
				
				
			} catch (ClassNotFoundException e) {
				out.println("Driver não encontrado!");
			}
		
		
	}

}
