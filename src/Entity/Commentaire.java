package Esprit.Entities;

import java.sql.Date;

public class Commentaire {
    private int id ;
    private String commentairecontenu ;
    private Date commentairedate ;
    private int article_id ;

    public Commentaire(String commentairecontenu, Date commentairedate, int article_id) {
        this.commentairecontenu = commentairecontenu;
        this.commentairedate = commentairedate;
        this.article_id = article_id;
    }

    public Commentaire(int id, String commentairecontenu, Date commentairedate, int article_id) {
        this.id = id;
        this.commentairecontenu = commentairecontenu;
        this.commentairedate = commentairedate;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentairecontenu() {
        return commentairecontenu;
    }

    public void setCommentairecontenu(String commentairecontenu) {
        this.commentairecontenu = commentairecontenu;
    }

    public Date getCommentairedate() {
        return commentairedate;
    }

    public void setCommentairedate(Date commentairedate) {
        this.commentairedate = commentairedate;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public Commentaire() {

    }
}
