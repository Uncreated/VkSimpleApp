package com.uncreated.vksimpleapp.model2.loader;

import java.io.IOException;

public abstract class Loader<KEY, VALUE> {
    public abstract VALUE load(KEY key) throws IOException;

    public void save(KEY key, VALUE value) throws IOException {
        //nothing
    }
}
