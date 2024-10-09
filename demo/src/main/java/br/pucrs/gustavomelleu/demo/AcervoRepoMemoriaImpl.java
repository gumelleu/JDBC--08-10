package br.pucrs.gustavomelleu.demo;

import java.util.*;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;

@Repository
public class AcervoRepoMemoriaImpl implements IAcervoRepository {
    private List<Livro> livros;

    public AcervoRepoMemoriaImpl(){
        livros = new ArrayList<>();

        livros.add(new Livro(110, "Aprendendo Java", "Maria da Silva", 2015));
        livros.add(new Livro(120, "Spring-Boot", "Jose de Souza", 2020));
        livros.add(new Livro(130, "Principios SOLID", "Pedro da Silva", 2023));
        livros.add(new Livro(140, "Padroes de Projeto", "JoanaMoura", 2023));
        livros.add(new Livro(150, "Teste Unitario", "Pedro da Silva", 2024)); 
    }

    @Override
    public List<Livro>getLivros(){  
        return livros; 
    }

    @Override
    public List<String> getTitulos() {
        return livros.stream()
               .map(livro->livro.getTitulo())
               .toList();
    }

    @Override
    public List<String> getAutores() {
        return livros.stream()
                .map(l -> l.getAutor())
                .distinct()
                .toList();
    }

    @Override
    public List<Livro> getLivrosDoAutor(String autor) {
        return livros.stream()
                  .filter(livro->livro.getAutor().equals(autor))
                  .toList();
    }

    @Override
    public List<Livro> getLivrosDoAutor(String autor, int ano) {
        return livros.stream()
            .filter(livro->livro.getAutor().equals(autor))
            .filter(livro->livro.getAno() == ano)
            .toList();
    }

    @Override
    public boolean cadastraLivroNovo(Livro livro) {
        return livros.add(livro);
    }

    @Override
    public Livro getLivroTitulo(String titulo) {
        return livros.stream()
        .filter(livro->livro.getTitulo().equals(titulo))
        .findFirst()
        .orElse(null);   
    }

    public ResponseEntity<Livro> getLivroTituloResp(String titulo) {
        Livro resp = livros.stream()
               .filter(livro->livro.getTitulo().equals(titulo))
               .findFirst()
               .orElse(null);   
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp);
    }

    @Override
    public boolean removeLivro(int id) {
        List<Livro> tmp = livros.stream()
            .filter(livro->livro.getId() == id)
            .toList();
        return tmp.removeAll(tmp);
    }
}