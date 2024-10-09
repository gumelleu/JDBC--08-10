package br.pucrs.gustavomelleu.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AcervoRepoJdbcImpl implements IAcervoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AcervoRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Livro> getLivros() {
        List<Livro> resp = this.jdbcTemplate.query("SELECT * FROM livros",
                (rs, rowNum) -> new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
                        rs.getInt("ano")));
        return resp;
    }

    @Override
    public List<String> getTitulos() {
        List<String> resp = this.jdbcTemplate.query("SELECT titulo FROM livros",
                (rs, rowNum) -> rs.getString("titulo"));
        return resp;
    }

    @Override
    public List<String> getAutores() {
        return null;
    }

    @Override
    public List<Livro> getLivrosDoAutor(String autor) {
        return null;
    }

    @Override
    public List<Livro> getLivrosDoAutor(String autor, int ano) {
        return null;
    }

    @Override
    public Livro getLivroTitulo(String titulo) {
        String sql = "SELECT * FROM livros WHERE titulo = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[] { titulo }, new LivroRowMapper());
        return jdbcTemplate.queryForObject(sql, new LivroRowMapper(), titulo);
    }

    @Override
    public ResponseEntity<Livro> getLivroTituloResp(String titulo) {
        return null;
    }

    @Override
    public boolean cadastraLivroNovo(Livro livro) {
        this.jdbcTemplate.update(
                "INSERT INTO livros(id,titulo,autor,ano) VALUES (?,?,?,?)",
                livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAno());
        return true;
    }

    @Override
    public boolean removeLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = " + id;
        this.jdbcTemplate.batchUpdate(sql);
        return true;
    }
}
