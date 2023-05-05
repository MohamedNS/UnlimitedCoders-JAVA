package Services;

import Interfaces.ArticleInt;
import Entity.Article;
import Entity.Commentaire;
import Utils.MyConnection;
import com.itextpdf.text.DocumentException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleService implements ArticleInt {

    public ArticleService() {
    }

    @Override
    public void insertArticle(Article article) {
        String requete = "insert into article (titre,article_desc,article_date,nbcomment,nblike,nbdislike,file) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) MyConnection.getInstance().getConnection().prepareStatement(requete);

            ps.setString(1, article.getTitre());
            ps.setString(2, article.getArticle_desc());
            ps.setDate(3, Date.valueOf(article.getArticle_date().toLocalDate()));
            ps.setInt(4, article.getNbcomment());
            ps.setInt(5, article.getNblike());
            ps.setInt(6, article.getNbdislike());
            ps.setString(7, article.getFile());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void update(Article article) {
        String requete = "UPDATE article SET titre=?, article_date=?, article_desc=?, file=?, nblike=?, nbdislike=?, nbcomment=? WHERE id=?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getConnection().prepareStatement(requete);
            ps.setString(1, article.getTitre());
            ps.setDate(2, article.getArticle_date());
            ps.setString(3, article.getArticle_desc());
            ps.setString(4, article.getFile());
            ps.setInt(5, article.getNblike());
            ps.setInt(6, article.getNbdislike());
            ps.setInt(7, article.getNbcomment());
            ps.setInt(8, article.getId());
            ps.executeUpdate();
            System.out.println("Article updated successfully");
        } catch (SQLException ex) {
            System.out.println("Error updating article: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String requete = "delete from article where Id=?";
        try {
            PreparedStatement ps = (PreparedStatement) MyConnection.getInstance().getConnection().prepareStatement(requete);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("article supprimée");
        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la suppression " + ex.getMessage());
        }
    }

    @Override
    public Article findDepotById(int id) {
        String req = "select * from article where id =" + id;
        Article article = new Article();
        try {
            PreparedStatement pst = (PreparedStatement) MyConnection.getInstance().getConnection().prepareStatement(req);

            ResultSet resultat = pst.executeQuery(req);
            // while(rs.next()){
            resultat.next();
            article.setTitre(resultat.getString(2));
            article.setArticle_desc(resultat.getString(3));
            article.setArticle_date(resultat.getDate(4));
            article.setNbcomment(resultat.getInt(5));
            article.setNblike(resultat.getInt(6));
            article.setNbdislike(resultat.getInt(7));
            article.setFile(resultat.getString(8));
            //}
        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return article;
    }

    @Override
    public List<Article> DisplayAll() throws SQLException {
        List<Article> listearticle = new ArrayList<Article>();
        MyConnection m = MyConnection.getInstance();

        String requete = "select * from article";
        Statement pst = m.getConnection().createStatement();
        ResultSet resultat = pst.executeQuery(requete);

        while (resultat.next()) {
            Article article = new Article();

            article.setId(resultat.getInt(1));
            article.setTitre(resultat.getString(2));
            article.setArticle_desc(resultat.getString(3));
            article.setArticle_date(resultat.getDate(4));
            article.setNbcomment(resultat.getInt(5));
            article.setNblike(resultat.getInt(6));
            article.setNbdislike(resultat.getInt(7));
            article.setFile(resultat.getString(8));

            listearticle.add(article);
        }
        return listearticle;
    }

    public static void ajouterCommentaire(int articleId, String contenu, java.util.Date d) throws SQLException {

        MyConnection conn = MyConnection.getInstance();

        // Create a new Commentaire object with the provided content and the article ID
        Commentaire commentaire = new Commentaire(0, contenu, (Date) d, articleId);

        // Insert the new commentaire into the database
        String sql = "INSERT INTO commentaire (commentairecontenu, commentairedate, article_id) VALUES (?, ?, ?)";
        PreparedStatement statement = (PreparedStatement) conn.getConnection().prepareStatement(sql);
        statement.setString(1, commentaire.getCommentairecontenu());
        statement.setDate(2, (commentaire.getCommentairedate()));
        statement.setInt(3, commentaire.getArticle_id());
        statement.executeUpdate();
    }

    public List<Article> searchArticles(String str) {
        List<Article> articles = new ArrayList<>();
        String requete = "SELECT * FROM article WHERE titre LIKE ? OR article_desc LIKE ? OR article_date LIKE ? OR nblike LIKE ? OR nbdislike LIKE ?  OR nbcomment LIKE ?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getConnection().prepareStatement(requete);
            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");
            ps.setString(3, "%" + str + "%");
            ps.setString(4, "%" + str + "%");
            ps.setString(5, "%" + str + "%");
            ps.setString(6, "%" + str + "%");

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Article article = new Article();
                article.setId(result.getInt("id"));
                article.setTitre(result.getString("titre"));
                article.setArticle_desc(result.getString("article_desc"));
                article.setArticle_date(Date.valueOf(result.getDate("article_date").toLocalDate()));
                article.setNbcomment(result.getInt("nblike"));
                article.setNblike(result.getInt("nbdislike"));
                article.setNbdislike(result.getInt("nbcomment"));
                articles.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
    }

    private static ArticleInt intarticle;

    public static ArticleInt getInstance() {
        if (intarticle == null) {
            intarticle = (ArticleService) new ArticleService();
        }
        return intarticle;
    }

    public void GeneratePdf(String name) throws SQLException {
        List<Article> listdemande = DisplayAll();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String strDate = formatter.format(date);

        String path = "C:\\Users\\USER\\Desktop\\mohamedproject" + name + ".pdf";
        //double x=Math.random(100,999);

        Document doc = new Document();
        try {
            //Tableau 1
            PdfWriter.getInstance(doc, new FileOutputStream(path + name + ".pdf"));
            doc.open();
            PdfPTable col = new PdfPTable(2);
            PdfPCell col1 = new PdfPCell(new Paragraph(""));
            col1.setHorizontalAlignment(Element.HEADER);
            col1.setBorder(0);
            col1.setFixedHeight(70);
            col.addCell(col1);

            PdfPCell col2 = new PdfPCell(new Paragraph(strDate));
            col2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            col2.setFixedHeight(70);
            col2.setBorder(0);
            col.addCell(col2);

            doc.add(col);
            //end tableau 1

            //Tableau 2
            PdfPTable tab2 = new PdfPTable(1);
            PdfPCell t2 = new PdfPCell(new Paragraph("Estate Roomies", FontFactory.getFont(FontFactory.COURIER_BOLD)));
            t2.setHorizontalAlignment(Element.ALIGN_CENTER);
            t2.setBorder(0);
            t2.setFixedHeight(50);
            tab2.addCell(t2);

            doc.add(tab2);
            //end tableau2

            //Tableau 3
            PdfPTable tab = new PdfPTable(5);
            //add title
            PdfPCell cell = new PdfPCell(new Paragraph("La Liste des Articles"));
            cell.setColspan(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.RED);
            cell.setFixedHeight(25);
            tab.addCell(cell);
            //addheader
            PdfPCell id = new PdfPCell(new Paragraph("ID"));
            id.setHorizontalAlignment(Element.ALIGN_CENTER);
            id.setBackgroundColor(Color.LIGHT_GRAY);
            id.setFixedHeight(20);
            tab.addCell(id);

            PdfPCell nom = new PdfPCell(new Paragraph("Titre"));
            id.setHorizontalAlignment(Element.ALIGN_CENTER);
            id.setBackgroundColor(Color.LIGHT_GRAY);
            id.setFixedHeight(20);
            tab.addCell(nom);

            PdfPCell prenom = new PdfPCell(new Paragraph("Description "));
            nom.setHorizontalAlignment(Element.ALIGN_CENTER);
            nom.setBackgroundColor(Color.LIGHT_GRAY);
            nom.setFixedHeight(20);
            tab.addCell(prenom);

            PdfPCell Numero = new PdfPCell(new Paragraph("Nombre des likes :"));
            Numero.setHorizontalAlignment(Element.ALIGN_CENTER);
            Numero.setBackgroundColor(Color.LIGHT_GRAY);
            Numero.setFixedHeight(20);
            tab.addCell(Numero);

            PdfPCell Numero1 = new PdfPCell(new Paragraph("Nombre  des dislikes :"));
            Numero1.setHorizontalAlignment(Element.ALIGN_CENTER);
            Numero1.setBackgroundColor(Color.LIGHT_GRAY);
            Numero1.setFixedHeight(20);
            tab.addCell(Numero1);

            for (Article d : listdemande) {
                String idChar = Integer.toString(d.getId());
                String numero = Integer.toString(d.getNblike());
                String numero2 = Integer.toString(d.getNbdislike());

                PdfPCell idd = new PdfPCell(new Paragraph(idChar));
                id.setHorizontalAlignment(Element.ALIGN_CENTER);
                id.setFixedHeight(20);
                tab.addCell(idd);

                PdfPCell titre = new PdfPCell(new Paragraph(d.getTitre()));
                titre.setHorizontalAlignment(Element.ALIGN_CENTER);
                titre.setFixedHeight(20);
                tab.addCell(titre);

                PdfPCell descr = new PdfPCell(new Paragraph(d.getArticle_desc()));
                descr.setHorizontalAlignment(Element.ALIGN_CENTER);
                descr.setFixedHeight(20);
                tab.addCell(descr);

                PdfPCell numeroo = new PdfPCell(new Paragraph(numero));
                PdfPCell disl = new PdfPCell(new Paragraph(numero2));

                numeroo.setHorizontalAlignment(Element.ALIGN_CENTER);
                numeroo.setFixedHeight(20);
                tab.addCell(numeroo);
                tab.addCell(disl);

            }
            doc.add(tab);
        } catch (com.lowagie.text.DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        doc.close();
    }
}
