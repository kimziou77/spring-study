import AOP.AopBrowser;
import Adapter.*;
import Proxy.Browser;
import Proxy.BrowserProxy;
import Proxy.IBrowser;
import Singleton.AClazz;
import Singleton.BClazz;
import Singleton.SocketClient;

import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        // Test_01_singleton();
        // Test_02_Adapter();
        // Test_03_Proxy();
        Test_04_AOP();

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
    public static void Test_04_AOP(){
        //AOP패턴은 Proxy패턴을 활용하고 있다.
        //특정기능 앞뒤로 여러 흩어지고 공통된 기능들을 묶어주는 기능을 한다.
        //시간을 체크해준다던지 트랜젝션있는곳 시스템이 어디서 오래걸리고 있는지
        //어떤 메소드 때문에 오래걸리는지 확인하는데 많이 사용한다.
        AtomicLong start = new AtomicLong();
        AtomicLong end = new AtomicLong();
        IBrowser aopBrowser = new AopBrowser("www.naver.com",
                ()->{
                    System.out.println("before");
                    start.set(System.currentTimeMillis());
                },
                ()->{
                    long now = System.currentTimeMillis();
                    end.set(now - start.get());
                });
        aopBrowser.show(); // loading
        System.out.println("loading time :" + end.get());

        aopBrowser.show(); // cache
        System.out.println("loading time :" + end.get());
    }
        // 콘센트
    public static void connect(Electronic110V electronic110V){
        electronic110V.powerOn();
    }
}
