package br.com.solara.services;

import br.com.solara.model.bo.MedicaoBO;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MedicaoScheduler {

    public static void agendarMedicoes(MedicaoBO medicaoBO) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable tarefa = () -> {
            try {
                System.out.println("Realizando medições: " + LocalDateTime.now());
                medicaoBO.realizarMedicoesAutomatizadas();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Calcular tempo inicial até as 12h ou 00h
        long delayInicial = calcularDelayParaProximaExecucao();

        // Agendar para executar a cada 12 horas
        scheduler.scheduleAtFixedRate(tarefa, delayInicial, TimeUnit.HOURS.toSeconds(12), TimeUnit.SECONDS);
    }

    private static long calcularDelayParaProximaExecucao() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime proximaExecucao;

        // Definir a próxima execução às 12h ou 00h
        if (agora.getHour() < 12) {
            proximaExecucao = agora.withHour(12).withMinute(0).withSecond(0);
        } else {
            proximaExecucao = agora.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        }

        return java.time.Duration.between(agora, proximaExecucao).getSeconds();
    }
}
