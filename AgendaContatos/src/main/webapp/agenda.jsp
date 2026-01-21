<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="model.JavaBeans"%>
    <%@ page import="java.util.ArrayList"%>
    
    <%
    @ SuppressWarnings ("unchecked")
    ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
   
    %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda de contatos</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Agenda de contatos</h1>
	<a href="novo.html"class="botao1" id="novoContato">Novo contato</a>
	<table>
	<thead>
		<tr>
			<th>Id</th>
			<th>Nome</th>
			<th>Telefone</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>
		<%
		if (lista != null && !lista.isEmpty()) {
		    for (int i = 0; i < lista.size(); i++) {%>
		        <tr>
		            <td><%= lista.get(i).getIdcon() %></td>
		            <td><%= lista.get(i).getNome() %></td>
		            <td><%= lista.get(i).getTelefone() %></td>
		            <td><%= lista.get(i).getEmail() %></td>
		        </tr><%
		    }
		} else {%>
		        <tr>
		            <td colspan="4">Nenhum contato encontrado</td>
		        </tr><%
		}%>
</tbody>
	</table>
	
	<a href="atualizarContato.html"class="botao1" id="novoContato">Atualize o contato!</a>
	<a href="deletarContato.html"class="botao1" id="novoContato">Delete o contato!</a>
	<a href="report" class="botao2">Relatório</a>
</body>
</html>