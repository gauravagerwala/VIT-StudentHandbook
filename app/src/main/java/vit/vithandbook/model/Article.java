package vit.vithandbook.model;

/**
 * Created by pulkit on 07/07/2015.
 */
public class Article
{
    public String mainCategory ;
    public String subCategory ;
    public String topic ;
    public String content ;
    public String tags ;

    public Article(String mainCategory ,String subCategory ,String topic) //constructor used for search
    {
        this.mainCategory=mainCategory;
        this.subCategory=subCategory;
        this.topic=topic;
    }
}
