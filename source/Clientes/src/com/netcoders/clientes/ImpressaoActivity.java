package com.netcoders.clientes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ImpressaoActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impressao);
		
		//Verificamos se existe um extra
		//com o nome cliente
		if(getIntent().hasExtra("cliente")){
			//pegamos o cliente passado pela tela
			//FormActivity
			Cliente cliente = (Cliente)
					getIntent().getSerializableExtra("cliente");
			
			//Capturamos os elementos da tela
			TextView nome = (TextView) 
					findViewById(R.id.textViewNome);
			
			TextView email = (TextView)
					findViewById(R.id.textViewEmail);
			
			TextView telefone = (TextView)
					findViewById(R.id.textViewTel);
			
			//Populamos os elementos a partir do objeto cliente
			nome.setText(cliente.getNome());
			email.setText(cliente.getEmail());
			telefone.setText(cliente.getTel());
		}
	}
}
