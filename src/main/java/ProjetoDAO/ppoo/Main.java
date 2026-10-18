package ProjetoDAO.ppoo;

import exception.LivroNaoEncontradoException;
import exception.ValidacaoException;
import model.Livro;
import service.LivroService;
import service.LivroServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static LivroService livroService = new LivroServiceImpl();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    buscarLivroPorId();
                    break;
                case 4:
                    atualizarLivro();
                    break;
                case 5:
                    deletarLivro();
                    break;
                case 6:
                    System.out.println("👋 Saindo... Até mais!");
                    break;
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
            }

            System.out.println(); // linha em branco para organizar

        } while (opcao != 6);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== SISTEMA DE LIVROS =====");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Buscar livro por ID");
        System.out.println("4 - Atualizar livro");
        System.out.println("5 - Deletar livro");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1; // opção inválida, vai cair no "default" do switch
        }
    }

    private static void cadastrarLivro() {
        System.out.println("\n--- Cadastro de Livro ---");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());

        System.out.print("Ano de publicação: ");
        int ano = Integer.parseInt(scanner.nextLine());

        try {
            Livro livro = new Livro(titulo, autor, preco, ano);
            livroService.salvar(livro);
            System.out.println("✅ Livro cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
        }
    }

    private static void listarLivros() {
        System.out.println("\n--- Lista de Livros ---");

        List<Livro> livros = livroService.listarTodos();

        if (livros.isEmpty()) {
            System.out.println("📭 Nenhum livro cadastrado ainda.");
        } else {
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    private static void buscarLivroPorId() {
        System.out.println("\n--- Buscar Livro por ID ---");
        System.out.print("Digite o ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Livro livro = livroService.buscarPorId(id);
            System.out.println("📖 Livro encontrado: " + livro);
        } catch (LivroNaoEncontradoException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void atualizarLivro() {
        System.out.println("\n--- Atualizar Livro ---");
        System.out.print("Digite o ID do livro que deseja atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Livro livro = livroService.buscarPorId(id);

            System.out.print("Novo título (atual: " + livro.getTitulo() + "): ");
            livro.setTitulo(scanner.nextLine());

            System.out.print("Novo autor (atual: " + livro.getAutor() + "): ");
            livro.setAutor(scanner.nextLine());

            System.out.print("Novo preço (atual: " + livro.getPreco() + "): ");
            livro.setPreco(Double.parseDouble(scanner.nextLine()));

            System.out.print("Novo ano de publicação (atual: " + livro.getAnoPublicacao() + "): ");
            livro.setAnoPublicacao(Integer.parseInt(scanner.nextLine()));

            livroService.atualizar(livro);
            System.out.println("✅ Livro atualizado com sucesso!");

        } catch (LivroNaoEncontradoException | ValidacaoException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void deletarLivro() {
        System.out.println("\n--- Deletar Livro ---");
        System.out.print("Digite o ID do livro que deseja deletar: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            livroService.deletar(id);
            System.out.println("✅ Livro deletado com sucesso!");
        } catch (LivroNaoEncontradoException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

}