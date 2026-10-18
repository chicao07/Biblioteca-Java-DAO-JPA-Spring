package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Livro")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "O título do livro não pode ser vazio!")
	@Column(nullable = false, length = 150)
	private String titulo;
	
	@NotNull(message = "O livro deve ter um ator associado")
	@ManyToOne
	@JoinColumn(name = "autor_id", nullable = false)
	private Autor autor;
	
	@Positive(message = "O preço do livro deve ser maior que zero!")
	@Column(nullable = false)
	private double preco;
	
	@Max(value = 2025, message = "O ano de publicação não deve ser no futuro!")
	@Positive(message = "O ano de publicação deve ser um valor positivo!")
	@Column(name = "ano_publicacao", nullable = false)
	private int anoPublicacao;
	
	// Construtor vazio
	public Livro() {
		
	}
	
	// Construtor sem ID
	public Livro(String titulo, Autor autor, double preco, int anoPublicacao) {
		this.titulo = titulo;
		this.autor = autor;
		this.preco = preco;
		this.anoPublicacao = anoPublicacao;
	}

	
	// Construtor completo
		public Livro(int id, String titulo, Autor autor, double preco, int anoPublicacao) {
			this.id = id;
			this.titulo = titulo;
			this.autor = autor;
			this.preco = preco;
			this.anoPublicacao = anoPublicacao;
		}
		
		//Gettters e Setters
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public String getTitulo() {
			return titulo;
		}
		
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		
		public Autor getAutor() {
			return autor;
		}
		
		public void setAutor(Autor autor) {
			this.autor = autor;
		}
		
		public double getPreco() {
			return preco;
		}
		
		public void setPreco(double preco) {
			this.preco = preco;
		}
		
		public int getAnoPublicacao() {
			return anoPublicacao;
		}
		
		public void setAnoPublicacao(int anoPublicacao) {
			this.anoPublicacao = anoPublicacao;
		}
		
		// toString para exibição no console
		@Override
		public String toString() {
			return "Livro{" +
					"id=" + id +
					", titulo='" + titulo + '\'' +
					", autor='" + (autor != null ? autor.getNome() : "null") + '\'' +
					", preco=" + preco +
					", anoPublicacao=" + anoPublicacao +
					'}';
		}
}
