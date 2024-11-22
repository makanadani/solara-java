package br.com.solara.model.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.xml.bind.annotation.XmlRootElement;

// Classe referente às empresas que administram os microgrids
@XmlRootElement
public class Empresa {
    private int idEmpresa;
    private String razaoSocialEmpresa;
    private String cnpjEmpresa;
    private String senhaEmpresa;
    private String imagemEmpresa;
    private String descricaoEmpresa;

    public Empresa() {}

    // Construtor com ID, razão social, imagem e descrição (CONSUMO DE API PELO FRONT-END)
    public Empresa(int idEmpresa, String razaoSocialEmpresa, String imagemEmpresa, String descricaoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocialEmpresa = razaoSocialEmpresa;
        this.imagemEmpresa = imagemEmpresa;
        this.descricaoEmpresa = descricaoEmpresa;
    }

    // Construtor completo
    public Empresa(int idEmpresa, String razaoSocialEmpresa, String cnpjEmpresa, String senhaEmpresa, String imagemEmpresa, String descricaoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocialEmpresa = razaoSocialEmpresa;
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

    public String getRazaoSocialEmpresa() {
        return razaoSocialEmpresa;
    }

    public void setRazaoSocialEmpresa(String razaoSocialEmpresa) {
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

    public void setSenhaEmpresa(String senha) {
        this.senhaEmpresa = criptografarSenha(senha);
    }

    public boolean verificarSenha(String senha) {
        return this.senhaEmpresa.equals(criptografarSenha(senha));
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

    // Criptografia da senha
    private String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha: " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Empresa [idEmpresa=" + idEmpresa +
                ", razaoSocialEmpresa=" + razaoSocialEmpresa +
                ", cnpjEmpresa=" + cnpjEmpresa +
                ", imagemEmpresa=" + imagemEmpresa +
                ", descricaoEmpresa=" + descricaoEmpresa + "]";
    }
}
