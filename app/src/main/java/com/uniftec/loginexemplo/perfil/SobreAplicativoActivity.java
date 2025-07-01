package com.uniftec.loginexemplo.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.HomeActivity;


public class SobreAplicativoActivity extends AppCompatActivity {

    private TextView text;
    private Button btnVoltar;
    private long USU_ID_SESSION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sobre_aplicativo);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USU_ID_SESSION")) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
        }

        inicializarComponentes();
        configurarEventos(USU_ID_SESSION);

        String sobreNosTexto = "Bem-vindo a plataforma definitiva para conectar profissionais de eventos a oportunidades de trabalho e organizadores a talentos qualificados."
                + "<br><br>"
                + "Nascemos da necessidade de otimizar e profissionalizar a contratação no setor de eventos. Nosso objetivo é simplificar o processo, garantindo que o profissional certo encontre o evento certo, e que os organizadores montem equipes de excelência com agilidade e confiança."
                + "<br><br>"
                + "<b>Para Profissionais:</b><br>"
                + "Encontre vagas alinhadas com suas habilidades e experiência em nosso nicho específico de eventos. Crie seu perfil, destaque seu portfólio e candidate-se com facilidade. Oferecemos um caminho direto para as melhores oportunidades, eliminando burocracias e conectando você diretamente aos projetos que importam."
                + "<br><br>"
                + "<b>Para Organizadores de Eventos:</b><br>"
                + "Descubra profissionais avaliados e qualificados, prontos para atuar em seu evento. Com nossas <b>ferramentas exclusivas de organização</b>, você pode gerenciar processos seletivos, comunicar-se com candidatos e construir equipes eficientes rapidamente. Nosso <b>sistema de avaliação de profissionais</b> garante que você tenha acesso a talentos verificados e de alta performance.";

        text.setText(Html.fromHtml(sobreNosTexto, Html.FROM_HTML_MODE_LEGACY));
    }

    private void inicializarComponentes() {
        btnVoltar = findViewById(R.id.btnVoltar);
        text = findViewById(R.id.text);
    }

    private void configurarEventos(long USU_ID_SESSION) {

        btnVoltar.setOnClickListener(v -> voltar(USU_ID_SESSION));
    }

    private void voltar(long USU_ID_SESSION) {
        Intent intent = new Intent(SobreAplicativoActivity.this, HomeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
        finish();
    }
}