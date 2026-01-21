package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/**
	 * Cadastrar.
	 *
	 * @param user the user
	 */
	public void cadastrar(JavaBeans user) {

		if (numeroExists(user.getTelefone())) {
			throw new RuntimeException("Numero já existe");
		}

		if (emailExists(user.getEmail())) {
			throw new RuntimeException("email já existe");
		}
		String sql = "INSERT INTO contatos(nome, telefone, email) VALUES (?,?,?)";

		try (Connection conn = Conectar.getConnection();

		) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getNome());
			stmt.setString(2, user.getTelefone());
			stmt.setString(3, user.getEmail());

			stmt.execute();

			System.out.println("Cliente salvo");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletar.
	 *
	 * @param user the user
	 */
	public void deletar(JavaBeans user) {
		String sql = "DELETE FROM contatos WHERE email = ? AND telefone = ?";

		try (Connection conn = Conectar.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getTelefone());

			int linhas = stmt.executeUpdate();

			if (linhas > 0) {
				System.out.println("Contato deletado");
			} else {
				System.out.println("Usuário não encontrado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Atualizar telefone.
	 *
	 * @param user the user
	 */
	public void atualizarTelefone(JavaBeans user) {
		String sql = "UPDATE contatos SET telefone = ? WHERE email = ?";

		try (Connection conn = Conectar.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getTelefone());
			stmt.setString(2, user.getEmail());

			int linhas = stmt.executeUpdate();

			if (linhas > 0) {
				System.out.println("Telefone atualizado");
			} else {
				System.out.println("Erro, não atualizou o telefone");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	// listar os contatos
	public ArrayList<JavaBeans> listarContatos() {
		// criando objeto para acessar a classe javabeans

		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String sql = "SELECT *  FROM contatos ORDER BY nome";

		try {
			Connection conn = Conectar.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			while (resultado.next()) {
				JavaBeans contato = new JavaBeans();
				contato.setIdcon(resultado.getString("idcon"));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));

				// populando o Array

				contatos.add(contato);

			}

		} catch (Exception e) {
			System.out.println(e);

		}

		return contatos;
	}

	/**
	 * Numero exists.
	 *
	 * @param telefone the telefone
	 * @return true, if successful
	 */
	public boolean numeroExists(String telefone) {
		String sql = "SELECT telefone FROM contatos WHERE telefone = ?";

		try (Connection conn = Conectar.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);

		) {
			stmt.setString(1, telefone);
			ResultSet result = stmt.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Email exists.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean emailExists(String email) {
		String sql = "SELECT email FROM contatos WHERE email = ?";

		try (Connection conn = Conectar.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);

		) {
			stmt.setString(1, email);
			ResultSet result = stmt.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}
