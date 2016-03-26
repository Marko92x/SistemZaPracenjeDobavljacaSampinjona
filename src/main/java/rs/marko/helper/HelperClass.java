/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.helper;

import java.lang.reflect.Field;
//import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import rs.marko.domain.Administrativniradnik;
import rs.marko.token.AbstractTokenCreator;
import rs.marko.token.Base64Token;

/**
 *
 * @author Marko
 */
public class HelperClass {

    AbstractTokenCreator tokenHelper;

    public AbstractTokenCreator getAbstractToken() {
        return tokenHelper;
    }

    public HelperClass() {
        tokenHelper = new Base64Token();
    }

//    public String getJson(List list) {
//        JSONArray jsonList = new JSONArray();
//        for (Object o : list) {
//            JSONObject obj1 = new JSONObject();
//            for (Field field : o.getClass().getDeclaredFields()) {
//                if (!field.getName().equals("serialVersionUID")) {
//                    field.setAccessible(true);
//                    String s = "";
//                    try {
//                        s = field.get(o) + "";
//                    } catch (IllegalArgumentException | IllegalAccessException ex) {
//                        Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    obj1.put(field.getName(), s);
//                }
//            }
//            jsonList.add(obj1);
//        }
//        return jsonList.toJSONString();
//    }
    
    public Object mergeValues(Object oldObject, Object newObject) {
        Field[] nizOld = oldObject.getClass().getDeclaredFields();
        Field[] nizNew = newObject.getClass().getDeclaredFields();
        for (int i = 0; i < nizOld.length; i++) {
            if (!nizOld[i].getName().equals("serialVersionUID")) {
                nizOld[i].setAccessible(true);
                nizNew[i].setAccessible(true);
                if (nizOld[i].getGenericType().getClass().toString().equals("class java.lang.Class")) {
                    try {
                        try {
                            if (nizNew[i].get(newObject) == null || Integer.parseInt(nizNew[i].get(newObject) + "") == 0) {
                                nizNew[i].set(newObject, nizOld[i].get(oldObject));
                            }
                        } catch (NumberFormatException ignore) {
                            if (nizNew[i].get(newObject) == null) {
                                nizNew[i].set(newObject, nizOld[i].get(oldObject));
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return newObject;
    }
    
    public boolean isLogged(String token, EntityManager em) {
        try {
            String[] userPass = getAbstractToken().decode(token).split("##");
            Administrativniradnik ar = em.find(Administrativniradnik.class, Integer.parseInt(userPass[1]));
            return ar.getToken() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public void persistObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void removeObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    public void mergeObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }
}
