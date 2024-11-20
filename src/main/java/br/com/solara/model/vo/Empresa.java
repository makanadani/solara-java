package br.com.solara.model.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Empresa {
    private int idEmpresa;
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private String senhaEmpresa;

    public Empresa() {
    }

    public Empresa(int idEmpresa, String nomeEmpresa, String cnpjEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Empresa(int idEmpresa, String nomeEmpresa, String cnpjEmpresa, String senhaEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        setSenhaEmpresa(senhaEmpresa); // Criptografa ao definir a senha
    }

    /** 
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
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

    public void setSenhaEmpresa(String senhaEmpresa) {
        this.senhaEmpresa = criptografarSenha(senhaEmpresa);
    }

    public boolean verificarSenha(String senha) {
        String senhaCriptografada = criptografarSenha(senha);
        return this.senhaEmpresa.equals(senhaCriptografada);
    }

    private String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Empresa [idEmpresa=" + idEmpresa +
                ", nomeEmpresa=" + nomeEmpresa +
                ", cnpjEmpresa=" + cnpjEmpresa + "]";
    }
}
