package Esprit.Entities;

import javafx.scene.control.DatePicker;

import java.sql.Date;

public class Article {
    private int id ;
    private String titre ;
    private Date  article_date;
    private String article_desc ;
    private String file ;
    private int nblike ;
    private int nbdislike ;
    private int nbcomment ;


    public Article(String titre, String article_desc, Date article_date, int nblike, int nbdislike, int nbcomment) {
        this.titre = titre;
        this.article_date = article_date;
        this.article_desc = article_desc;
        this.nblike = nblike;
        this.nbdislike = nbdislike;
        this.nbcomment = nbcomment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getArticle_date() {
        return article_date;
    }

    public void setArticle_date(Date article_date) {
        this.article_date = article_date;
    }

    public String getArticle_desc() {
        return article_desc;
    }

    public void setArticle_desc(String article_desc) {
        this.article_desc = article_desc;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getNblike() {
        return nblike;
    }

    public void setNblike(int nblike) {
        this.nblike = nblike;
    }

    public int getNbdislike() {
        return nbdislike;
    }

    public void setNbdislike(int nbdislike) {
        this.nbdislike = nbdislike;
    }

    public int getNbcomment() {
        return nbcomment;
    }

    public void setNbcomment(int nbcomment) {
        this.nbcomment = nbcomment;
    }

    public Article() {

    }

    public Article(int id, String titre, Date article_date, String article_desc, String file, int nblike, int nbdislike, int nbcomment) {
        this.id = id;
        this.titre = titre;
        this.article_date = article_date;
        this.article_desc = article_desc;
        this.file = file;
        this.nblike = nblike;
        this.nbdislike = nbdislike;
        this.nbcomment = nbcomment;
    }

    public Article(String titre, Date article_date, String article_desc, String file, int nblike, int nbdislike, int nbcomment) {
        this.titre = titre;
        this.article_date = article_date;
        this.article_desc = article_desc;
        this.file = file;
        this.nblike = nblike;
        this.nbdislike = nbdislike;
        this.nbcomment = nbcomment;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", article_date=" + article_date +
                ", article_desc='" + article_desc + '\'' +
                ", file='" + file + '\'' +
                ", nblike=" + nblike +
                ", nbdislike=" + nbdislike +
                ", nbcomment=" + nbcomment +
                '}';
    }
}
