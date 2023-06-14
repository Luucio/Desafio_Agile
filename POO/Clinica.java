package Purc_test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clinica {
    private List<Paciente> pacientesCadastrados = new ArrayList<>();
    private List<Agendamento> agendamentos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Clinica clinica = new Clinica();

        int opcao;
        do {
            System.out.println("----- Menu Principal -----");
            System.out.println("1. Cadastrar paciente");
            System.out.println("2. Exibir pacientes cadastrados");
            System.out.println("3. Marcar consulta");
            System.out.println("4. Cancelar consulta");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    clinica.cadastrarPaciente();
                    break;
                case 2:
                    clinica.exibirPacientesCadastrados();
                    break;
                case 3:
                    clinica.marcarConsulta();
                    break;
                case 4:
                    clinica.cancelarConsulta();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (opcao != 5);
    }

    public void cadastrarPaciente() {
        System.out.print("Digite o nome do paciente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o telefone do paciente: ");
        String telefone = scanner.nextLine();

        for (Paciente paciente : pacientesCadastrados) {
            if (paciente.getTelefone().equals(telefone)) {
                System.out.println("Paciente já cadastrado!");
                return;
            }
        }

        Paciente paciente = new Paciente(nome, telefone);
        pacientesCadastrados.add(paciente);
        System.out.println("Paciente cadastrado com sucesso!");
    }
    public void exibirPacientesCadastrados() {
        if (pacientesCadastrados.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        System.out.println("Pacientes cadastrados:");
        for (int i = 0; i < pacientesCadastrados.size(); i++) {
            Paciente paciente = pacientesCadastrados.get(i);
            System.out.println((i + 1) + ". " + paciente.getNome() + " - Telefone: " + paciente.getTelefone());
        }
    }
    public void marcarConsulta() {
        exibirPacientesCadastrados();
        if (pacientesCadastrados.isEmpty()) {
            return;
        }
        System.out.print("Selecione o número do paciente para marcar a consulta: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        int pacienteIndex = opcao - 1;
        if (pacienteIndex < 0 || pacienteIndex >= pacientesCadastrados.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Paciente paciente = pacientesCadastrados.get(pacienteIndex);
        System.out.print("Digite o dia da consulta: ");
        String dia = scanner.nextLine();
        System.out.print("Digite a hora da consulta: ");
        String hora = scanner.nextLine();
        System.out.print("Digite a especialidade da consulta: ");
        String especialidade = scanner.nextLine();

        if (consultaJaAgendada(dia, hora)) {
            System.out.println("Já existe uma consulta agendada nesse dia e horário.");
            return;
        }

        if (dataHoraPassada(dia, hora)) {
            System.out.println("Não é possível marcar consultas em datas passadas.");
            return;
        }

        Agendamento agendamento = new Agendamento(paciente, dia, hora, especialidade);
        agendamentos.add(agendamento);
        System.out.println("Consulta agendada com sucesso!");
        
    }
    public boolean consultaJaAgendada(String dia, String hora) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDia().equals(dia) && agendamento.getHora().equals(hora)) {
                return true;
            }
        }
        return false;
    }
    public boolean dataHoraPassada(String dia, String hora) {
        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();
        LocalDate dataConsulta = LocalDate.parse(dia, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime horaConsulta = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

        if (dataConsulta.isBefore(dataAtual) || (dataConsulta.isEqual(dataAtual) && horaConsulta.isBefore(horaAtual))) {
            return true;
        }
        return false;
    }
	public void cancelarConsulta() {
	    if (agendamentos.isEmpty()) {
	        System.out.println("Nenhum agendamento existente.");
	        return;
	    }
	    System.out.println("Agendamentos existentes:");
	    for (int i = 0; i < agendamentos.size(); i++) {
	        Agendamento agendamento = agendamentos.get(i);
	        System.out.println((i + 1) + ". Paciente: " + agendamento.getPaciente().getNome() + " - Data: " + agendamento.getDia() + " - Hora: " + agendamento.getHora() + " - Especialidade: " + agendamento.getEspecialidade());
	    }
	    System.out.print("Escolha o número do agendamento que deseja cancelar: ");
	    int opcao = scanner.nextInt();
	    scanner.nextLine();
	    int agendamentoIndex = opcao - 1;
	    if (agendamentoIndex < 0 || agendamentoIndex >= agendamentos.size()) {
	        System.out.println("Opção inválida.");
	        return;
	    }
	    Agendamento agendamentoCancelado = agendamentos.get(agendamentoIndex);
	    System.out.println("Agendamento selecionado:");
	    System.out.println("Paciente: " + agendamentoCancelado.getPaciente().getNome());
	    System.out.println("Data: " + agendamentoCancelado.getDia());
	    System.out.println("Hora: " + agendamentoCancelado.getHora());
	    System.out.println("Especialidade: " + agendamentoCancelado.getEspecialidade());
	    System.out.print("Deseja cancelar o agendamento? (S/N): ");
	    String confirmacao = scanner.nextLine();
	    if (confirmacao.equalsIgnoreCase("S")) {
	        agendamentos.remove(agendamentoIndex);
	        System.out.println("Agendamento cancelado com sucesso!");
	    } else {
	        System.out.println("Cancelamento de agendamento cancelado.");
	    }
	}
}