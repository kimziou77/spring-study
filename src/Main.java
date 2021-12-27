import AOP.AopBrowser;
import Adapter.*;
import Decorator.*;
import Facade.Ftp;
import Facade.Reader;
import Facade.SftpClient;
import Facade.Writer;
import Observer.Button;
import Observer.IButtonListener;
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
        // Test_03_2_AOP();
        // Test_04_Decorator();
        // Test_05_Observer();
        // Test_06_1_Facade();
        Test_06_2_Facade();
        Test_07_Strategy();
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
    public static void Test_03_2_AOP(){
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
    public static void connect(Electronic110V electronic110V){
        electronic110V.powerOn();
    }

    public static void Test_04_Decorator(){
        // 등급이 올라갈 수록 가격이 올라감.
        // 기본 뼈대를 두고 부가적인 첨가를 하며 속성을 변환시키는 것을 데코레이터 페턴이라고 한다.
        ICar audi = new Audi(1000);
        audi.showPrice();
        //a3
        ICar audi3 = new A3(audi,"A3");
        audi3.showPrice();

        //a4
        ICar audi4 = new A4(audi,"A4");
        audi4.showPrice();

        //a5
        ICar audi5 = new A5(audi,"A5");
        audi5.showPrice();

    }
    public static void Test_05_Observer(){
        Button button = new Button("버튼");
        button.addListener(new IButtonListener() {
            @Override
            public void clickEvent(String event) {
                System.out.println(event);
            }
        });
        button.click("메시지 전달 : click 1");
        button.click("메시지 전달 : click 2");
        button.click("메시지 전달 : click 3");
        button.click("메시지 전달 : click 4");
    }
    public static void Test_06_1_Facade(){
        // Facade 객체가 없는 경우 이렇게 노가다 해야함.
        Ftp ftpClient = new Ftp("www.foo.co.kr",22,"/home/etc");
        ftpClient.connect();
        ftpClient.moveDirectory();

        Writer writer = new Writer("text.tmp");
        writer.fileConnect();
        writer.write();

        Reader reader = new Reader("text.tmp");
        reader.fileDisconnect();
        reader.fileRead();

        reader.fileDisconnect();
        writer.fileDisconnect();
        ftpClient.disConnect();

    }
    public static void Test_06_2_Facade(){
        // ver1 내부 복잡한 부분을 새로운 인터페이스로 정면만 바라볼 수 있도록
        // Facade객체를 통해서 각 객체에 의존되던 애들을 간략하게 사용할 수 있다.
        SftpClient sftpClient = new SftpClient("www.foo.co.kr",22,"/home/etc", "text.tmp");
        sftpClient.connect();
        sftpClient.write();
        sftpClient.read();
        sftpClient.disConnect();
    }
    public static void Test_07_Strategy(){

    }
}

