package br.com.guaxinim.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by elvis on 22/08/2016.
 */

@Entity
@Table(name="\"Livro\"")
@JsonRootName("Livro")
@NamedQuery(name="Livro.findAll", query="SELECT liv FROM Livro liv")
public class Livro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonProperty
    @Column(name="\"codigoLivro\"")
    private Integer codigoLivro;

    @NotNull
    @Size(max = 80)
    @JsonProperty
    @Column(name="\"nome\"")
    private String nome;

    @Size(max = 50)
    @JsonProperty
    @Column(name="\"autor\"")
    private String autor;

    @JsonProperty
    @Column(name="\"observacao\"")
    private String observacao;

    public Integer getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(Integer codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

