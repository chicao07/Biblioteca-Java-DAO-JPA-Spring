package ProjetoDAO.ppoo;

import model.Autor;
import model.Livro;
import serviceSpring.AutorServiceSpring;
import serviceSpring.LivroServiceSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")
@ComponentScan(basePackages = {"serviceSpring", "repository", "model", "controller"})
public class BibliotecaApplication implements CommandLineRunner {

	private final AutorServiceSpring autorService;
	private final LivroServiceSpring livroService;
	
	public BibliotecaApplication(AutorServiceSpring autorService, LivroServiceSpring livroService) {
		this.autorService = autorService;
		this.livroService = livroService;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Autor autor = new Autor("Graciliano Ramos", "Brasileiro");
		autorService.salvar(autor);
		System.out.println("Autor salvo via Service: " + autor);
		
		Livro livro = new Livro("Vidas secas", autor, 24.90, 1938);
		livroService.salvar(livro);
		System.out.println("Livro salvo via Service: " + livro);
		
		System.out.println("\n Lista de livros via Services: ");
		livroService.listarTodos().forEach(System.out::println);
	}
}
