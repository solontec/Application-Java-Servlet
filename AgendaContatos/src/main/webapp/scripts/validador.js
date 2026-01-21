// validar formulario


function validar(){
	let nome = frmContato.nome.value
	let telefone = frmContato.telefone.value
	let email = frmContato.nome.value
	
	if(nome === ""){
		alert("Preenche o nome")
		frmContato.nome.focus()
		return false
	} 
	
	if(telefone === ""){
		alert("preenche o telefone")
		frmContato.telefone.focus()
		return false
	} 
	
	if(email === ""){
			alert("preenche o email")
			frmContato.telefone.focus()
			return false
		} 
	
	document.forms["frmContato"].submit()

}