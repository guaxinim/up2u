package br.com.guaxinim.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="\"Usuario\"")
@JsonRootName("Usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonProperty
    @Column(name="\"codigoUsuario\"")
    private Integer codigoUsuario;

    @NotNull
    @Size(max = 80)
    @JsonProperty
    @Column(name="\"nome\"")
    private String nome;

    @JsonProperty
    @Column(name="\"nascimento\"")
    private Date nascimento;

    @Size(max = 14)
    @JsonProperty
    @Column(name="\"cpf\"")
    private String cpf;

    @JsonProperty
    @Column(name="\"telefone\"")
    private String telefone;

    @JsonProperty
    @Column(name="\"observacao\"")
    private String observacao;

    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigoUsuario=" + codigoUsuario +
                ", nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
