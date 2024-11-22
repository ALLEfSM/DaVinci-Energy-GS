package br.com.fiap.davinciEnergy.controller.eficiencia;

import br.com.fiap.davinciEnergy.model.Dispositivo;
import br.com.fiap.davinciEnergy.model.Eficiencia;
import br.com.fiap.davinciEnergy.model.Tipos;
import br.com.fiap.davinciEnergy.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("eficiencia")
public class EficienciaController {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @GetMapping("media")
    public String calcularMedia(Model model){
        model.addAttribute("dispositivo", dispositivoRepository.findAll());
        return "eficiencia/media";
    }

    @PostMapping("media")
    public String calcularMedia(Eficiencia eficiencia, Model model) {
        // Verificar se o dispositivo foi selecionado
        if (eficiencia.getDispositivo() == null || eficiencia.getDispositivo().getId() == null) {
            model.addAttribute("erro", "Dispositivo não foi selecionado.");
            return "eficiencia/media";
        }

        Dispositivo dispositivo = dispositivoRepository.findById(eficiencia.getDispositivo().getId()).orElse(null);
        if (dispositivo == null) {
            model.addAttribute("erro", "Dispositivo não encontrado.");
            return "eficiencia/media";
        }

        eficiencia.setDispositivo(dispositivo);
        Double mediaFinal = eficiencia.calcularMediaFinal();
        String classificacao = calcularClassificacao(dispositivo.getTipos(), mediaFinal);


        model.addAttribute("mediaFinal", mediaFinal);
        model.addAttribute("classificacao", classificacao);
        model.addAttribute("dispositivo", dispositivo);

        return "eficiencia/media";
    }

    private String calcularClassificacao(Tipos tipo, double consumoMensal) {
        switch (tipo) {
            case arCondicionado:
                if (consumoMensal <= 100) return "A";
                if (consumoMensal <= 200) return "B";
                if (consumoMensal <= 300) return "C";
                return "D";
            case fogao:
                if (consumoMensal <= 20) return "A";
                if (consumoMensal <= 40) return "B";
                if (consumoMensal <= 60) return "C";
                return "D";
            case microondas:
                if (consumoMensal <= 30) return "A";
                if (consumoMensal <= 60) return "B";
                if (consumoMensal <= 90) return "C";
                return "D";
            case fornoEletrico:
                if (consumoMensal <= 50) return "A";
                if (consumoMensal <= 100) return "B";
                if (consumoMensal <= 150) return "C";
                return "D";
            case Lampada:
                if (consumoMensal <= 5) return "A";
                if (consumoMensal <= 10) return "B";
                if (consumoMensal <= 15) return "C";
                return "D";
            case lavadorRoupa:
                if (consumoMensal <= 80) return "A";
                if (consumoMensal <= 150) return "B";
                if (consumoMensal <= 200) return "C";
                return "D";
            case refrigerador:
                if (consumoMensal <= 50) return "A";
                if (consumoMensal <= 100) return "B";
                if (consumoMensal <= 150) return "C";
                return "D";
            case televisor:
                if (consumoMensal <= 30) return "A";
                if (consumoMensal <= 60) return "B";
                if (consumoMensal <= 90) return "C";
                return "D";
            case ventilador:
                if (consumoMensal <= 15) return "A";
                if (consumoMensal <= 30) return "B";
                if (consumoMensal <= 50) return "C";
                return "D";
            default:
                if (consumoMensal <= 50) return "A";
                if (consumoMensal <= 100) return "B";
                if (consumoMensal <= 200) return "C";
                return "D";
        }
    }
}
