package Proxy;

public class Browser implements IBrowser{
    private String url;
    public Browser(String url){
        this.url = url;
    }
    @Override
    public Html show(){
        System.out.println("browser loading htrml from : " + url);
        //url을 요청하면 새로운 Html을 내려주는 형태
        return new Html(url);
    }
}
