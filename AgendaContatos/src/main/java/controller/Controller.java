package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/deletar", "/atualizarTelefone", "/report" })
public class Controller extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		switch (action) {
		case "/main":
			contatos(request, response);
			break;

		case "/insert":
			Novocontato(request, response);
			break;

		case "/deletar":
			DeletarContato(request, response);
			break;

		case "/atualizarTelefone":
			atualizarTelefone(request, response);
			break;

		case "/report":
			gerarRelatorio(request, response);
			break;

		default:
			response.sendRedirect("index.html");
			break;
		}
	}

	// listar contatos

	/**
	 * Contatos.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return the array list
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected ArrayList<JavaBeans> contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// criando um objeto que irá receber os dados JavaBeans

		DAO dao = new DAO();
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// encaminhar a lista pra o documento agenda.jsp

		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

		// teste de recebimento da lista

		return lista;

	}

	/**
	 * Novocontato.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	// novo contato
	protected void Novocontato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.getparameter para capturar os dados
		// test for get of data

		JavaBeans dados = new JavaBeans(request.getParameter("nome"), request.getParameter("telefone"),
				request.getParameter("email"));

		DAO dao = new DAO();
		dao.cadastrar(dados);

		response.sendRedirect("main");

	}

	/**
	 * Deletar contato.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void DeletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JavaBeans dados = new JavaBeans(request.getParameter("email"), request.getParameter("telefone"));

		DAO dao = new DAO();
		dao.deletar(dados);

	}

	/**
	 * Atualizar telefone.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void atualizarTelefone(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JavaBeans dados = new JavaBeans(request.getParameter("email"), request.getParameter("telefone"));

		DAO dao = new DAO();
		dao.atualizarTelefone(dados);

		response.sendRedirect("main");
	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	// gerar relatório em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();

		try {
			// trasnformando o DOC em PDF

			response.setContentType("apllication/pdf");
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");

			// criar o document

			PdfWriter.getInstance(documento, response.getOutputStream());

			// abrir o conteudo

			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(" "));

			// definindo que a tabela vai ter 3 colunas
			PdfPTable tabela = new PdfPTable(3);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);

			DAO dao = new DAO();
			ArrayList<JavaBeans> lista = dao.listarContatos();

			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getTelefone());
				tabela.addCell(lista.get(i).getEmail());
			}

			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

}
