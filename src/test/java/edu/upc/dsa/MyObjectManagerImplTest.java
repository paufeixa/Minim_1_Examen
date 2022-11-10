package edu.upc.dsa;

import edu.upc.dsa.data.MyObjectManager;
import edu.upc.dsa.data.MyObjectManagerImpl;
import edu.upc.dsa.exceptions.BuyObjectException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.MyObject;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyObjectManagerImplTest {
    MyObjectManager om;

    @Before
    public void setUp() throws UserExistingException, BuyObjectException {
        om = new MyObjectManagerImpl();
        Credentials credentials1 = new Credentials("paufeixa20019@gmail.com","1234");
        om.register("Pau","Feixa","29/12/2001",credentials1);
        Credentials credentials2 = new Credentials("kevintorres@gmail.com","9876");
        om.register("Kevin","Torres","23/07/2001",credentials2);
        Credentials credentials3 = new Credentials("llucfeixa@gmail.com","abcd");
        om.register("Lluc","Feixa","29/12/2001",credentials3);
        Credentials credentials4 = new Credentials("perefeixa@gmail.com","1a2b");
        om.register("Pere","Morancho","03/11/2003",credentials4);


        om.addObject("1a","Bolígrafo","Bolígrafo de color azul",1.25);
        om.addObject("2b","Carpeta","Carpeta curso escolar",5);
        om.addObject("3c","Libreta","Libreta de 200 páginas",3);
        om.addObject("4d","Calculadora","Calculador científica",30);

        om.buyObject("paufeixa20019@gmail.com","2b");
    }

    @After
    public void tearDown() { this.om = null; }

    @Test
    public void testRegister() throws UserExistingException{
        Assert.assertEquals(4,this.om.numUsers());
        Credentials credentials5 = new Credentials("rogertudel@gmail.com","abcd");
        this.om.register("Roger","Tudel","14/09/2001",credentials5);
        Assert.assertEquals(5,this.om.numUsers());
    }

    @Test
    public void testUsersByAlphabet() throws UserExistingException {
        testRegister();
        List<User> users = this.om.usersByAlphabet();

        Assert.assertEquals("Feixa", users.get(0).getSurname());
        Assert.assertEquals("Lluc", users.get(0).getName());

        Assert.assertEquals("Feixa", users.get(1).getSurname());
        Assert.assertEquals("Pau", users.get(1).getName());

        Assert.assertEquals("Morancho", users.get(2).getSurname());
        Assert.assertEquals("Pere", users.get(2).getName());

        Assert.assertEquals("Torres", users.get(3).getSurname());
        Assert.assertEquals("Kevin", users.get(3).getName());

        Assert.assertEquals("Tudel", users.get(4).getSurname());
        Assert.assertEquals("Roger", users.get(4).getName());
    }

    @Test
    public void testLogin() {
        Credentials credentials6 = new Credentials("paufeixa20019@gmail.com","1234");
        Assert.assertEquals(0,om.login(credentials6));
        Credentials credentials7 = new Credentials("paufeixa@gmail.com","1234");
        Assert.assertEquals(-1,om.login(credentials7));
        Credentials credentials8 = new Credentials("perefeixa@gmail.com","0123");
        Assert.assertEquals(-1,om.login(credentials8));
        Credentials credentials9 = new Credentials("paufeixa@gmail.com","0123");
        Assert.assertEquals(-1,om.login(credentials9));
        Credentials credentials10 = new Credentials("kevintorres@gmail.com","9876");
        Assert.assertEquals(0,om.login(credentials10));
    }

    @Test
    public void testAddObject() {
        Assert.assertEquals(4,this.om.numObjects());
        this.om.addObject("1a","Goma","Goma para borrar",2);
        Assert.assertEquals(4,this.om.numObjects());
        this.om.addObject("5e","Regla","Regla para medir",4);
        Assert.assertEquals(5,this.om.numObjects());
    }

    @Test
    public void testObjectsByPrice() {
        List<MyObject> myObjects = this.om.objectsByPrice();

        Assert.assertEquals(30, myObjects.get(0).getCoins(),0);
        Assert.assertEquals("Calculadora", myObjects.get(0).getName());

        Assert.assertEquals(5, myObjects.get(1).getCoins(),0);
        Assert.assertEquals("Carpeta", myObjects.get(1).getName());

        Assert.assertEquals(3, myObjects.get(2).getCoins(),0);
        Assert.assertEquals("Libreta", myObjects.get(2).getName());

        Assert.assertEquals(1.25, myObjects.get(3).getCoins(),0);
        Assert.assertEquals("Bolígrafo", myObjects.get(3).getName());
    }

    @Test
    public void testBuyObject() throws BuyObjectException {
        Assert.assertEquals(45, this.om.getUserCoins("paufeixa20019@gmail.com"), 0);
        this.om.buyObject("paufeixa20019@gmail.com", "2b");
        Assert.assertEquals(40, this.om.getUserCoins("paufeixa20019@gmail.com"), 0);

        this.om.buyObject("paufeixa20019@gmail.com", "4d");
        Assert.assertEquals(10, this.om.getUserCoins("paufeixa20019@gmail.com"), 0);

        this.om.buyObject("paufeixa20019@gmail.com", "1a");
        Assert.assertEquals(8.75, this.om.getUserCoins("paufeixa20019@gmail.com"), 0);

        Assert.assertEquals(50, this.om.getUserCoins("llucfeixa@gmail.com"), 0);
    }

    @Test
    public void testObjectsByUser() throws BuyObjectException {
        Assert.assertEquals(1,this.om.objectsByUser("paufeixa20019@gmail.com").size());

        this.om.buyObject("paufeixa20019@gmail.com","2b");
        Assert.assertEquals(2,this.om.objectsByUser("paufeixa20019@gmail.com").size());

        Assert.assertEquals(0,this.om.objectsByUser("perefeixa@gmail.com").size());

    }
}
