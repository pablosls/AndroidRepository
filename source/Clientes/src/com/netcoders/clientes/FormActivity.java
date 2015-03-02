package com.netcoders.clientes;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class FormActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
		recuperarCliente();
	}

	public void emitir(View botao){
		//Pegamos o elemento de texto da tela
		//e convertemos para EditText pois
		//tudo que vem da tela é herança da View
		EditText txtNome = (EditText) 
				findViewById(R.id.editTextNome);
		EditText txtEmail = (EditText)
				findViewById(R.id.editTextEmail);
		EditText txtTel = (EditText)
				findViewById(R.id.editTextTel);

		boolean existeErro = false;
		if(!validar(txtNome)){
			existeErro = true;
		}
		if(!validar(txtEmail)){
			existeErro = true;
		}
		if(!validar(txtTel)){
			existeErro = true;
		}
		
		if(!existeErro)
		{
			Cliente cliente = new Cliente();
			cliente.setNome(txtNome.getText().toString());
			cliente.setEmail(txtEmail.getText().toString());
			cliente.setTel(txtTel.getText().toString());
			emitirGuia(cliente);
		}
	}
	
	private void emitirGuia(Cliente cliente){
		//EStamos criando um componente de informação
		//para que seja iniciada a nova tela
		Intent it = new Intent(this, ImpressaoActivity.class);
		//Passamos o cliiente para o componente que sera
		//interpretado na classe Impressao
		it.putExtra("cliente", cliente);
		//Iniciamos a nova atividade/tela
		startActivity(it);
		salvarCliente(cliente);
	}

	private boolean validar(EditText editText){
		if(editText.getText().toString().length() > 0){
			//Apaga a marcação de erro do campo de texto
			editText.setError(null);
			return true;
		}
		//Criar uma marcacao de erro no campo de texto
		editText.setError("Campo obrigatório");
		return false;
	}

	public void limpar(View botao){
		EditText txtNome = (EditText) 
				findViewById(R.id.editTextNome);
		EditText txtEmail = (EditText)
				findViewById(R.id.editTextEmail);
		EditText txtTel = (EditText)
				findViewById(R.id.editTextTel);
		
		txtNome.setText("");
		txtEmail.setText("");
		txtTel.setText("");
	}

	private void salvarCliente(Cliente cliente){
		
		//Convertemos nosso cliente 
		//para o formato Json utilizando a API
		//Gson localizado na pasta libs
		String json =
				new Gson().toJson(cliente);
		
		//Capturamos o arquivo xml de preferencias 
		//presente em todo aplicativo android
		SharedPreferences preferencias =
				getSharedPreferences("Clientes",
						MODE_PRIVATE);
		
		//Obtemos uma versao editavel das preferencias
		Editor editor = preferencias.edit();
		
		//colocamos o nosso cliente em formato json
		//no arquivo de preferencias
		editor.putString("cliente", json);
		//Confirmamos as alterações
		editor.apply();
		
	}

	private void recuperarCliente(){
		SharedPreferences preferencias =
				getSharedPreferences("Clientes",
						MODE_PRIVATE);
		String json = preferencias.getString("cliente",
				null);
		if(json != null){
			Cliente cliente = new Gson()
				.fromJson(json, Cliente.class);
			
			EditText txtNome = (EditText) 
					findViewById(R.id.editTextNome);
			EditText txtEmail = (EditText)
					findViewById(R.id.editTextEmail);
			EditText txtTel = (EditText)
					findViewById(R.id.editTextTel);
			
			txtNome.setText(cliente.getNome());
			txtEmail.setText(cliente.getEmail());
			txtTel.setText(cliente.getTel());
		}
	}
}
