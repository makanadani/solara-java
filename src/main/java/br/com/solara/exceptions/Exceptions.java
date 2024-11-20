package br.com.solara.exceptions;

public class Exceptions {

    // Exceção para validações gerais
    public static class ValidationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ValidationException(String message) {
            super(message);
        }
    }

    // Exceção para entidades não encontradas
    public static class NotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NotFoundException(String message) {
            super(message);
        }
    }

    // Exceção para dados duplicados
    public static class DuplicateException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public DuplicateException(String message) {
            super(message);
        }
    }

    // Exceção para erros de banco de dados
    public static class DatabaseException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // A classe principal serve apenas como agrupadora, sem instâncias
    private Exceptions() {
        // Evita instanciamento
    }
}
