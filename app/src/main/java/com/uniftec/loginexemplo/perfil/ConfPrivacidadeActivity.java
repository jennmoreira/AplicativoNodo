package com.uniftec.loginexemplo.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.HomeActivity;


public class ConfPrivacidadeActivity extends AppCompatActivity {

    private TextView text;
    private Button btnVoltar;
    private long USU_ID_SESSION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conf_privacidade);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USU_ID_SESSION")) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
        }

        inicializarComponentes();
        configurarEventos(USU_ID_SESSION);

        String privacidadeTexto = "Sua privacidade é fundamental para nós. Aqui você tem o controle total sobre como suas informações são compartilhadas e utilizadas. Personalize suas preferências para garantir que sua experiência seja segura e alinhada com suas expectativas."
                + "<br><br>"
                + "<b>Visibilidade do Perfil</b><br>"
                + "<b>Perfil Público:</b> Seu perfil, incluindo nome, foto e experiência profissional, é visível para organizadores de eventos que buscam profissionais. Recomendado para quem busca novas oportunidades."
                + "<br>"
                + "<b>Perfil Privado:</b> Seu perfil não será visível publicamente para organizadores. Você só será encontrado se aplicar a uma vaga específica e o organizador visualizar sua candidatura."
                + "<br><br>"
                + "<b>Notificações</b><br>"
                + "<b>Novas Oportunidades de Vaga:</b> Receba alertas sobre vagas que correspondem ao seu perfil e preferências."
                + "<br>"
                + "<b>Mensagens e Convites:</b> Receba notificações quando organizadores enviarem mensagens ou convites."
                + "<br>"
                + "<b>Atualizações do Aplicativo:</b> Receba informações sobre novas funcionalidades e melhorias."
                + "<br><br>"
                + "<b>Compartilhamento de Dados</b><br>"
                + "<b>Uso de Dados para Melhorias:</b> Ajude-nos a aprimorar o aplicativo permitindo o uso de dados anonimizados para análise de desempenho e identificação de tendências (nunca compartilharemos seus dados pessoais)."
                + "<br>"
                + "<b>Marketing e Promoções:</b> Permita o recebimento de comunicações sobre ofertas especiais, parceiros ou serviços relacionados ao universo de eventos.";

        text.setText(Html.fromHtml(privacidadeTexto, Html.FROM_HTML_MODE_LEGACY));
    }

    private void inicializarComponentes() {
        btnVoltar = findViewById(R.id.btnVoltar);
        text = findViewById(R.id.text);
    }

    private void configurarEventos(long USU_ID_SESSION) {

        btnVoltar.setOnClickListener(v -> voltar(USU_ID_SESSION));
    }

    private void voltar(long USU_ID_SESSION) {
        Intent intent = new Intent(ConfPrivacidadeActivity.this, HomeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
        finish();
    }
}