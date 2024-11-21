package br.com.solara.model.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Empresa {
    private int idEmpresa;
    private String razaoSocialEmpresa; // Alterado de nomeEmpresa para razaoSocialEmpresa
    private String cnpjEmpresa;
    private String senhaEmpresa;
    private String imagemEmpresa;
    private String descricaoEmpresa;

    public Empresa() {
    }

    public Empresa(int idEmpresa, String razaoSocialEmpresa, String cnpjEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocialEmpresa = razaoSocialEmpresa; // Ajustado o construtor
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Empresa(int idEmpresa, String razaoSocialEmpresa, String cnpjEmpresa, String senhaEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocialEmpresa = razaoSocialEmpresa; // Ajustado o construtor
        this.cnpjEmpresa = cnpjEmpresa;
        setSenhaEmpresa(senhaEmpresa);
    }

    public Empresa(int idEmpresa, String razaoSocialEmpresa, String cnpjEmpresa, String senhaEmpresa, String imagemEmpresa, String descricaoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocialEmpresa = razaoSocialEmpresa; // Ajustado o construtor
        this.cnpjEmpresa = cnpjEmpresa;
        setSenhaEmpresa(senhaEmpresa);
        this.imagemEmpresa = imagemEmpresa;
        this.descricaoEmpresa = descricaoEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazaoSocialEmpresa() { // Ajustado o getter
        return razaoSocialEmpresa;
    }

    public void setRazaoSocialEmpresa(String razaoSocialEmpresa) { // Ajustado o setter
        this.razaoSocialEmpresa = razaoSocialEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getSenhaEmpresa() {
        return senhaEmpresa;
    }

    // Senha criptografada
    public void setSenhaEmpresa(String senha) {
        this.senhaEmpresa = criptografarSenha(senha);
    }

    public boolean verificarSenha(String senha) {
        return this.senhaEmpresa.equals(criptografarSenha(senha));
    }

    private String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes()); // Calcula o hash com SHA-256
            return Base64.getEncoder().encodeToString(hash); // Converte para Base64 para facilitar armazenamento
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha: " + e.getMessage(), e);
        }
    }

    public String getImagemEmpresa() {
        return imagemEmpresa;
    }

    public void setImagemEmpresa(String imagemEmpresa) {
        this.imagemEmpresa = imagemEmpresa;
    }

    public String getDescricaoEmpresa() {
        return descricaoEmpresa;
    }

    public void setDescricaoEmpresa(String descricaoEmpresa) {
        this.descricaoEmpresa = descricaoEmpresa;
    }

    @Override
    public String toString() {
        return "Empresa [idEmpresa=" + idEmpresa +
                ", razaoSocialEmpresa=" + razaoSocialEmpresa + // Ajustado no toString
                ", cnpjEmpresa=" + cnpjEmpresa +
                ", imagemEmpresa=" + imagemEmpresa +
                ", descricaoEmpresa=" + descricaoEmpresa + "]";
    }
}
