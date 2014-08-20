Não perca tempo desbloqueando seu dispositivo em lugares sem essa necessidade.

Quantas vezes você precisou desbloquear seu Android mesmo estando em casa ou em lugares longe de ladrões, fora do alcance das crianças ou livre das brincadeiras dos colegas de escritório?

O safe unlock permite que você defina lugares para você não perder tempo desbloqueando seu dispositivo.

Ao iniciar o aplicativo, será exibido uma lista de redes Wi-Fi já configuradas. Basta selecionar aquelas em que você se sinta seguro.

Todos os tipos de bloqueios de tela são suportados: reconhecimento facial, PIN, senha etc.

Keywords: automatic, face unlock, home, keyguard, lockscreen, password, pattern, pin, safe, skip, slide, unlock, wifi, work

————

Do not waste time to unlock your device in places where there is no such need.

How many times have you unlock your Android even when at home or in places away from thieves, out of children reach or the office colleagues pranks?<br/>
Safe unlock allows you to define places so you don’t waste time unlocking your device.<br/>
When you start the application, a list of Wi-Fi networks already configured will be displayed. Select those where you feel safe.<br/>
All types of screen locks are supported: facial recognition, PIN, password etc.
<br/>
<b>Features</b>
Automatically disables the device lockscreen when at home or other predefined places
Don't asks for password inputs
The easiest way to control the device lockscreen
Don't requires administrator privileges
<br/>
<b>Keywords</b>
automatic, face unlock, home, keyguard, lockscreen, password, pattern, pin, safe, skip, slide, unlock, wifi, work

——
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Connection c1 = new Connection();
        c1.setName("Home");
        c1.setAddress("");
        c1.setChecked(true);

        Connection c2 = new Connection();
        c2.setName("Coffee House");
        c2.setAddress("");
        c2.setChecked(false);

        Connection c3 = new Connection();
        c3.setName("Office");
        c3.setAddress("");
        c3.setChecked(false);

        Connection c4 = new Connection();
        c4.setName("Country House");
        c4.setAddress("");
        c4.setChecked(true);

        Connection c5 = new Connection();
        c5.setName("Mall Center");
        c5.setAddress("");
        c5.setChecked(false);

        Connection c6 = new Connection();
        c6.setName("Restaurant");
        c6.setAddress("");
        c6.setChecked(false);

        Connection c7 = new Connection();
        c7.setName("Neighbor");
        c7.setAddress("");
        c7.setChecked(false);

        //List<Connection> result = mConnectionDao.loadAll();
        List<Connection> result = new ArrayList<Connection>();
        result.add(c1);
        result.add(c2);
        result.add(c3);
        result.add(c4);
        result.add(c5);
        result.add(c6);
        result.add(c7);