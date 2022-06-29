package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class Session {
    private static Session session;
    private Map<Object, Object> _objectContainer = new HashMap();

    private Session() {
    }

    public static Session getSession() {
        if (session != null) {
            return session;
        }
        session = new Session();
        return session;
    }

    public void put(Object key, Object value) {
        this._objectContainer.put(key, value);
    }

    public Object get(Object key) {
        return this._objectContainer.get(key);
    }

    public void cleanUpSession() {
        this._objectContainer.clear();
    }

    public void remove(Object key) {
        this._objectContainer.remove(key);
    }
}
