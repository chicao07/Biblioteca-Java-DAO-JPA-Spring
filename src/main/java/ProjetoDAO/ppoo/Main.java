package ProjetoDAO.ppoo;

import exception.AutorNaoEncontradoException;
import exception.LivroNaoEncontradoException;
import exception.ValidacaoException;
import model.Autor;
import model.Livro;
import service.AutorService;
import service.AutorServiceImpl;
import service.LivroService;
import service.LivroServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static LivroService livroService = new LivroServiceImpl();
    private static AutorService autorService = new AutorServiceImpl();

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

            System.out.println();

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
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void cadastrarLivro() {
        System.out.println("\n--- Cadastro de Livro ---");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        Autor autor = selecionarAutor();
        if (autor == null) return; // autor não encontrado, aborta o cadastro

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

    // Método auxiliar: pede o ID do autor e busca o objeto correspondente
    private static Autor selecionarAutor() {
        System.out.print("ID do autor (digite 2 para listar autores cadastrados): ");
        String entrada = scanner.nextLine();

        if (entrada.equals("2")) {
            listarAutores();
            System.out.print("Agora digite o ID do autor desejado: ");
            entrada = scanner.nextLine();
        }

        try {
            int autorId = Integer.parseInt(entrada);
            return autorService.buscarPorId(autorId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
            return null;
        } catch (AutorNaoEncontradoException e) {
            System.out.println("❌ " + e.getMessage());
            return null;
        }
    }

    private static void listarAutores() {
        List<Autor> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("📭 Nenhum autor cadastrado ainda.");
        } else {
            for (Autor a : autores) {
                System.out.println(a);
            }
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

            System.out.println("Autor atual: " + livro.getAutor());
            Autor novoAutor = selecionarAutor();
            if (novoAutor != null) {
                livro.setAutor(novoAutor);
            }

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