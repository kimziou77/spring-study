import Adapter.*;
import Proxy.Browser;
import Proxy.BrowserProxy;
import Proxy.IBrowser;
import Singleton.AClazz;
import Singleton.BClazz;
import Singleton.SocketClient;

public class Main {

    public static void main(String[] args) {
        // Test_01_singleton();
        // Test_02_Adapter();
         Test_03_Proxy();

    }
    public static void Test_01_singleton(){
        AClazz aClazz = new AClazz();
        BClazz bClazz = new BClazz();

        SocketClient aClient = aClazz.getSocketClient();
        SocketClient bClient = bClazz.getSocketClient();

        System.out.println("두 개의 객체가 동일한가?");
        System.out.println(aClient.equals(bClient));
    }
    public static void Test_02_Adapter(){
        HairDryer hairDryer = new HairDryer();
        connect(hairDryer);

        Cleaner cleaner = new Cleaner();
        //connect(cleaner);
        Electronic110V adapter = new SocketAdapter(cleaner);
        connect(adapter);

        AirConditioner airConditioner = new AirConditioner();
        Electronic110V airAdapter = new SocketAdapter(airConditioner);
        connect(airAdapter);

    }
    public static void Test_03_Proxy(){
//        Browser browser = new Browser("www.naver.com");
//        browser.show();
//        browser.show();
//        browser.show();
        //매번 받아오고 있음

        IBrowser browser = new BrowserProxy("www.naver.com");
        browser.show();
        browser.show();
        browser.show();
        // 똑같이 구현체 자체는 건들지 않고 캐싱하는 기능을 만들 수 있었음
        // AOP같은경우 기능 전후 내가 원하는 메소드, 흩어져있는걸 동일하게 제공하는걸 Proxy패턴으로 하고 있음.

        /*
           AOP같은경우 특정한 메소드들의 실행시간 전후로 작업하고 싶은 부분들
           일괄적으로 특정한 요청한 정보에 대해서 Request정보를 남기다넌지
           Response정보를 남긴다고 할 때 코드에 개별적으로 하는게 아니라
           일괄적으로 특정패키지에 있는 모든 메소드들 이런 형식으로
           전후에 있는 기능을 넣을 수 있는게 AOP이다.

         */
    }
        // 콘센트
    public static void connect(Electronic110V electronic110V){
        electronic110V.powerOn();
    }
}
