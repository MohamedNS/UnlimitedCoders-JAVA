package Interfaces;

import Entity.Article;
import Entity.Commentaire;

import java.sql.SQLException;
import java.util.List;

public interface CommentaireInt {
    void insert(Commentaire commentaire  , Article article);
    void insert(Commentaire commentaire );
    static List<Commentaire> getCommentairesForArticle(int articleId) throws SQLException {
        return null;
    }

    boolean update(Commentaire commentaire);

    void delete(int id);

    Commentaire findDepotById(int id);


    List<Commentaire> DisplayAll() throws SQLException;
}
