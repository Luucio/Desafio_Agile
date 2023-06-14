package Purc_test;

public class Agendamento {
	 private Paciente paciente;
	    private String dia;
	    private String hora;
	    private String especialidade;

	    public Agendamento(Paciente paciente, String dia, String hora, String especialidade) {
	        this.paciente = paciente;
	        this.dia = dia;
	        this.hora = hora;
	        this.especialidade = especialidade;
	    }

	    public Paciente getPaciente() {
	        return paciente;
	    }

	    public String getDia() {
	        return dia;
	    }

	    public String getHora() {
	        return hora;
	    }

	    public String getEspecialidade() {
	        return especialidade;
	    }
}
